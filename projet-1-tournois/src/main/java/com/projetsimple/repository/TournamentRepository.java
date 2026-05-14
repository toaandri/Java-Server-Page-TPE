package com.projetsimple.repository;

import com.projetsimple.config.Database;
import com.projetsimple.model.Tournament;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TournamentRepository {

    public void create(Tournament tournament) throws SQLException {
        String sql = "INSERT INTO tournament(name, sport, type, location, start_date, end_date, match_duration_minutes, available_fields) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tournament.getName());
            statement.setString(2, tournament.getSport());
            statement.setString(3, tournament.getType());
            statement.setString(4, tournament.getLocation());
            statement.setDate(5, Date.valueOf(tournament.getStartDate()));
            statement.setDate(6, Date.valueOf(tournament.getEndDate()));
            statement.setInt(7, tournament.getMatchDurationMinutes());
            statement.setInt(8, tournament.getAvailableFields());
            statement.executeUpdate();
        }
    }

    public List<Tournament> findAll() throws SQLException {
        List<Tournament> tournaments = new ArrayList<>();
        String sql = "SELECT * FROM tournament ORDER BY id DESC";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                tournaments.add(fromResultSet(resultSet));
            }
        }
        return tournaments;
    }

    public Tournament findById(int id) throws SQLException {
        String sql = "SELECT * FROM tournament WHERE id = ?";
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

    private Tournament fromResultSet(ResultSet resultSet) throws SQLException {
        Tournament tournament = new Tournament();
        tournament.setId(resultSet.getInt("id"));
        tournament.setName(resultSet.getString("name"));
        tournament.setSport(resultSet.getString("sport"));
        tournament.setType(resultSet.getString("type"));
        tournament.setLocation(resultSet.getString("location"));
        tournament.setStartDate(resultSet.getDate("start_date").toLocalDate());
        tournament.setEndDate(resultSet.getDate("end_date").toLocalDate());
        tournament.setMatchDurationMinutes(resultSet.getInt("match_duration_minutes"));
        tournament.setAvailableFields(resultSet.getInt("available_fields"));
        return tournament;
    }
}
