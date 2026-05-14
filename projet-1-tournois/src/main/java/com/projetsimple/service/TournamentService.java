package com.projetsimple.service;

import com.projetsimple.model.Match;
import com.projetsimple.model.StandingRow;
import com.projetsimple.model.Team;
import com.projetsimple.model.Tournament;
import com.projetsimple.repository.MatchRepository;
import com.projetsimple.repository.TeamRepository;
import com.projetsimple.repository.TournamentRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TournamentService {
    private static final int WIN_POINTS = 3;
    private static final int DRAW_POINTS = 1;

    private final TournamentRepository tournamentRepository = new TournamentRepository();
    private final TeamRepository teamRepository = new TeamRepository();
    private final MatchRepository matchRepository = new MatchRepository();

    public void generateScheduleIfMissing(int tournamentId) throws SQLException {
        if (matchRepository.existsForTournament(tournamentId)) {
            return;
        }

        Tournament tournament = tournamentRepository.findById(tournamentId);
        if (tournament == null) {
            return;
        }

        List<Team> teams = teamRepository.findByTournamentId(tournamentId);
        if (teams.size() < 2) {
            return;
        }

        if ("CHAMPIONNAT".equalsIgnoreCase(tournament.getType())) {
            generateRoundRobin(tournament, teams);
        } else {
            generateKnockout(tournament, teams);
        }
    }

    public List<StandingRow> computeStandings(int tournamentId) throws SQLException {
        List<Team> teams = teamRepository.findByTournamentId(tournamentId);
        List<Match> matches = matchRepository.findByTournamentId(tournamentId);

        Map<Integer, StandingRow> table = new HashMap<>();
        for (Team team : teams) {
            StandingRow row = new StandingRow();
            row.setTeamId(team.getId());
            row.setTeamName(team.getName());
            table.put(team.getId(), row);
        }

        for (Match match : matches) {
            if (match.getHomeScore() == null || match.getAwayScore() == null) {
                continue;
            }
            StandingRow home = table.get(match.getHomeTeamId());
            StandingRow away = table.get(match.getAwayTeamId());
            if (home == null || away == null) {
                continue;
            }

            home.setPlayed(home.getPlayed() + 1);
            away.setPlayed(away.getPlayed() + 1);
            home.setGoalsFor(home.getGoalsFor() + match.getHomeScore());
            home.setGoalsAgainst(home.getGoalsAgainst() + match.getAwayScore());
            away.setGoalsFor(away.getGoalsFor() + match.getAwayScore());
            away.setGoalsAgainst(away.getGoalsAgainst() + match.getHomeScore());

            if (match.getHomeScore() > match.getAwayScore()) {
                home.setWins(home.getWins() + 1);
                away.setLosses(away.getLosses() + 1);
                home.setPoints(home.getPoints() + WIN_POINTS);
            } else if (match.getHomeScore() < match.getAwayScore()) {
                away.setWins(away.getWins() + 1);
                home.setLosses(home.getLosses() + 1);
                away.setPoints(away.getPoints() + WIN_POINTS);
            } else {
                home.setDraws(home.getDraws() + 1);
                away.setDraws(away.getDraws() + 1);
                home.setPoints(home.getPoints() + DRAW_POINTS);
                away.setPoints(away.getPoints() + DRAW_POINTS);
            }
        }

        List<StandingRow> standings = new ArrayList<>(table.values());
        for (StandingRow row : standings) {
            row.setGoalDifference(row.getGoalsFor() - row.getGoalsAgainst());
        }
        standings.sort(Comparator
                .comparingInt(StandingRow::getPoints).reversed()
                .thenComparingInt(StandingRow::getGoalDifference).reversed()
                .thenComparingInt(StandingRow::getGoalsFor).reversed()
                .thenComparingInt(StandingRow::getGoalsAgainst));

        return standings;
    }

    private void generateRoundRobin(Tournament tournament, List<Team> teams) throws SQLException {
        LocalDateTime slot = tournament.getStartDate().atStartOfDay().plusHours(8);
        int field = 1;
        int round = 1;

        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                Match match = new Match();
                match.setTournamentId(tournament.getId());
                match.setHomeTeamId(teams.get(i).getId());
                match.setAwayTeamId(teams.get(j).getId());
                match.setStage("JOURNEE " + round);
                match.setScheduledAt(slot);
                match.setFieldNumber(field);
                matchRepository.create(match);

                field++;
                if (field > Math.max(1, tournament.getAvailableFields())) {
                    field = 1;
                    slot = slot.plusMinutes(tournament.getMatchDurationMinutes());
                    round++;
                }
            }
        }
    }

    private void generateKnockout(Tournament tournament, List<Team> teams) throws SQLException {
        LocalDateTime slot = tournament.getStartDate().atStartOfDay().plusHours(8);
        int field = 1;
        for (int i = 0; i + 1 < teams.size(); i += 2) {
            Match match = new Match();
            match.setTournamentId(tournament.getId());
            match.setHomeTeamId(teams.get(i).getId());
            match.setAwayTeamId(teams.get(i + 1).getId());
            match.setStage("KO - TOUR 1");
            match.setScheduledAt(slot);
            match.setFieldNumber(field);
            matchRepository.create(match);

            field++;
            if (field > Math.max(1, tournament.getAvailableFields())) {
                field = 1;
                slot = slot.plusMinutes(tournament.getMatchDurationMinutes());
            }
        }
    }
}
