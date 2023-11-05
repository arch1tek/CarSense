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
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.Message;


public class SQShandler {

    public SQShandler(){
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
    private String accessKey;
    private String secretKey;
    private String sessionToken;
    private String queueUrl;
    private  BasicSessionCredentials credentials;
    private AmazonSQS sqsClient;

    public String receiveMessage() {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                .withQueueUrl(queueUrl)
                .withMaxNumberOfMessages(1)
                .withWaitTimeSeconds(3);

        ReceiveMessageResult receiveMessageResult = sqsClient.receiveMessage(receiveMessageRequest);

        for (Message message : receiveMessageResult.getMessages()) {
            String messageBody = message.getBody();

            System.out.println("Received message: " + messageBody);

            // Delete the message from the queue to acknowledge receipt
            sqsClient.deleteMessage(queueUrl, message.getReceiptHandle());
            return messageBody;
        }
        return null;
    }
}

