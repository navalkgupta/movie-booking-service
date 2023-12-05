package com.xyz.mbs.service;

import com.xyz.mbs.model.Booking;
import org.springframework.stereotype.Service;

@Service
public interface QueueService {

    void saveToNotify(String string);
}
