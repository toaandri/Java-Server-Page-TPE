package com.projetsimple.parking.model;

import java.time.LocalDateTime;

public class ParkingEntry {
    private int id;
    private String plateNumber;
    private int spotId;
    private LocalDateTime entryAt;
    private LocalDateTime exitAt;
    private double amount;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public int getSpotId() { return spotId; }
    public void setSpotId(int spotId) { this.spotId = spotId; }
    public LocalDateTime getEntryAt() { return entryAt; }
    public void setEntryAt(LocalDateTime entryAt) { this.entryAt = entryAt; }
    public LocalDateTime getExitAt() { return exitAt; }
    public void setExitAt(LocalDateTime exitAt) { this.exitAt = exitAt; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
