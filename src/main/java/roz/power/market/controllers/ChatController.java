package roz.power.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import roz.power.market.websoket.Message;

import java.time.Instant;
import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/execute")
    @SendTo("/topic/status")
    public Message executeTask(Message message) {
        // Логика обработки задачи
        // Например, сохранение задачи в БД
        System.out.println(message.getContent());
        // Симуляция выполнения задачи
        message.setContent("Выполнение задачи начато");
        message.setTimestamp(new Date());

        return message;
    }
}