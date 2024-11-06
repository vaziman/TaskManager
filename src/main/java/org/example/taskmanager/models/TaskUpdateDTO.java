package org.example.taskmanager.models;


import lombok.Data;

@Data
public class TaskUpdateDTO {

    private Long id;
    private String name;
    private String description;
    private int priority;
}
