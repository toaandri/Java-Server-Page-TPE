package com.projetsimple.servlet;

import com.projetsimple.repository.TournamentRepository;
import com.projetsimple.service.TournamentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/standings")
public class StandingsServlet extends HttpServlet {
    private final TournamentService tournamentService = new TournamentService();
    private final TournamentRepository tournamentRepository = new TournamentRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tournamentId = Integer.parseInt(req.getParameter("tournamentId"));
        try {
            req.setAttribute("tournament", tournamentRepository.findById(tournamentId));
            req.setAttribute("standings", tournamentService.computeStandings(tournamentId));
            req.getRequestDispatcher("/WEB-INF/jsp/standings.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
