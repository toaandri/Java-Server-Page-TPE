package com.projetsimple.taxis.repository;

import com.projetsimple.taxis.config.Database;
import com.projetsimple.taxis.model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepository {
    public void create(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO vehicle(brand_model, plate_number, mileage, status, assigned_driver_id) VALUES(?, ?, ?, ?, ?)";
        try (Connection c = Database.getConnection(); PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, vehicle.getBrandModel());
            s.setString(2, vehicle.getPlateNumber());
            s.setInt(3, vehicle.getMileage());
            s.setString(4, vehicle.getStatus());
            s.setObject(5, vehicle.getAssignedDriverId());
            s.executeUpdate();
        }
    }

    public List<Vehicle> findAll() throws SQLException {
        List<Vehicle> list = new ArrayList<>();
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT * FROM vehicle ORDER BY id DESC");
             ResultSet rs = s.executeQuery()) {
            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt("id"));
                v.setBrandModel(rs.getString("brand_model"));
                v.setPlateNumber(rs.getString("plate_number"));
                v.setMileage(rs.getInt("mileage"));
                v.setStatus(rs.getString("status"));
                v.setAssignedDriverId((Integer) rs.getObject("assigned_driver_id"));
                list.add(v);
            }
        }
        return list;
    }

    public Vehicle findFirstAvailable() throws SQLException {
        String sql = "SELECT * FROM vehicle WHERE status='DISPONIBLE' ORDER BY id LIMIT 1";
        try (Connection c = Database.getConnection(); PreparedStatement s = c.prepareStatement(sql); ResultSet rs = s.executeQuery()) {
            if (rs.next()) {
                Vehicle v = new Vehicle();
                v.setId(rs.getInt("id"));
                v.setBrandModel(rs.getString("brand_model"));
                v.setPlateNumber(rs.getString("plate_number"));
                v.setMileage(rs.getInt("mileage"));
                v.setStatus(rs.getString("status"));
                v.setAssignedDriverId((Integer) rs.getObject("assigned_driver_id"));
                return v;
            }
        }
        return null;
    }

    public void updateStatus(int id, String status) throws SQLException {
        try (Connection c = Database.getConnection(); PreparedStatement s = c.prepareStatement("UPDATE vehicle SET status=? WHERE id=?")) {
            s.setString(1, status);
            s.setInt(2, id);
            s.executeUpdate();
        }
    }
}
