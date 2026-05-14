package com.projetsimple.repository;

import com.projetsimple.config.Database;
import com.projetsimple.model.Match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MatchRepository {
    public void create(Match match) throws SQLException {
        String sql = "INSERT INTO match_schedule(tournament_id, home_team_id, away_team_id, home_score, away_score, stage, scheduled_at, field_number) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, match.getTournamentId());
            statement.setInt(2, match.getHomeTeamId());
            statement.setInt(3, match.getAwayTeamId());
            statement.setObject(4, match.getHomeScore());
            statement.setObject(5, match.getAwayScore());
            statement.setString(6, match.getStage());
            statement.setTimestamp(7, Timestamp.valueOf(match.getScheduledAt()));
            statement.setInt(8, match.getFieldNumber());
            statement.executeUpdate();
        }
    }

    public List<Match> findByTournamentId(int tournamentId) throws SQLException {
        List<Match> matches = new ArrayList<>();
        String sql = "SELECT * FROM match_schedule WHERE tournament_id = ? ORDER BY scheduled_at, id";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, tournamentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    matches.add(fromResultSet(resultSet));
                }
            }
        }
        return matches;
    }

    public void updateScore(int id, int homeScore, int awayScore) throws SQLException {
        String sql = "UPDATE match_schedule SET home_score = ?, away_score = ? WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, homeScore);
            statement.setInt(2, awayScore);
            statement.setInt(3, id);
            statement.executeUpdate();
        }
    }

    public boolean existsForTournament(int tournamentId) throws SQLException {
        String sql = "SELECT 1 FROM match_schedule WHERE tournament_id = ? LIMIT 1";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, tournamentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private Match fromResultSet(ResultSet resultSet) throws SQLException {
        Match match = new Match();
        match.setId(resultSet.getInt("id"));
        match.setTournamentId(resultSet.getInt("tournament_id"));
        match.setHomeTeamId(resultSet.getInt("home_team_id"));
        match.setAwayTeamId(resultSet.getInt("away_team_id"));
        match.setHomeScore((Integer) resultSet.getObject("home_score"));
        match.setAwayScore((Integer) resultSet.getObject("away_score"));
        match.setStage(resultSet.getString("stage"));
        Timestamp timestamp = resultSet.getTimestamp("scheduled_at");
        match.setScheduledAt(timestamp != null ? timestamp.toLocalDateTime() : LocalDateTime.now());
        match.setFieldNumber(resultSet.getInt("field_number"));
        return match;
    }
}
