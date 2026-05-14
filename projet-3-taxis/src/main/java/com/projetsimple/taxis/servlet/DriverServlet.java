package com.projetsimple.taxis.servlet;

import com.projetsimple.taxis.model.Driver;
import com.projetsimple.taxis.service.FleetService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/drivers")
public class DriverServlet extends HttpServlet {
    private final FleetService service = new FleetService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Driver d = new Driver();
        d.setFullName(req.getParameter("fullName"));
        d.setPhone(req.getParameter("phone"));
        d.setLicenseNumber(req.getParameter("licenseNumber"));
        try {
            service.addDriver(d);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
