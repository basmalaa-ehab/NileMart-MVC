package Nilemarmvc.example.Nilemart.order;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class OrderRegistry {

    private final Map<String, Order> orders = new HashMap<>();
    private int orderCounter = 0;

    public String generateOrderNumber() {
        orderCounter++;
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return "NM-" + date + "-" + String.format("%05d", orderCounter);
    }

    public void registerOrder(Order order) {
        orders.put(order.orderNumber(), order);
    }
    public Order findOrderById(String orderNumber) {
        return orders.get(orderNumber);
    }
    public Map<String, Order> getAllOrders() {
        return orders;
    }
    @PreDestroy
    public void printEndOfDaySummary() {
        BigDecimal revenue = orders.values()
                .stream()
                .map(Order::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("End of day: " + orders.size()
                + " orders, revenue "
                + revenue.setScale(2, RoundingMode.HALF_UP) + " EGP");
    }
}