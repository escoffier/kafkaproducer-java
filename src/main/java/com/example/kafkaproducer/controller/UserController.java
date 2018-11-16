package com.example.kafkaproducer.controller;


import com.example.kafkaproducer.AccessLog;
import com.example.kafkaproducer.KafkaClient.KafkaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private KafkaClient kafkaClient = null;

    @GetMapping("/test1")
    @ResponseBody
    public Map<String, Object> userTest(HttpServletRequest request) {
        System.out.println("--------userTest--------->");
        //kafkaClient.sendToKafka(request.getRemoteAddr(), request.getProtocol());
        AccessLog accessLog = new AccessLog();
        accessLog.setIp(request.getRemoteAddr());
        accessLog.setPort(request.getRemotePort());
        accessLog.setUrl(request.getRequestURI());

        String key = request.getRemoteAddr() + request.getRemotePort();

        kafkaClient.sendJson(key.hashCode(), accessLog);

        Map<String, Object> result = new HashMap<>();
        result.put("result", "this is test page");
        return result;
    }

    UserController() {
        System.out.println("--------Controller---------");
    }
}
