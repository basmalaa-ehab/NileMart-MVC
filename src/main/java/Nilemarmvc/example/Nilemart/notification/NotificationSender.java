package Nilemarmvc.example.Nilemart.notification;

import Nilemarmvc.example.Nilemart.model.Customer;

public interface NotificationSender {
    void send(Customer customer, String message);
}
