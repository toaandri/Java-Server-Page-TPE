package com.projetsimple.textile.servlet;

import com.projetsimple.textile.model.TextileOrder;
import com.projetsimple.textile.service.WorkflowService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    private final WorkflowService workflowService = new WorkflowService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/order-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TextileOrder order = new TextileOrder();
        order.setClientName(req.getParameter("clientName"));
        order.setArticleType(req.getParameter("articleType"));
        order.setQuantity(Integer.parseInt(req.getParameter("quantity")));
        order.setSizeColor(req.getParameter("sizeColor"));
        order.setExpectedDeliveryDate(LocalDate.parse(req.getParameter("expectedDeliveryDate")));
        try {
            workflowService.createOrderWithWorkflow(order);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

