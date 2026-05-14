package com.projetsimple.servlet;

import com.projetsimple.model.Team;
import com.projetsimple.repository.MatchRepository;
import com.projetsimple.repository.TeamRepository;
import com.projetsimple.repository.TournamentRepository;
import com.projetsimple.service.TournamentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/matches")
public class MatchServlet extends HttpServlet {
    private final MatchRepository matchRepository = new MatchRepository();
    private final TeamRepository teamRepository = new TeamRepository();
    private final TournamentRepository tournamentRepository = new TournamentRepository();
    private final TournamentService tournamentService = new TournamentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tournamentId = Integer.parseInt(req.getParameter("tournamentId"));
        try {
            tournamentService.generateScheduleIfMissing(tournamentId);
            List<Team> teams = teamRepository.findByTournamentId(tournamentId);
            Map<Integer, String> teamNames = new HashMap<>();
            for (Team team : teams) {
                teamNames.put(team.getId(), team.getName());
            }

            req.setAttribute("tournament", tournamentRepository.findById(tournamentId));
            req.setAttribute("matches", matchRepository.findByTournamentId(tournamentId));
            req.setAttribute("teamNames", teamNames);
            req.getRequestDispatcher("/WEB-INF/jsp/matches.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tournamentId = Integer.parseInt(req.getParameter("tournamentId"));
        int matchId = Integer.parseInt(req.getParameter("matchId"));
        int homeScore = Integer.parseInt(req.getParameter("homeScore"));
        int awayScore = Integer.parseInt(req.getParameter("awayScore"));

        try {
            matchRepository.updateScore(matchId, homeScore, awayScore);
            resp.sendRedirect(req.getContextPath() + "/matches?tournamentId=" + tournamentId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
