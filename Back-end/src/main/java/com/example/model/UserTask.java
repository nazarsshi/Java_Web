package com.example.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user", "task"})
@Entity
@Table(name = "volunteer_task")
public class UserTask implements Serializable {

    @Id
    @JoinColumn(name = "volunteer_id")
    @ManyToOne
    private User user;

    @Id
    @JoinColumn(name = "task_id")
    @ManyToOne
    private Task task;

    @Column(name = "participation_date")
    private LocalDate participationDate;

    @Column(name = "comment")
    private String comment;

    @Column(name = "approved")
    private boolean approved;

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        UserTask userTask = (UserTask) other;
        return  approved == userTask.approved
                && Objects.equals(user, userTask.user)
                && Objects.equals(task, userTask.task)
                && Objects.equals(participationDate, userTask.participationDate)
                && Objects.equals(comment, userTask.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

}