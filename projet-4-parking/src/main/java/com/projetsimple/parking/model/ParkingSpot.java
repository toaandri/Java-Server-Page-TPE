package com.projetsimple.parking.model;

public class ParkingSpot {
    private int id;
    private String code;
    private boolean vipReserved;
    private boolean occupied;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public boolean isVipReserved() { return vipReserved; }
    public void setVipReserved(boolean vipReserved) { this.vipReserved = vipReserved; }
    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }
}
