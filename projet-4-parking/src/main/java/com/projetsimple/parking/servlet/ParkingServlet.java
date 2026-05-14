package com.projetsimple.parking.servlet;

import com.projetsimple.parking.model.Reservation;
import com.projetsimple.parking.service.ParkingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/parking")
public class ParkingServlet extends HttpServlet {
    private final ParkingService service = new ParkingService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String message = null;
        try {
            if ("ADD_SPOT".equals(action)) {
                service.addSpot(req.getParameter("code"), "on".equals(req.getParameter("vipReserved")));
            } else if ("RESERVE".equals(action)) {
                Reservation r = new Reservation();
                r.setCustomerName(req.getParameter("customerName"));
                r.setUserType(req.getParameter("userType"));
                r.setPlateNumber(req.getParameter("plateNumber"));
                r.setDurationHours(Integer.parseInt(req.getParameter("durationHours")));
                message = service.createReservation(r);
            } else if ("ENTRY".equals(action)) {
                message = service.vehicleEntry(req.getParameter("plateNumber"), req.getParameter("userType"));
            } else if ("EXIT".equals(action)) {
                message = service.vehicleExit(req.getParameter("plateNumber"), "on".equals(req.getParameter("subscribed")));
            } else if ("CANCEL_RESERVATION".equals(action)) {
                message = service.cancelReservation(Integer.parseInt(req.getParameter("reservationId")));
            } else if ("RESERVATION_ARRIVAL".equals(action)) {
                message = service.reservationArrival(Integer.parseInt(req.getParameter("reservationId")));
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        if (message != null) req.getSession().setAttribute("flash", message);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
