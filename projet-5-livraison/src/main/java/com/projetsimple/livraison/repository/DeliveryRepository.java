package com.projetsimple.livraison.repository;

import com.projetsimple.livraison.config.Database;
import com.projetsimple.livraison.model.DeliveryOrder;
import com.projetsimple.livraison.model.DeliveryPerson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRepository {
    public void createDeliveryPerson(DeliveryPerson person) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("INSERT INTO delivery_person(full_name, phone, status) VALUES(?, ?, ?)")) {
            s.setString(1, person.getFullName());
            s.setString(2, person.getPhone());
            s.setString(3, person.getStatus());
            s.executeUpdate();
        }
    }

    public List<DeliveryPerson> listDeliveryPeople() throws SQLException {
        List<DeliveryPerson> list = new ArrayList<>();
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT * FROM delivery_person ORDER BY id DESC");
             ResultSet rs = s.executeQuery()) {
            while (rs.next()) {
                DeliveryPerson p = new DeliveryPerson();
                p.setId(rs.getInt("id"));
                p.setFullName(rs.getString("full_name"));
                p.setPhone(rs.getString("phone"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        }
        return list;
    }

    public DeliveryPerson firstAvailableDeliveryPerson() throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT * FROM delivery_person WHERE status='DISPONIBLE' ORDER BY id LIMIT 1");
             ResultSet rs = s.executeQuery()) {
            if (rs.next()) {
                DeliveryPerson p = new DeliveryPerson();
                p.setId(rs.getInt("id"));
                p.setFullName(rs.getString("full_name"));
                p.setPhone(rs.getString("phone"));
                p.setStatus(rs.getString("status"));
                return p;
            }
        }
        return null;
    }

    public void createOrder(DeliveryOrder order) throws SQLException {
        String sql = "INSERT INTO delivery_order(client_name, pickup_address, delivery_address, package_weight, package_size, desired_at, distance_km, extra_fees, status, delivery_person_id, price) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = Database.getConnection(); PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1, order.getClientName());
            s.setString(2, order.getPickupAddress());
            s.setString(3, order.getDeliveryAddress());
            s.setDouble(4, order.getPackageWeight());
            s.setString(5, order.getPackageSize());
            s.setTimestamp(6, Timestamp.valueOf(order.getDesiredAt()));
            s.setDouble(7, order.getDistanceKm());
            s.setDouble(8, order.getExtraFees());
            s.setString(9, order.getStatus());
            s.setObject(10, order.getDeliveryPersonId());
            s.setObject(11, order.getPrice());
            s.executeUpdate();
        }
    }

    public List<DeliveryOrder> listOrders() throws SQLException {
        List<DeliveryOrder> list = new ArrayList<>();
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT * FROM delivery_order ORDER BY id DESC");
             ResultSet rs = s.executeQuery()) {
            while (rs.next()) {
                DeliveryOrder o = new DeliveryOrder();
                o.setId(rs.getInt("id"));
                o.setClientName(rs.getString("client_name"));
                o.setPickupAddress(rs.getString("pickup_address"));
                o.setDeliveryAddress(rs.getString("delivery_address"));
                o.setPackageWeight(rs.getDouble("package_weight"));
                o.setPackageSize(rs.getString("package_size"));
                Timestamp t = rs.getTimestamp("desired_at");
                o.setDesiredAt(t != null ? t.toLocalDateTime() : LocalDateTime.now());
                o.setDistanceKm(rs.getDouble("distance_km"));
                o.setExtraFees(rs.getDouble("extra_fees"));
                o.setStatus(rs.getString("status"));
                o.setDeliveryPersonId((Integer) rs.getObject("delivery_person_id"));
                Number price = (Number) rs.getObject("price");
                o.setPrice(price != null ? price.doubleValue() : null);
                list.add(o);
            }
        }
        return list;
    }

    public DeliveryOrder findOrderById(int id) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("SELECT * FROM delivery_order WHERE id=?")) {
            s.setInt(1, id);
            try (ResultSet rs = s.executeQuery()) {
                if (rs.next()) {
                    DeliveryOrder o = new DeliveryOrder();
                    o.setId(rs.getInt("id"));
                    o.setStatus(rs.getString("status"));
                    o.setDeliveryPersonId((Integer) rs.getObject("delivery_person_id"));
                    return o;
                }
            }
        }
        return null;
    }

    public void updateOrderStatus(int id, String status) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("UPDATE delivery_order SET status=? WHERE id=?")) {
            s.setString(1, status);
            s.setInt(2, id);
            s.executeUpdate();
        }
    }

    public void updateDeliveryPersonStatus(int id, String status) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement s = c.prepareStatement("UPDATE delivery_person SET status=? WHERE id=?")) {
            s.setString(1, status);
            s.setInt(2, id);
            s.executeUpdate();
        }
    }
}
