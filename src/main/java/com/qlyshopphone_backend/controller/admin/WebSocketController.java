package com.qlyshopphone_backend.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import static com.qlyshopphone_backend.constant.PathConstant.API_V1_WEBSOCKET;


@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_WEBSOCKET)
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public void send(@RequestParam String destination, @RequestBody Object request) {
        messagingTemplate.convertAndSend(destination, request);
    }
}