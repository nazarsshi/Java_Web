package com.example.filter;

import com.example.dto.task.MainTaskDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskFilter {

    private static List<MainTaskDto> filterByDate(List<MainTaskDto> tasksToFilter, String order) {
        return (order != null && order.equalsIgnoreCase("Asc")) ?
                tasksToFilter
                        .stream()
                        .sorted(Comparator.comparing(MainTaskDto::getCreationDate))
                        .collect(Collectors.toList())
                :
                tasksToFilter
                        .stream()
                        .sorted(Comparator.comparing(MainTaskDto::getCreationDate).reversed())
                        .collect(Collectors.toList());

    }

    public static List<MainTaskDto> filterByPriority(List<MainTaskDto> tasksToFilter, String priority, String order) {
        return (priority == null || priority.trim().isEmpty()) ? initialFilter(tasksToFilter, order) :
                tasksToFilter
                        .stream()
                        .filter(task -> task.getPriority().equalsIgnoreCase(priority))
                        .collect(Collectors.toList());
    }

    public static List<MainTaskDto> filterByCategory(List<MainTaskDto> tasksToFilter, String category, String order) {
        return (category == null || category.trim().isEmpty()) ? initialFilter(tasksToFilter, order) :
                tasksToFilter
                        .stream()
                        .filter(task -> task.getCategory().getName().equalsIgnoreCase(category))
                        .collect(Collectors.toList());
    }

    public static List<MainTaskDto> filterByCriteria(List<MainTaskDto> tasksToFilter, String criteria, String order) {
        return (criteria == null || criteria.trim().isEmpty()) ? initialFilter(tasksToFilter, order) :
                tasksToFilter
                        .stream()
                        .filter(task ->
                                task.getDescription().contains(criteria)
                                        || task.getTitle().contains(criteria)
                        )
                        .collect(Collectors.toList());
    }

    public static List<MainTaskDto> initialFilter(List<MainTaskDto> tasksToFilter, String order) {
        List<MainTaskDto> criticalTasks = filterByDate(filterByPriority(tasksToFilter, "Critical", order), order);
        List<MainTaskDto> highTasks = filterByDate(filterByPriority(tasksToFilter, "High", order), order);
        List<MainTaskDto> mediumTasks = filterByDate(filterByPriority(tasksToFilter, "Medium", order), order);
        List<MainTaskDto> lowTasks = filterByDate(filterByPriority(tasksToFilter, "Low", order), order);

        List<MainTaskDto> initialList;
        if (order != null && order.equalsIgnoreCase("Asc")) {
            initialList = new ArrayList<>(lowTasks);
            initialList.addAll(mediumTasks);
            initialList.addAll(highTasks);
            initialList.addAll(criticalTasks);
        } else {
            initialList = new ArrayList<>(criticalTasks);
            initialList.addAll(highTasks);
            initialList.addAll(mediumTasks);
            initialList.addAll(lowTasks);
        }
        return initialList;
    }

}