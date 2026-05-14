package com.projetsimple.parking.repository;

import com.projetsimple.parking.config.Database;
import com.projetsimple.parking.model.ParkingEntry;
import com.projetsimple.parking.model.ParkingSpot;
import com.projetsimple.parking.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingRepository {
    public void createSpot(String code, boolean vipReserved) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("INSERT INTO parking_spot(code, vip_reserved, occupied) VALUES(?, ?, false)")) {
            s.setString(1, code);
            s.setBoolean(2, vipReserved);
            s.executeUpdate();
        }
    }

    public List<ParkingSpot> listSpots() throws SQLException {
        List<ParkingSpot> list = new ArrayList<>();
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT * FROM parking_spot ORDER BY id DESC");
             ResultSet rs = s.executeQuery()) {
            while (rs.next()) {
                ParkingSpot p = new ParkingSpot();
                p.setId(rs.getInt("id"));
                p.setCode(rs.getString("code"));
                p.setVipReserved(rs.getBoolean("vip_reserved"));
                p.setOccupied(rs.getBoolean("occupied"));
                list.add(p);
            }
        }
        return list;
    }

    public Integer firstAvailableSpot(String userType) throws SQLException {
        String sql = "SELECT id FROM parking_spot WHERE occupied=false " +
                ("VIP".equals(userType) ? "" : "AND vip_reserved=false ") +
                "ORDER BY id LIMIT 1";
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement(sql);
             ResultSet rs = s.executeQuery()) {
            if (rs.next()) return rs.getInt("id");
        }
        return null;
    }

    public void createReservation(Reservation r) throws SQLException {
        String sql = "INSERT INTO reservation(customer_name, user_type, plate_number, spot_id, start_at, duration_hours, status) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = Database.getConnection(); PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, r.getCustomerName());
            s.setString(2, r.getUserType());
            s.setString(3, r.getPlateNumber());
            s.setInt(4, r.getSpotId());
            s.setTimestamp(5, Timestamp.valueOf(r.getStartAt()));
            s.setInt(6, r.getDurationHours());
            s.setString(7, r.getStatus());
            s.executeUpdate();
        }
    }

    public List<Reservation> listReservations() throws SQLException {
        List<Reservation> list = new ArrayList<>();
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT * FROM reservation ORDER BY id DESC");
             ResultSet rs = s.executeQuery()) {
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setCustomerName(rs.getString("customer_name"));
                r.setUserType(rs.getString("user_type"));
                r.setPlateNumber(rs.getString("plate_number"));
                r.setSpotId(rs.getInt("spot_id"));
                r.setStartAt(rs.getTimestamp("start_at").toLocalDateTime());
                r.setDurationHours(rs.getInt("duration_hours"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        }
        return list;
    }

    public Reservation findReservationById(int reservationId) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT * FROM reservation WHERE id=?")) {
            s.setInt(1, reservationId);
            try (ResultSet rs = s.executeQuery()) {
                if (rs.next()) {
                    Reservation r = new Reservation();
                    r.setId(rs.getInt("id"));
                    r.setCustomerName(rs.getString("customer_name"));
                    r.setUserType(rs.getString("user_type"));
                    r.setPlateNumber(rs.getString("plate_number"));
                    r.setSpotId(rs.getInt("spot_id"));
                    r.setStartAt(rs.getTimestamp("start_at").toLocalDateTime());
                    r.setDurationHours(rs.getInt("duration_hours"));
                    r.setStatus(rs.getString("status"));
                    return r;
                }
            }
        }
        return null;
    }

    public void updateReservationStatus(int reservationId, String status) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("UPDATE reservation SET status=? WHERE id=?")) {
            s.setString(1, status);
            s.setInt(2, reservationId);
            s.executeUpdate();
        }
    }

    public boolean isSpotOccupied(int spotId) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT occupied FROM parking_spot WHERE id=?")) {
            s.setInt(1, spotId);
            try (ResultSet rs = s.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("occupied");
                }
            }
        }
        return false;
    }

    public void occupySpot(int spotId, boolean occupied) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("UPDATE parking_spot SET occupied=? WHERE id=?")) {
            s.setBoolean(1, occupied);
            s.setInt(2, spotId);
            s.executeUpdate();
        }
    }

    public void createEntry(String plateNumber, int spotId) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("INSERT INTO parking_entry(plate_number, spot_id, entry_at) VALUES(?, ?, NOW())")) {
            s.setString(1, plateNumber);
            s.setInt(2, spotId);
            s.executeUpdate();
        }
    }

    public ParkingEntry activeEntryByPlate(String plate) throws SQLException {
        String sql = "SELECT * FROM parking_entry WHERE plate_number=? AND exit_at IS NULL ORDER BY id DESC LIMIT 1";
        try (Connection c = Database.getConnection(); PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, plate);
            try (ResultSet rs = s.executeQuery()) {
                if (rs.next()) {
                    ParkingEntry e = new ParkingEntry();
                    e.setId(rs.getInt("id"));
                    e.setPlateNumber(rs.getString("plate_number"));
                    e.setSpotId(rs.getInt("spot_id"));
                    e.setEntryAt(rs.getTimestamp("entry_at").toLocalDateTime());
                    return e;
                }
            }
        }
        return null;
    }

    public void closeEntry(int entryId, double amount) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("UPDATE parking_entry SET exit_at=NOW(), amount=? WHERE id=?")) {
            s.setDouble(1, amount);
            s.setInt(2, entryId);
            s.executeUpdate();
        }
    }

    public List<ParkingEntry> listEntries() throws SQLException {
        List<ParkingEntry> list = new ArrayList<>();
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT * FROM parking_entry ORDER BY id DESC");
             ResultSet rs = s.executeQuery()) {
            while (rs.next()) {
                ParkingEntry e = new ParkingEntry();
                e.setId(rs.getInt("id"));
                e.setPlateNumber(rs.getString("plate_number"));
                e.setSpotId(rs.getInt("spot_id"));
                Timestamp in = rs.getTimestamp("entry_at");
                Timestamp out = rs.getTimestamp("exit_at");
                e.setEntryAt(in != null ? in.toLocalDateTime() : null);
                e.setExitAt(out != null ? out.toLocalDateTime() : null);
                e.setAmount(rs.getDouble("amount"));
                list.add(e);
            }
        }
        return list;
    }

    public int availableCount() throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT COUNT(*) c FROM parking_spot WHERE occupied=false");
             ResultSet rs = s.executeQuery()) {
            rs.next();
            return rs.getInt("c");
        }
    }
}
