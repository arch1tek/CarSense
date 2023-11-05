package org.example;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import com.amazonaws.services.rekognition.model.S3Object;
import org.example.SQShandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
    private String outputFileName = "output.txt"; // The name of the output file
    private SQShandler sqshandler = new SQShandler();
    private RekognitionHandler rekognitionHandler = new RekognitionHandler();
    private String accessKey;
    private String secretKey;
    private String sessionToken;
    private String bucketName = "njit-cs-643";
    private  BasicSessionCredentials credentials;
    private AmazonS3 s3Client;

    public String messageProcesser(){
        StringBuilder sBuilder=new StringBuilder();
        while (true){
            String res = sqshandler.receiveMessage();
            if (res==null)continue;
            if(res.equals("-1")){
                System.out.println("Quit");
                System.out.println("Data has been written to " + outputFileName);
                break;
            }
            Image img= new Image()
                            .withS3Object(new S3Object()
                            .withName(res).withBucket(bucketName));
            
            String detText = rekognitionHandler.detectCarText(img);
            if(detText!=null){
                sBuilder.append(res);
                sBuilder.append(detText);
                sBuilder.append("\n");
            }
        }
        return sBuilder.toString();
    }  
}

