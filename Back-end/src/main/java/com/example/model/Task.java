package com.example.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"userTasks"})
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "participants")
    private int possibleNumberOfParticipants;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<UserTask> userTasks = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "photos")
    private Set<String> photos = new HashSet<>();

    @Transient
    private int approvedParticipants;

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Task task = (Task) other;
        return possibleNumberOfParticipants == task.possibleNumberOfParticipants
                && Objects.equals(id, task.id)
                && Objects.equals(description, task.description)
                && Objects.equals(creationDate, task.creationDate)
                && Objects.equals(title, task.title)
                && status == task.status
                && priority == task.priority
                && Objects.equals(category, task.category)
                && Objects.equals(userTasks, task.userTasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}