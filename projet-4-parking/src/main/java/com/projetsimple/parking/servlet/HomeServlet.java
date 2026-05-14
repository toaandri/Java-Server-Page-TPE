package com.projetsimple.parking.servlet;

import com.projetsimple.parking.service.ParkingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/")
public class HomeServlet extends HttpServlet {
    private final ParkingService service = new ParkingService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("spots", service.spots());
            req.setAttribute("reservations", service.reservations());
            req.setAttribute("entries", service.entries());
            req.setAttribute("available", service.availableCount());
            req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
