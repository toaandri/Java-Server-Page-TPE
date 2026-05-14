package com.projetsimple.textile.service;

import com.projetsimple.textile.model.ProductionStep;
import com.projetsimple.textile.model.TextileOrder;
import com.projetsimple.textile.repository.OrderRepository;
import com.projetsimple.textile.repository.StepRepository;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class WorkflowService {
    private final OrderRepository orderRepository = new OrderRepository();
    private final StepRepository stepRepository = new StepRepository();

    public void createOrderWithWorkflow(TextileOrder order) throws SQLException {
        order.setStatus("EN_ATTENTE");
        orderRepository.create(order);
        List<TextileOrder> created = orderRepository.findAll();
        if (!created.isEmpty()) {
            stepRepository.createDefaultSteps(created.get(0).getId());
        }
    }

    public List<TextileOrder> listOrders() throws SQLException {
        return orderRepository.findAll();
    }

    public TextileOrder getOrder(int orderId) throws SQLException {
        return orderRepository.findById(orderId);
    }

    public List<ProductionStep> getSteps(int orderId) throws SQLException {
        return stepRepository.findByOrderId(orderId);
    }

    public String startStep(int stepId, String responsible) throws SQLException {
        ProductionStep current = stepRepository.findById(stepId);
        if (current == null) {
            return "Etape introuvable.";
        }
        if (!canStartStep(current)) {
            return "Blocage: l'etape precedente n'est pas terminee.";
        }
        stepRepository.startStep(stepId, responsible);
        orderRepository.updateStatus(current.getOrderId(), "EN_PRODUCTION");
        return null;
    }

    public String completeStep(int stepId, String notes) throws SQLException {
        ProductionStep current = stepRepository.findById(stepId);
        if (current == null) {
            return "Etape introuvable.";
        }
        if (!"IN_PROGRESS".equals(current.getStatus())) {
            return "Impossible de valider une etape qui n'est pas en cours.";
        }
        stepRepository.completeStep(stepId, notes);

        List<ProductionStep> steps = stepRepository.findByOrderId(current.getOrderId());
        boolean allDone = steps.stream().allMatch(s -> "DONE".equals(s.getStatus()));
        if (allDone) {
            orderRepository.updateStatus(current.getOrderId(), "LIVRE");
        }
        return null;
    }

    public String estimateDelayText(int orderId) throws SQLException {
        List<ProductionStep> steps = stepRepository.findByOrderId(orderId);
        long plannedHours = steps.stream()
                .map(ProductionStep::getPlannedDurationHours)
                .filter(h -> h != null)
                .mapToLong(Integer::longValue)
                .sum();

        LocalDateTime start = steps.stream()
                .map(ProductionStep::getStartedAt)
                .filter(t -> t != null)
                .min(LocalDateTime::compareTo)
                .orElse(null);
        if (start == null) {
            return "Aucun demarrage pour l'instant.";
        }

        LocalDateTime now = LocalDateTime.now();
        long elapsed = Duration.between(start, now).toHours();
        if (elapsed > plannedHours) {
            return "Retard potentiel: +" + (elapsed - plannedHours) + "h par rapport au planning.";
        }
        return "Planning tenu: environ " + (plannedHours - elapsed) + "h restantes.";
    }

    private boolean canStartStep(ProductionStep step) throws SQLException {
        if ("COUPE".equals(step.getStepName())) {
            return true;
        }
        List<ProductionStep> steps = stepRepository.findByOrderId(step.getOrderId());
        for (int i = 0; i < steps.size(); i++) {
            ProductionStep s = steps.get(i);
            if (s.getId() == step.getId() && i > 0) {
                return "DONE".equals(steps.get(i - 1).getStatus());
            }
        }
        return false;
    }
}

