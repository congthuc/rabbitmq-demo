package com.congthuc.rabbitmqdemo.controller;

import com.congthuc.rabbitmqdemo.dto.Order;
import com.congthuc.rabbitmqdemo.sender.OrderMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Author: pct
 * 5/9/2019
 */

@Controller
public class OrderController {
    private final OrderMessageSender orderMessageSender;

    @Autowired
    public OrderController(OrderMessageSender orderMessageSender) {
        this.orderMessageSender = orderMessageSender;
    }

    @PostMapping("/sendOrder")
    public String handleMessage(Order order, RedirectAttributes redirectAttributes) {
        orderMessageSender.sendOrder(order);
        redirectAttributes.addFlashAttribute("message", "Order message sent successfully");
        return "redirect:/";
    }
}
