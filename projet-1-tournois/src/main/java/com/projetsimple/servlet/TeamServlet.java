package com.projetsimple.servlet;

import com.projetsimple.model.Team;
import com.projetsimple.repository.TeamRepository;
import com.projetsimple.repository.TournamentRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/teams")
public class TeamServlet extends HttpServlet {
    private final TeamRepository teamRepository = new TeamRepository();
    private final TournamentRepository tournamentRepository = new TournamentRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tournamentId = Integer.parseInt(req.getParameter("tournamentId"));
        try {
            req.setAttribute("tournament", tournamentRepository.findById(tournamentId));
            req.setAttribute("teams", teamRepository.findByTournamentId(tournamentId));
            req.getRequestDispatcher("/WEB-INF/jsp/teams.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Team team = new Team();
        int tournamentId = Integer.parseInt(req.getParameter("tournamentId"));
        team.setTournamentId(tournamentId);
        team.setName(req.getParameter("name"));
        team.setLogoUrl(req.getParameter("logoUrl"));
        team.setContact(req.getParameter("contact"));

        try {
            teamRepository.create(team);
            resp.sendRedirect(req.getContextPath() + "/teams?tournamentId=" + tournamentId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
