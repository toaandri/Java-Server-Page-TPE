package com.projetsimple.repository;

import com.projetsimple.config.Database;
import com.projetsimple.model.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamRepository {
    public void create(Team team) throws SQLException {
        String sql = "INSERT INTO team(tournament_id, name, logo_url, contact) VALUES(?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, team.getTournamentId());
            statement.setString(2, team.getName());
            statement.setString(3, team.getLogoUrl());
            statement.setString(4, team.getContact());
            statement.executeUpdate();
        }
    }

    public List<Team> findByTournamentId(int tournamentId) throws SQLException {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT * FROM team WHERE tournament_id = ? ORDER BY id";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, tournamentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Team team = new Team();
                    team.setId(resultSet.getInt("id"));
                    team.setTournamentId(resultSet.getInt("tournament_id"));
                    team.setName(resultSet.getString("name"));
                    team.setLogoUrl(resultSet.getString("logo_url"));
                    team.setContact(resultSet.getString("contact"));
                    teams.add(team);
                }
            }
        }
        return teams;
    }

    public Team findById(int id) throws SQLException {
        String sql = "SELECT * FROM team WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Team team = new Team();
                    team.setId(resultSet.getInt("id"));
                    team.setTournamentId(resultSet.getInt("tournament_id"));
                    team.setName(resultSet.getString("name"));
                    team.setLogoUrl(resultSet.getString("logo_url"));
                    team.setContact(resultSet.getString("contact"));
                    return team;
                }
            }
        }
        return null;
    }
}
