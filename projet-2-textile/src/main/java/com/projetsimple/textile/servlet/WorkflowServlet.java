package com.projetsimple.textile.servlet;

import com.projetsimple.textile.service.WorkflowService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/workflow")
public class WorkflowServlet extends HttpServlet {
    private final WorkflowService workflowService = new WorkflowService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        try {
            req.setAttribute("order", workflowService.getOrder(orderId));
            req.setAttribute("steps", workflowService.getSteps(orderId));
            req.setAttribute("delayInfo", workflowService.estimateDelayText(orderId));
            req.getRequestDispatcher("/WEB-INF/jsp/workflow.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        int stepId = Integer.parseInt(req.getParameter("stepId"));
        String action = req.getParameter("action");

        String error = null;
        try {
            if ("START".equals(action)) {
                error = workflowService.startStep(stepId, req.getParameter("responsible"));
            } else if ("COMPLETE".equals(action)) {
                error = workflowService.completeStep(stepId, req.getParameter("notes"));
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        if (error != null) {
            req.getSession().setAttribute("errorMessage", error);
        }
        resp.sendRedirect(req.getContextPath() + "/workflow?orderId=" + orderId);
    }
}

