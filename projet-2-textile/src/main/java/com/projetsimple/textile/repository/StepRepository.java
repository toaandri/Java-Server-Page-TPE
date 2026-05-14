package com.projetsimple.textile.repository;

import com.projetsimple.textile.config.Database;
import com.projetsimple.textile.model.ProductionStep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StepRepository {
    public void createDefaultSteps(int orderId) throws SQLException {
        createStep(orderId, "COUPE", 8);
        createStep(orderId, "COUTURE", 12);
        createStep(orderId, "FINITION", 6);
        createStep(orderId, "LIVRAISON", 4);
    }

    private void createStep(int orderId, String stepName, int plannedDurationHours) throws SQLException {
        String sql = "INSERT INTO production_step(order_id, step_name, status, planned_duration_hours) VALUES(?, ?, 'PENDING', ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.setString(2, stepName);
            statement.setInt(3, plannedDurationHours);
            statement.executeUpdate();
        }
    }

    public List<ProductionStep> findByOrderId(int orderId) throws SQLException {
        List<ProductionStep> steps = new ArrayList<>();
        String sql = "SELECT * FROM production_step WHERE order_id = ? ORDER BY id";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    steps.add(fromResultSet(rs));
                }
            }
        }
        return steps;
    }

    public void startStep(int stepId, String responsible) throws SQLException {
        String sql = "UPDATE production_step SET status='IN_PROGRESS', responsible=?, started_at=COALESCE(started_at, NOW()) WHERE id=?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, responsible);
            statement.setInt(2, stepId);
            statement.executeUpdate();
        }
    }

    public void completeStep(int stepId, String notes) throws SQLException {
        String sql = "UPDATE production_step SET status='DONE', ended_at=NOW(), notes=? WHERE id=?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, notes);
            statement.setInt(2, stepId);
            statement.executeUpdate();
        }
    }

    public ProductionStep findById(int stepId) throws SQLException {
        String sql = "SELECT * FROM production_step WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, stepId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return fromResultSet(rs);
                }
            }
        }
        return null;
    }

    private ProductionStep fromResultSet(ResultSet rs) throws SQLException {
        ProductionStep step = new ProductionStep();
        step.setId(rs.getInt("id"));
        step.setOrderId(rs.getInt("order_id"));
        step.setStepName(rs.getString("step_name"));
        step.setResponsible(rs.getString("responsible"));
        step.setStatus(rs.getString("status"));
        step.setPlannedDurationHours((Integer) rs.getObject("planned_duration_hours"));
        Timestamp started = rs.getTimestamp("started_at");
        Timestamp ended = rs.getTimestamp("ended_at");
        step.setStartedAt(started != null ? started.toLocalDateTime() : null);
        step.setEndedAt(ended != null ? ended.toLocalDateTime() : null);
        step.setNotes(rs.getString("notes"));
        return step;
    }
}

