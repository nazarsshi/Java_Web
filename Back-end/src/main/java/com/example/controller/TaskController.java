package com.example.controller;

import com.example.dto.apiKey.ApiKeyDto;
import com.example.dto.pagination.PaginationDto;
import com.example.dto.task.MainTaskDto;
import com.example.dto.task.TaskDto;
import com.example.error.ApiError;
import com.example.service.TaskService;
import com.example.service.impl.AmazonClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/Unity/tasks")
@RequiredArgsConstructor
@Api(tags = "Task")
public class TaskController {

    private final TaskService taskService;


    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new Task")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New task created", response = TaskDto.class),
            @ApiResponse(code = 400, message = "Validation error", response = ApiError.class)
    })
    public MainTaskDto createTask(@Valid @RequestBody TaskDto taskDto, @RequestParam int userId, @RequestParam(required = false) Long categoryId) {
        return taskService.createTask(taskDto, userId, categoryId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete task by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task successfully deleted"),
            @ApiResponse(code = 404, message = "Non-existing task id", response = ApiError.class)
    })
    public void deleteTask(@PathVariable int id, @RequestBody ApiKeyDto apiKeyDto) {
        taskService.deleteTask(id, apiKeyDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update task by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task successfully updated", response = TaskDto.class),
            @ApiResponse(code = 400, message = "Validation error", response = ApiError.class),
            @ApiResponse(code = 404, message = "Non-existing task id", response = ApiError.class)
    })
    public MainTaskDto updateTask(@Valid @RequestBody TaskDto taskDto, @PathVariable int id) {
        return taskService.updateTask(taskDto, id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get task by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task found", response = MainTaskDto.class),
            @ApiResponse(code = 404, message = "Non-existing task id", response = ApiError.class)
    })
    public MainTaskDto getTaskById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View list of all the tasks")
    @ApiResponse(code = 200, message = "List of all tasks", response = PaginationDto.class)
    public PaginationDto<MainTaskDto> getAllTasks(@RequestParam(required = false) Integer offset,
                                                  @RequestParam(required = false) Integer limit,
                                                  @RequestParam(required = false) String criteria,
                                                  @RequestParam(required = false) String priority,
                                                  @RequestParam(required = false) String category,
                                                  @RequestParam(required = false) String order) {
        return taskService.getAllTasks(offset, limit, criteria, priority, category, order);
    }

}