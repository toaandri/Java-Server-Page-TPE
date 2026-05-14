package com.projetsimple.model;

import java.time.LocalDate;

public class Tournament {
    private int id;
    private String name;
    private String sport;
    private String type;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private int matchDurationMinutes;
    private int availableFields;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getMatchDurationMinutes() {
        return matchDurationMinutes;
    }

    public void setMatchDurationMinutes(int matchDurationMinutes) {
        this.matchDurationMinutes = matchDurationMinutes;
    }

    public int getAvailableFields() {
        return availableFields;
    }

    public void setAvailableFields(int availableFields) {
        this.availableFields = availableFields;
    }
}
