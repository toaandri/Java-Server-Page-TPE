package com.projetsimple.taxis.servlet;

import com.projetsimple.taxis.model.Vehicle;
import com.projetsimple.taxis.service.FleetService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/vehicles")
public class VehicleServlet extends HttpServlet {
    private final FleetService service = new FleetService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Vehicle v = new Vehicle();
        v.setBrandModel(req.getParameter("brandModel"));
        v.setPlateNumber(req.getParameter("plateNumber"));
        v.setMileage(Integer.parseInt(req.getParameter("mileage")));
        try {
            service.addVehicle(v);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
