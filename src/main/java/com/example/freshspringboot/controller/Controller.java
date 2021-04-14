package com.example.freshspringboot.controller;

import com.example.freshspringboot.model.ChatMessage;
import com.example.freshspringboot.producer.Producer;
import com.example.freshspringboot.util.KafkaUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class Controller {

    @Autowired
    private KafkaUtil kafkaUtil;

    private Producer producer;
    public Controller(Producer producer) {
        super();
        this.producer = producer;
    }
    // get the message as a complex type via HTTP, publish it to broker using spring cloud stream
    @RequestMapping(value = "/sendMessage/complexType", method = RequestMethod.POST)
    public String publishMessageComplextType(@RequestBody ChatMessage payload) {
        payload.setTime(System.currentTimeMillis());
        producer.getMysource()
                .output()
                .send(MessageBuilder.withPayload(payload)
                        .setHeader("type", "chat")
                        .build());
        return "success";
    }
    // get the String message via HTTP, publish it to broker using spring cloud stream
    @RequestMapping(value = "/sendMessage/name/{name}", method = RequestMethod.GET)
    public String publishMessageString(@PathVariable("name") String name) {
// send message to channel
        System.out.println("## GET ###");
        producer.getMysource()
                .output()
                .send(MessageBuilder.withPayload(name)
                        .setHeader("type", "string")
                        .build());
        return "success";
    }

    @GetMapping("/offset/groupId/{groupId}")
    public Map<String, Long> getCurrentOffset(@PathVariable String groupId) throws InterruptedException, ExecutionException, JsonProcessingException {
        System.out.println("### called getCurrentOffset REST ###");
        Map<String, Long> offsetDetails = kafkaUtil.getOffsetDetails(groupId);
        return offsetDetails;
    }
}
