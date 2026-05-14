package com.projetsimple.servlet;

import com.projetsimple.model.Tournament;
import com.projetsimple.repository.TournamentRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/tournaments")
public class TournamentServlet extends HttpServlet {
    private final TournamentRepository tournamentRepository = new TournamentRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/tournament-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Tournament tournament = new Tournament();
        tournament.setName(req.getParameter("name"));
        tournament.setSport(req.getParameter("sport"));
        tournament.setType(req.getParameter("type"));
        tournament.setLocation(req.getParameter("location"));
        tournament.setStartDate(LocalDate.parse(req.getParameter("startDate")));
        tournament.setEndDate(LocalDate.parse(req.getParameter("endDate")));
        tournament.setMatchDurationMinutes(Integer.parseInt(req.getParameter("matchDurationMinutes")));
        tournament.setAvailableFields(Integer.parseInt(req.getParameter("availableFields")));

        try {
            tournamentRepository.create(tournament);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
