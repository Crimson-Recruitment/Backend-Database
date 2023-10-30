package com.CrimsonBackendDatabase.crimsondb.Notifcations;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationsController {

    @SendTo("notify/payments/{random}")
    public Notifications sendPaymentNotification(@DestinationVariable Long paymentId,@DestinationVariable Long id, Notifications notification) {
        return notification;
    }

    @SendTo("msg/{receiverType}/{receiverId}")
    public Notifications sendMessageNotification(@DestinationVariable String receiverType,@DestinationVariable Long receiveId, Notifications notification) {
        return notification;
    }
}