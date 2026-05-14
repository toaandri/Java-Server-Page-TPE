package com.projetsimple.taxis.model;

public class Vehicle {
    private int id;
    private String brandModel;
    private String plateNumber;
    private int mileage;
    private String status;
    private Integer assignedDriverId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getBrandModel() { return brandModel; }
    public void setBrandModel(String brandModel) { this.brandModel = brandModel; }
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getAssignedDriverId() { return assignedDriverId; }
    public void setAssignedDriverId(Integer assignedDriverId) { this.assignedDriverId = assignedDriverId; }
}
