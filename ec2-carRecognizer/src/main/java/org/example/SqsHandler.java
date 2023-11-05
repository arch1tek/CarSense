package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.time.LocalDateTime;

public class SqsHandler {
    public SqsHandler(){
        try (FileInputStream input = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.accessKey = properties.getProperty("aws.access.key");
        this.secretKey = properties.getProperty("aws.secret.key");
        this.queueUrl = properties.getProperty("aws.queue.url");
        this.sessionToken = properties.getProperty("aws.session.token");
        this.credentials= new BasicSessionCredentials(accessKey, secretKey, sessionToken);
        this.sqsClient = AmazonSQSClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion("us-east-1") // Replace with the appropriate AWS region
            .build();
    }

    private Properties properties = new Properties();

    //access credentials
    private String accessKey;
    private String secretKey;
    private String sessionToken;
    private  BasicSessionCredentials credentials;

    //sqs queue url
    private String queueUrl;

    //SQS client
    private AmazonSQS sqsClient;

    //function to send message over the queue
    public void sendMessage(String msg){
        LocalDateTime currentTimestamp = LocalDateTime.now();
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(msg)
                .withMessageGroupId("rec")
                .withMessageDeduplicationId(currentTimestamp.toString())
                ;
        SendMessageResult sendMessageResult = sqsClient.sendMessage(sendMessageRequest);

        System.out.println("Message sent. Message ID: " + sendMessageResult.getMessageId());
    }
}

