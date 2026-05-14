package com.projetsimple.taxis.servlet;

import com.projetsimple.taxis.model.Ride;
import com.projetsimple.taxis.service.FleetService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/rides")
public class RideServlet extends HttpServlet {
    private final FleetService service = new FleetService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("CREATE".equals(action)) {
                Ride r = new Ride();
                r.setPickup(req.getParameter("pickup"));
                r.setDestination(req.getParameter("destination"));
                r.setDistanceKm(Double.parseDouble(req.getParameter("distanceKm")));
                r.setWaitMinutes(Integer.parseInt(req.getParameter("waitMinutes")));
                r.setExtraFees(Double.parseDouble(req.getParameter("extraFees")));
                service.createRide(r);
            } else if ("START".equals(action)) {
                service.startRide(Integer.parseInt(req.getParameter("rideId")));
            } else if ("FINISH".equals(action)) {
                service.finishRide(Integer.parseInt(req.getParameter("rideId")));
            }
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
