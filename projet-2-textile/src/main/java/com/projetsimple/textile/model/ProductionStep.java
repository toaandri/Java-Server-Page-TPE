package com.projetsimple.textile.model;

import java.time.LocalDateTime;

public class ProductionStep {
    private int id;
    private int orderId;
    private String stepName;
    private String responsible;
    private String status;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Integer plannedDurationHours;
    private String notes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public Integer getPlannedDurationHours() {
        return plannedDurationHours;
    }

    public void setPlannedDurationHours(Integer plannedDurationHours) {
        this.plannedDurationHours = plannedDurationHours;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

