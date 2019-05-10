package com.congthuc.rabbitmqdemo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Author: pct
 * 5/9/2019
 */

public class Letter implements Serializable {

    private String id;
    private String subject;
    private String body;
    List <String> receivers;

    public Letter() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "Letter{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                ", body=" + body +
                ", receivers: " + String.join(",", receivers) +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List <String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List <String> receivers) {
        this.receivers = receivers;
    }
}
