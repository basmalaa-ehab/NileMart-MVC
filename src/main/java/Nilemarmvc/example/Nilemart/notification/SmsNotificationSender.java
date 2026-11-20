package Nilemarmvc.example.Nilemart.notification;

import Nilemarmvc.example.Nilemart.model.Customer;

public class SmsNotificationSender implements NotificationSender{
    @Override
    public void send(Customer customer, String message) {
        System.out.println("Sending SMS to: " + customer.phone());
        System.out.println("Message: " + message);
    }
}
