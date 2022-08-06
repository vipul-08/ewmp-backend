package com.hashedin.service;

public interface SmsService {

    public void sendMessage(String contactNumber, String body);
}
