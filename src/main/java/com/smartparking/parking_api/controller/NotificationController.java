package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Notification;
import com.smartparking.parking_api.service.NotificationService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    // GET USER NOTIFICATIONS
    @GetMapping
    public List<Notification> getNotifications(
            Authentication auth
    ){

        return service.getUserNotifications(
                auth.getName()
        );
    }

    // CREATE NOTIFICATION
    @PostMapping
    public Notification createNotification(
            Authentication auth,
            @RequestBody Notification notification
    ){

        return service.createNotification(
                auth.getName(),
                notification
        );
    }

    // MARK AS READ
    @PutMapping("/{id}/read")
    public Notification markAsRead(
            @PathVariable Long id
    ){

        return service.markAsRead(id);
    }

    // MARK ALL AS READ
    @PutMapping("/read-all")
    public String markAllAsRead(
            Authentication auth
    ){

        service.markAllAsRead(auth.getName());

        return "Toutes les notifications sont lues";
    }

    // DELETE ALL
    @DeleteMapping
    public String deleteAll(
            Authentication auth
    ){

        service.deleteAll(auth.getName());

        return "Notifications supprimées";
    }
}