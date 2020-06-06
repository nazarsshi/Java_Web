package com.example.utils;

import com.example.model.Priority;
import com.example.model.Task;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class CalculatingUtils {

    public static void calculateTaskPriority(Task task) {
        LocalDate creationDate = task.getCreationDate();
        LocalDate endDate = task.getEndDate();

        int possibleNumberOfParticipants = task.getPossibleNumberOfParticipants();
        int approvedParticipants = task.getApprovedParticipants();

        String status = task.getStatus().getTaskStatus();
        if (status.equalsIgnoreCase("active") || status.equalsIgnoreCase("done")) {
            task.setPriority(Priority.NONE);
            return;
        }

        double participantsDiff = possibleNumberOfParticipants - approvedParticipants;
        long datesDiff = DAYS.between(creationDate, endDate);

        double coef = datesDiff / participantsDiff;
        if (coef < 1) {
            task.setPriority(Priority.CRITICAL);
        } else if (coef >= 1 && coef <= 1.5) {
            task.setPriority(Priority.HIGH);
        } else if (coef > 1.5 && coef <= 2.5) {
            task.setPriority(Priority.MEDIUM);
        } else task.setPriority(Priority.LOW);

    }

}
