package com.projetsimple.livraison.servlet;

import com.projetsimple.livraison.model.DeliveryOrder;
import com.projetsimple.livraison.model.DeliveryPerson;
import com.projetsimple.livraison.service.DeliveryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@WebServlet("/delivery")
public class DeliveryServlet extends HttpServlet {
    private final DeliveryService service = new DeliveryService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String message = null;
        try {
            if ("ADD_PERSON".equals(action)) {
                DeliveryPerson p = new DeliveryPerson();
                p.setFullName(req.getParameter("fullName"));
                p.setPhone(req.getParameter("phone"));
                service.addDeliveryPerson(p);
            } else if ("CREATE_ORDER".equals(action)) {
                DeliveryOrder o = new DeliveryOrder();
                o.setClientName(req.getParameter("clientName"));
                o.setPickupAddress(req.getParameter("pickupAddress"));
                o.setDeliveryAddress(req.getParameter("deliveryAddress"));
                o.setPackageWeight(Double.parseDouble(req.getParameter("packageWeight")));
                o.setPackageSize(req.getParameter("packageSize"));
                o.setDesiredAt(LocalDateTime.now());
                o.setDistanceKm(Double.parseDouble(req.getParameter("distanceKm")));
                o.setExtraFees(Double.parseDouble(req.getParameter("extraFees")));
                service.createOrder(o);
            } else if ("START".equals(action)) {
                message = service.startOrder(Integer.parseInt(req.getParameter("orderId")));
            } else if ("DELIVER".equals(action)) {
                message = service.deliverOrder(Integer.parseInt(req.getParameter("orderId")));
            } else if ("CANCEL".equals(action)) {
                message = service.cancelOrder(Integer.parseInt(req.getParameter("orderId")));
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        if (message != null) req.getSession().setAttribute("flash", message);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
