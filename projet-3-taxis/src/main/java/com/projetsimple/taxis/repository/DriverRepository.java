package com.projetsimple.taxis.repository;

import com.projetsimple.taxis.config.Database;
import com.projetsimple.taxis.model.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {
    public void create(Driver driver) throws SQLException {
        String sql = "INSERT INTO driver(full_name, phone, license_number, status) VALUES(?, ?, ?, ?)";
        try (Connection c = Database.getConnection(); PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, driver.getFullName());
            s.setString(2, driver.getPhone());
            s.setString(3, driver.getLicenseNumber());
            s.setString(4, driver.getStatus());
            s.executeUpdate();
        }
    }

    public List<Driver> findAll() throws SQLException {
        List<Driver> list = new ArrayList<>();
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT * FROM driver ORDER BY id DESC");
             ResultSet rs = s.executeQuery()) {
            while (rs.next()) {
                Driver d = new Driver();
                d.setId(rs.getInt("id"));
                d.setFullName(rs.getString("full_name"));
                d.setPhone(rs.getString("phone"));
                d.setLicenseNumber(rs.getString("license_number"));
                d.setStatus(rs.getString("status"));
                list.add(d);
            }
        }
        return list;
    }

    public Driver findFirstAvailable() throws SQLException {
        String sql = "SELECT * FROM driver WHERE status='DISPONIBLE' ORDER BY id LIMIT 1";
        try (Connection c = Database.getConnection(); PreparedStatement s = c.prepareStatement(sql); ResultSet rs = s.executeQuery()) {
            if (rs.next()) {
                Driver d = new Driver();
                d.setId(rs.getInt("id"));
                d.setFullName(rs.getString("full_name"));
                d.setPhone(rs.getString("phone"));
                d.setLicenseNumber(rs.getString("license_number"));
                d.setStatus(rs.getString("status"));
                return d;
            }
        }
        return null;
    }

    public void updateStatus(int id, String status) throws SQLException {
        try (Connection c = Database.getConnection(); PreparedStatement s = c.prepareStatement("UPDATE driver SET status=? WHERE id=?")) {
            s.setString(1, status);
            s.setInt(2, id);
            s.executeUpdate();
        }
    }
}
