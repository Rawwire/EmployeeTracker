package com.example.tasktracker.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Task {
    private int id;
    private int employeeId;
    private String project;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String category;
    private String description;

    // No-argument constructor
    public Task() {}

    // Parameterized constructor
    public Task(int employeeId, String project, LocalDate date, LocalTime startTime, LocalTime endTime, String category, String description) {
        this.employeeId = employeeId;
        this.project = project;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
        this.description = description;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDurationInSeconds() {
        if (endTime != null) {
            Duration duration = Duration.between(startTime, endTime);
            return duration.getSeconds();
        } else {
            return 0;
        }
    }
}
