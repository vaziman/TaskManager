package org.example.taskmanager.models.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "tasks")
@Data
@Table(name = "tasks")
public class Task {
    private LocalDateTime dateOfCreated;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "completed")
    private boolean completed;
    @Column(name = "priority")
    private int priority;
    @Column(name = "created_date", updatable  = false)
    private LocalDateTime createdDate;
    @Column(name = "status")
    private String status;
    @Column(name = "assigned_to")
    private String assignedTo;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

}
