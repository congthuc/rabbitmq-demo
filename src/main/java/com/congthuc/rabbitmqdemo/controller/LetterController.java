package com.congthuc.rabbitmqdemo.controller;

import com.congthuc.rabbitmqdemo.dto.Letter;
import com.congthuc.rabbitmqdemo.dto.Order;
import com.congthuc.rabbitmqdemo.sender.LetterMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Author: pct
 * 5/9/2019
 */

@Controller
public class LetterController {
    private final LetterMessageSender letterMessageSender;

    @Autowired
    public LetterController(LetterMessageSender letterMessageSender) {
        this.letterMessageSender = letterMessageSender;
    }

    @PostMapping("/sendLetter")
    public String handleMessage(Letter letter, RedirectAttributes redirectAttributes) {
        letterMessageSender.sendLetter(letter);
        redirectAttributes.addFlashAttribute("message", "Letter message sent successfully");
        return "redirect:/letter";
    }
}
