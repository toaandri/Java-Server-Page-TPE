package com.projetsimple.textile.repository;

import com.projetsimple.textile.config.Database;
import com.projetsimple.textile.model.TextileOrder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    public void create(TextileOrder order) throws SQLException {
        String sql = "INSERT INTO textile_order(client_name, article_type, quantity, size_color, expected_delivery_date, status) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, order.getClientName());
            statement.setString(2, order.getArticleType());
            statement.setInt(3, order.getQuantity());
            statement.setString(4, order.getSizeColor());
            statement.setDate(5, Date.valueOf(order.getExpectedDeliveryDate()));
            statement.setString(6, order.getStatus());
            statement.executeUpdate();
        }
    }

    public List<TextileOrder> findAll() throws SQLException {
        List<TextileOrder> orders = new ArrayList<>();
        String sql = "SELECT * FROM textile_order ORDER BY id DESC";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                orders.add(fromResultSet(resultSet));
            }
        }
        return orders;
    }

    public TextileOrder findById(int id) throws SQLException {
        String sql = "SELECT * FROM textile_order WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public void updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE textile_order SET status = ? WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    private TextileOrder fromResultSet(ResultSet rs) throws SQLException {
        TextileOrder order = new TextileOrder();
        order.setId(rs.getInt("id"));
        order.setClientName(rs.getString("client_name"));
        order.setArticleType(rs.getString("article_type"));
        order.setQuantity(rs.getInt("quantity"));
        order.setSizeColor(rs.getString("size_color"));
        order.setExpectedDeliveryDate(rs.getDate("expected_delivery_date").toLocalDate());
        order.setStatus(rs.getString("status"));
        return order;
    }
}

