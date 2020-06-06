package com.example.service.impl;

import com.example.dao.CategoryDao;
import com.example.dao.TaskDao;
import com.example.dao.UserDao;
import com.example.dto.apiKey.ApiKeyDto;
import com.example.dto.pagination.PaginationDto;
import com.example.dto.task.MainTaskDto;
import com.example.dto.task.MainUserTaskDto;
import com.example.dto.task.TaskDto;
import com.example.dto.user.ParticipantDto;
import com.example.error.BadCredentialsException;
import com.example.error.EntityNotFountException;
import com.example.error.UserIsNotCreatorException;
import com.example.filter.TaskFilter;
import com.example.model.*;
import com.example.service.TaskService;
import com.example.service.UserService;
import com.example.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.utils.CalculatingUtils.calculateTaskPriority;

import static java.lang.Math.toIntExact;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;

    private final UserDao userDao;

    private final CategoryDao categoryDao;

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final AmazonClient amazonClient;

    @Override
    public MainTaskDto createTask(TaskDto taskDto, int userId, Long categoryId) {
        int apiKeyId = userService.getByApiKey(taskDto.getApiKey());
        if (userId != apiKeyId) {
            throw new BadCredentialsException("Your apiKey is not tied to this id");
        }
        Task task = modelMapper.map(taskDto, Task.class);
        Set<String> photos = new HashSet<>();
        taskDto.getPhotos().forEach(photo -> {
            photos.add(amazonClient.uploadFile(photo));
        });
        if (categoryId == null)
            task.setCategory(null);
        else
            task.setCategory(categoryDao.getById(toIntExact(categoryId)));
        task.setPhotos(photos);
        task.setCreator(getByUserId(userId));
        task.setCreationDate(LocalDate.now());
        task.setStatus(Status.PENDING);
        calculateTaskPriority(task);
        taskDao.save(task);
        return null;
    }

    @Override
    public MainTaskDto getTaskById(int id) {
        Task task = getByTaskId(id);
        calculateTaskPriority(task);
        return modelMapper.map(task, MainTaskDto.class);
    }

    @Override
    public PaginationDto<MainTaskDto> getAllTasks(Integer offset, Integer limit, String criteria, String priority,
                                                  String category, String order) {
        List<Task> tasksFromDB = taskDao.getAll();
        tasksFromDB.forEach(task -> {
            task.setApprovedParticipants(
                    (int) task.getUserTasks()
                            .stream()
                            .filter(UserTask::isApproved)
                            .count()
            );
            calculateTaskPriority(task);
            if (task.getApprovedParticipants() == task.getPossibleNumberOfParticipants()) {
                task.setStatus(Status.ACTIVE);
                task.setPriority(Priority.NONE);
            }
        });

        List<MainTaskDto> mappedTasks = tasksFromDB
                .stream()
                .map(task -> modelMapper.map(task, MainTaskDto.class))
                .collect(Collectors.toList());

        List<MainTaskDto> criteriaSorted = TaskFilter.filterByCriteria(mappedTasks, criteria, order);
        List<MainTaskDto> categorySorted = TaskFilter.filterByCategory(criteriaSorted, category, order);
        List<MainTaskDto> prioritySorted = TaskFilter.filterByPriority(categorySorted, priority, order);
        List<MainTaskDto> mainTaskDtos = TaskFilter.initialFilter(prioritySorted, order);

        if (limit != null && offset != null) {
            return PaginationUtils.paginate(mainTaskDtos, offset, limit);
        }
        return PaginationDto.<MainTaskDto>builder()
                .entities(mainTaskDtos)
                .quantity(0)
                .entitiesLeft(0)
                .build();

    }

    @Override
    public void deleteTask(int id, ApiKeyDto apiKeyDto) {
        userService.getByApiKey(apiKeyDto.getApiKey());
        taskDao.delete(getByTaskId(id));
    }

    @Override
    public MainTaskDto updateTask(TaskDto taskDto, int id) {
        int userId = userService.getByApiKey(taskDto.getApiKey());

        Task task = getByTaskId(id);
        if (task.getCreator().getId() != userId) {
            throw new UserIsNotCreatorException("User is not creator of task: " + task.getId());
        }
        Task newTask = modelMapper.map(taskDto, Task.class);
        task.setCategory(newTask.getCategory());
        task.setCreationDate(task.getCreationDate());
        task.setDescription(newTask.getDescription());
        task.setPossibleNumberOfParticipants(newTask.getPossibleNumberOfParticipants());
        task.setTitle(newTask.getTitle());
        task.setPhotos(newTask.getPhotos());
        calculateTaskPriority(task);

        return modelMapper.map(taskDao.update(task), MainTaskDto.class);
    }

    @Override
    public List<MainUserTaskDto> getAllUsersByTaskId(int id) {
        return taskDao.getById(id).getUserTasks().stream()
                .map(userTask -> modelMapper.map(userTask, MainUserTaskDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PaginationDto<MainUserTaskDto> getAllApprovedUsers(Integer offset, Integer limit, int taskId) {
        Task task = getByTaskId(taskId);
        List<MainUserTaskDto> approvedUsers = task.getUserTasks()
                .stream()
                .filter(UserTask::isApproved)
                .map(userTask -> modelMapper.map(userTask, MainUserTaskDto.class))
                .collect(Collectors.toList());

        if (limit != null && offset != null) {
            return PaginationUtils.paginate(approvedUsers, offset, limit);
        }
        return PaginationDto.<MainUserTaskDto>builder()
                .entities(approvedUsers)
                .quantity(0)
                .entitiesLeft(0)
                .build();

    }

    @Override
    public ParticipantDto isParticipant(int userId, int taskId) {
        Task task = getByTaskId(taskId);
        boolean isParticipant = task.getUserTasks()
                .stream()
                .anyMatch(userTask -> userTask.getUser().getId() == userId);
        return ParticipantDto.builder()
                .isParticipant(isParticipant)
                .build();
    }

    private Task getByTaskId(int id) {
        Task task = taskDao.getById(id);
        if (task == null) {
            throw new EntityNotFountException("Task is not found with id = " + id);
        }
        long approvedParticipants = task.getUserTasks()
                .stream()
                .filter(UserTask::isApproved)
                .count();
        task.setApprovedParticipants((int) approvedParticipants);
        if (approvedParticipants == task.getPossibleNumberOfParticipants()) {
            task.setStatus(Status.ACTIVE);
            task.setPriority(Priority.NONE);
        }
        return task;
    }

    private User getByUserId(int id) {
        User user = userDao.getById(id);
        if (user == null) {
            throw new EntityNotFountException("User is not found with id = " + id);
        }
        return user;
    }

}
