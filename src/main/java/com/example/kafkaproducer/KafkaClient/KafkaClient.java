package com.example.kafkaproducer.KafkaClient;

import com.example.kafkaproducer.AccessLog;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Component
public class KafkaClient
{

    @Autowired
    private KafkaTemplate kafkaTemplate = null;


    public void sendToKafka(final String key, final String data) {
         ProducerRecord<String, String> record = new ProducerRecord<>("testtopic", key, data);
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(record);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(SendResult<Integer, String> integerStringSendResult) {

            }
        });
    }

    public  void sendJson(Integer key, AccessLog accessLog){
        //ProducerRecord<Integer, AccessLog> record = new ProducerRecord<>("concurrencytopic", key, accessLog);

        ListenableFuture<SendResult<Integer, AccessLog>> future = kafkaTemplate.send("concurrencytopic", accessLog);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, AccessLog>>() {
            @Override
            public void onFailure(Throwable ex) {

            }

            @Override
            public void onSuccess(SendResult<Integer, AccessLog> result) {
                System.out.println("send key: " + result.getProducerRecord().key() + "to partition: " + result.getRecordMetadata().partition());

            }
        });
    }

    //private ProducerRecord<String, String> createRecord()
}
