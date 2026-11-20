package Nilemarmvc.example.Nilemart.notification;

import Nilemarmvc.example.Nilemart.model.Customer;

public class EmailNotificationSender implements NotificationSender{
    @Override
    public void send(Customer customer, String message) {

        if (customer.email() != null) {
            System.out.println("Sending Email to: " + customer.email());
            System.out.println("Message: " + message);

        } else {
            System.out.println("Customer has no email address.");

        }
    }
}
