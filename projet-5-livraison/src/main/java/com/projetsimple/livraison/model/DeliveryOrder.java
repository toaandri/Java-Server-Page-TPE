package com.projetsimple.livraison.model;

import java.time.LocalDateTime;

public class DeliveryOrder {
    private int id;
    private String clientName;
    private String pickupAddress;
    private String deliveryAddress;
    private double packageWeight;
    private String packageSize;
    private LocalDateTime desiredAt;
    private double distanceKm;
    private double extraFees;
    private String status;
    private Integer deliveryPersonId;
    private Double price;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public String getPickupAddress() { return pickupAddress; }
    public void setPickupAddress(String pickupAddress) { this.pickupAddress = pickupAddress; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public double getPackageWeight() { return packageWeight; }
    public void setPackageWeight(double packageWeight) { this.packageWeight = packageWeight; }
    public String getPackageSize() { return packageSize; }
    public void setPackageSize(String packageSize) { this.packageSize = packageSize; }
    public LocalDateTime getDesiredAt() { return desiredAt; }
    public void setDesiredAt(LocalDateTime desiredAt) { this.desiredAt = desiredAt; }
    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }
    public double getExtraFees() { return extraFees; }
    public void setExtraFees(double extraFees) { this.extraFees = extraFees; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getDeliveryPersonId() { return deliveryPersonId; }
    public void setDeliveryPersonId(Integer deliveryPersonId) { this.deliveryPersonId = deliveryPersonId; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
