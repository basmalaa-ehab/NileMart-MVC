package Nilemarmvc.example.Nilemart.service;

import Nilemarmvc.example.Nilemart.enums.Governorate;
import Nilemarmvc.example.Nilemart.exception.StoreException;
import Nilemarmvc.example.Nilemart.model.Customer;
import Nilemarmvc.example.Nilemart.model.Product;
import Nilemarmvc.example.Nilemart.model.ShoppingCart;
import Nilemarmvc.example.Nilemart.notification.NotificationSender;
import Nilemarmvc.example.Nilemart.order.Order;
import Nilemarmvc.example.Nilemart.order.OrderRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final OrderRegistry orderRegistry;
    private final TaxCalculator taxCalculator;
    private final ProductService productService;
    private final ShippingQuoteService shippingQuoteService;
    private final List<NotificationSender> notificationSenders;

    public Order placeOrder(ShoppingCart cart, Governorate governorate) {
        if (cart.isEmpty()) {
            throw new StoreException("Shopping cart is empty.");
        }
        for (var entry : cart.getItems().entrySet()) {
            Product product = productService.findBySku(entry.getKey());

            if (product.getStockQuantity() < entry.getValue()) {
                throw new StoreException(
                        "Insufficient stock for: " + entry.getKey()
                );
            }
        }
        BigDecimal subtotal = cart.subtotal();
        BigDecimal vat = taxCalculator.calculateTax(subtotal);
        BigDecimal shippingFee =shippingQuoteService.quote(governorate, subtotal);

        BigDecimal total = subtotal.add(vat).add(shippingFee);

        for (var entry : cart.getItems().entrySet()) {
            productService.reduceStock(entry.getKey(), entry.getValue());
        }

        String orderNumber = orderRegistry.generateOrderNumber();
        Customer customer = cart.getCustomer();
        Order order = new Order(
                orderNumber,
                customer,
                governorate,
                Map.copyOf(cart.getItems()),
                subtotal,
                vat,
                shippingFee,
                total
        );

        orderRegistry.registerOrder(order);

        String message = "Order " + orderNumber
                + " confirmed. Total "
                + total.setScale(2, java.math.RoundingMode.HALF_UP)
                .toPlainString()
                + " EGP";

        for (NotificationSender sender : notificationSenders) {
            sender.send(customer, message);
        }

        cart.clear();

        return order;
    }
}