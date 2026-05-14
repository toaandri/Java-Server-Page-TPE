package com.projetsimple.taxis.model;

import java.time.LocalDateTime;

public class Ride {
    private int id;
    private String pickup;
    private String destination;
    private double distanceKm;
    private int waitMinutes;
    private double extraFees;
    private String status;
    private Integer driverId;
    private Integer vehicleId;
    private Double totalPrice;
    private Double companyCommission;
    private Double driverRevenue;
    private LocalDateTime createdAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPickup() { return pickup; }
    public void setPickup(String pickup) { this.pickup = pickup; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }
    public int getWaitMinutes() { return waitMinutes; }
    public void setWaitMinutes(int waitMinutes) { this.waitMinutes = waitMinutes; }
    public double getExtraFees() { return extraFees; }
    public void setExtraFees(double extraFees) { this.extraFees = extraFees; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getDriverId() { return driverId; }
    public void setDriverId(Integer driverId) { this.driverId = driverId; }
    public Integer getVehicleId() { return vehicleId; }
    public void setVehicleId(Integer vehicleId) { this.vehicleId = vehicleId; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    public Double getCompanyCommission() { return companyCommission; }
    public void setCompanyCommission(Double companyCommission) { this.companyCommission = companyCommission; }
    public Double getDriverRevenue() { return driverRevenue; }
    public void setDriverRevenue(Double driverRevenue) { this.driverRevenue = driverRevenue; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
