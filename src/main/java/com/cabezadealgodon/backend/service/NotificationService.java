package com.cabezadealgodon.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    @Lazy
    private SimpMessagingTemplate messagingTemplate;

    public void notificarCambio(String canal, Object mensaje) {
        messagingTemplate.convertAndSend("/topic/" + canal, mensaje);
    }
}