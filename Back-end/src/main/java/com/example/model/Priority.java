package com.example.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum Priority {

    CRITICAL("critical", 5),
    HIGH("high", 4),
    MEDIUM("medium", 3),
    LOW("low", 2),
    NONE("none", 1);

    private String taskPriority;

    private int priorityLvl;

    Priority(String taskPriority, int priorityLvl){
        this.taskPriority = taskPriority;
        this.priorityLvl = priorityLvl;
    }

    public static Priority getFromName(String name) {
        Optional<Priority> optionalPriority = Arrays
                .stream(values())
                .filter(x -> x.getTaskPriority().equalsIgnoreCase(name))
                .findFirst();
        if (optionalPriority.isPresent()) {
            return optionalPriority.get();
        } else {
            throw new UnsupportedOperationException("Unsupported priority: " + name);
        }
    }

    public static Boolean isPriority(String name) {
        Optional<Priority> priority = Arrays
                .stream(values())
                .filter(x -> x.getTaskPriority().equalsIgnoreCase(name))
                .findFirst();
        return priority.isPresent();
    }

}
