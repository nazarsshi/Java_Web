package com.example.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "tasks")
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Task> tasks = new HashSet<>();

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Category category = (Category) other;
        return Objects.equals(id, category.id)
                && Objects.equals(name, category.name)
                && Objects.equals(description, category.description)
                && Objects.equals(tasks, category.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}