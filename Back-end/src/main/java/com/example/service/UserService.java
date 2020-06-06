package com.example.service;

import com.example.dto.apiKey.ApiKeyDto;
import com.example.dto.task.CreatedTaskDto;
import com.example.dto.task.GetTaskDto;
import com.example.dto.task.MainUserTaskDto;
import com.example.dto.user.MainTaskUserDto;
import com.example.dto.user.MainUserDto;
import com.example.dto.user.UpdateUserDto;
import com.example.dto.usertask.UserTaskDto;

import java.util.List;

public interface UserService {

    MainUserDto getUserById(int id);

    List<MainUserDto> getAllUsers();

    void deleteUser(int id, ApiKeyDto apiKeyDto);

    MainUserDto updateUser(UpdateUserDto userDto);

    List<MainTaskUserDto> getAllTasksByUserId(int id);

    MainUserTaskDto takePartInTask(int userId, int taskId, UserTaskDto userTaskDto);

    int getByApiKey(String apiKey);

    MainUserTaskDto approveUserForTask(int userId, int taskId, boolean approved, ApiKeyDto apiKeyDto);

    List<CreatedTaskDto> getAllCreatedTasks(int id);

    List<GetTaskDto> getDoneTasks(int id);

}
