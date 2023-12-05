package com.xyz.mbs.service.impl;

import com.xyz.mbs.service.QueueService;
import org.springframework.stereotype.Service;

@Service
public class QueueServiceImpl implements QueueService {
    public void saveToNotify(String string) {
        System.out.println(string);
    }
}
