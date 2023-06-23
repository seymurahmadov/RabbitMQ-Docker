package com.example.rabbitmqdocker.handler;

import com.example.rabbitmqdocker.model.Notification;
import com.example.rabbitmqdocker.producer.NotificationProducer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class NotificationSender {

    @Autowired
    private NotificationProducer notificationProducer;

    public void getThread(){

        Thread thread = new Thread(() ->{

            while (true) {
                Notification notification = new Notification();
                notification.setNotificationId(UUID.randomUUID().toString());
                notification.setCreatedAt(new Date());
                notification.setMessage("First RabbitMQ project");
                notification.setSeen(Boolean.FALSE);

                notificationProducer.sendToQueue(notification);

                try {
                    Thread.sleep(5000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.setName("Notification Sender");
        thread.start();

    }

    @PostConstruct
    public void init() {
        getThread();
    }
}
