package com.projetsimple.taxis.repository;

import com.projetsimple.taxis.config.Database;
import com.projetsimple.taxis.model.Ride;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RideRepository {
    public void create(Ride ride) throws SQLException {
        String sql = "INSERT INTO ride(pickup, destination, distance_km, wait_minutes, extra_fees, status, driver_id, vehicle_id, total_price, company_commission, driver_revenue) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = Database.getConnection(); PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, ride.getPickup());
            s.setString(2, ride.getDestination());
            s.setDouble(3, ride.getDistanceKm());
            s.setInt(4, ride.getWaitMinutes());
            s.setDouble(5, ride.getExtraFees());
            s.setString(6, ride.getStatus());
            s.setObject(7, ride.getDriverId());
            s.setObject(8, ride.getVehicleId());
            s.setObject(9, ride.getTotalPrice());
            s.setObject(10, ride.getCompanyCommission());
            s.setObject(11, ride.getDriverRevenue());
            s.executeUpdate();
        }
    }

    public List<Ride> findAll() throws SQLException {
        List<Ride> list = new ArrayList<>();
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT * FROM ride ORDER BY id DESC");
             ResultSet rs = s.executeQuery()) {
            while (rs.next()) {
                Ride r = new Ride();
                r.setId(rs.getInt("id"));
                r.setPickup(rs.getString("pickup"));
                r.setDestination(rs.getString("destination"));
                r.setDistanceKm(rs.getDouble("distance_km"));
                r.setWaitMinutes(rs.getInt("wait_minutes"));
                r.setExtraFees(rs.getDouble("extra_fees"));
                r.setStatus(rs.getString("status"));
                r.setDriverId((Integer) rs.getObject("driver_id"));
                r.setVehicleId((Integer) rs.getObject("vehicle_id"));
                Number totalPrice = (Number) rs.getObject("total_price");
                Number companyCommission = (Number) rs.getObject("company_commission");
                Number driverRevenue = (Number) rs.getObject("driver_revenue");
                r.setTotalPrice(totalPrice != null ? totalPrice.doubleValue() : null);
                r.setCompanyCommission(companyCommission != null ? companyCommission.doubleValue() : null);
                r.setDriverRevenue(driverRevenue != null ? driverRevenue.doubleValue() : null);
                Timestamp ts = rs.getTimestamp("created_at");
                r.setCreatedAt(ts != null ? ts.toLocalDateTime() : null);
                list.add(r);
            }
        }
        return list;
    }

    public void updateStatus(int rideId, String status) throws SQLException {
        try (Connection c = Database.getConnection(); PreparedStatement s = c.prepareStatement("UPDATE ride SET status=? WHERE id=?")) {
            s.setString(1, status);
            s.setInt(2, rideId);
            s.executeUpdate();
        }
    }

    public Ride findById(int rideId) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT * FROM ride WHERE id=?")) {
            s.setInt(1, rideId);
            try (ResultSet rs = s.executeQuery()) {
                if (rs.next()) {
                    Ride r = new Ride();
                    r.setId(rs.getInt("id"));
                    r.setStatus(rs.getString("status"));
                    r.setDriverId((Integer) rs.getObject("driver_id"));
                    r.setVehicleId((Integer) rs.getObject("vehicle_id"));
                    return r;
                }
            }
        }
        return null;
    }
}
