package com.projetsimple.livraison.service;

import com.projetsimple.livraison.model.DeliveryOrder;
import com.projetsimple.livraison.model.DeliveryPerson;
import com.projetsimple.livraison.repository.DeliveryRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryService {
    private static final double PRICE_PER_KM = 2500.0;
    private static final double WEIGHT_FACTOR = 100.0;

    private final DeliveryRepository repository = new DeliveryRepository();

    public void addDeliveryPerson(DeliveryPerson person) throws SQLException {
        person.setStatus("DISPONIBLE");
        repository.createDeliveryPerson(person);
    }

    public void createOrder(DeliveryOrder order) throws SQLException {
        DeliveryPerson person = repository.firstAvailableDeliveryPerson();
        double price = (order.getDistanceKm() * PRICE_PER_KM) + (order.getPackageWeight() * WEIGHT_FACTOR) + order.getExtraFees();
        order.setPrice(price);
        order.setStatus("EN_ATTENTE");
        order.setDeliveryPersonId(null);

        if (person != null) {
            order.setStatus("ASSIGNEE");
            order.setDeliveryPersonId(person.getId());
            repository.updateDeliveryPersonStatus(person.getId(), "EN_LIVRAISON");
        }
        repository.createOrder(order);
    }

    public String startOrder(int orderId) throws SQLException {
        DeliveryOrder order = repository.findOrderById(orderId);
        if (order == null) return "Commande introuvable.";
        if (!"ASSIGNEE".equals(order.getStatus())) return "Impossible de passer EN_COURS sans ASSIGNEE.";
        repository.updateOrderStatus(orderId, "EN_COURS");
        return null;
    }

    public String deliverOrder(int orderId) throws SQLException {
        DeliveryOrder order = repository.findOrderById(orderId);
        if (order == null) return "Commande introuvable.";
        if (!"EN_COURS".equals(order.getStatus())) return "Impossible de passer LIVREE sans EN_COURS.";
        repository.updateOrderStatus(orderId, "LIVREE");
        if (order.getDeliveryPersonId() != null) {
            repository.updateDeliveryPersonStatus(order.getDeliveryPersonId(), "DISPONIBLE");
        }
        return null;
    }

    public String cancelOrder(int orderId) throws SQLException {
        DeliveryOrder order = repository.findOrderById(orderId);
        if (order == null) return "Commande introuvable.";
        if ("LIVREE".equals(order.getStatus())) return "Une commande livree ne peut pas etre annulee.";
        repository.updateOrderStatus(orderId, "ANNULEE");
        if (order.getDeliveryPersonId() != null) {
            repository.updateDeliveryPersonStatus(order.getDeliveryPersonId(), "DISPONIBLE");
        }
        return null;
    }

    public List<DeliveryPerson> people() throws SQLException { return repository.listDeliveryPeople(); }
    public List<DeliveryOrder> orders() throws SQLException { return repository.listOrders(); }

    public Map<String, Object> stats() throws SQLException {
        List<DeliveryOrder> orders = repository.listOrders();
        double total = orders.stream().map(DeliveryOrder::getPrice).filter(v -> v != null).mapToDouble(Double::doubleValue).sum();
        long delivered = orders.stream().filter(o -> "LIVREE".equals(o.getStatus())).count();
        long cancelled = orders.stream().filter(o -> "ANNULEE".equals(o.getStatus())).count();
        Map<String, Object> s = new HashMap<>();
        s.put("totalRevenue", total);
        s.put("delivered", delivered);
        s.put("cancelled", cancelled);
        return s;
    }
}
