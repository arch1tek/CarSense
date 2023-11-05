package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import org.example.SqsHandler;
import org.example.RekognitionHandler;

public class S3Handler {
    
    public S3Handler(){
        try (FileInputStream input = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.accessKey = properties.getProperty("aws.access.key");
        this.secretKey = properties.getProperty("aws.secret.key");
        this.sessionToken = properties.getProperty("aws.session.token");
        this.credentials= new BasicSessionCredentials(accessKey, secretKey, sessionToken);
        this.s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion("us-east-1")
            .build();
    }

    private Properties properties = new Properties();

    //handler for sending sqs messages
    private SqsHandler sqshandler = new SqsHandler();

    //handler for image recognition
    private RekognitionHandler rekognitionHandler=new RekognitionHandler();

    //credentials for service access
    private String accessKey;
    private String secretKey;
    private String sessionToken;
    private  BasicSessionCredentials credentials;

    //s3 bucket name
    private String bucketName = "njit-cs-643";

    //s3 client
    private AmazonS3 s3Client;

    //function to process images from s3 and send image key to sqs if image is a car with 90% confidence
    public void processImages(){
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            String key = objectSummary.getKey();
            Image img = new Image()
                            .withS3Object(new S3Object()
                            .withName(key).withBucket(bucketName));
            if(rekognitionHandler.detectCar(img, key)){
                sqshandler.sendMessage(key);
            }
        }
        sqshandler.sendMessage("-1");
    }
}
