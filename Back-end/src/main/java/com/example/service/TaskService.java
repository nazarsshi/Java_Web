package com.example.service;

import com.example.dto.apiKey.ApiKeyDto;
import com.example.dto.pagination.PaginationDto;
import com.example.dto.task.MainTaskDto;
import com.example.dto.task.MainUserTaskDto;
import com.example.dto.task.TaskDto;
import com.example.dto.user.ParticipantDto;

import java.util.List;

public interface TaskService {

    MainTaskDto createTask(TaskDto taskDto, int userId, Long categoryId);

    MainTaskDto getTaskById(int id);

    PaginationDto<MainTaskDto> getAllTasks(Integer offset, Integer limit, String criteria, String priority,
                                           String category, String order);

    void deleteTask(int id, ApiKeyDto apiKeyDto);

    MainTaskDto updateTask(TaskDto taskDto, int id);

    List<MainUserTaskDto> getAllUsersByTaskId(int id);

    PaginationDto<MainUserTaskDto> getAllApprovedUsers(Integer offset, Integer limit, int taskId);

    ParticipantDto isParticipant(int userId, int taskId);

}
