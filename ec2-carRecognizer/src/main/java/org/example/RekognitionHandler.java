package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Label;


public class RekognitionHandler {
     public RekognitionHandler(){
        try (FileInputStream input = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.accessKey = properties.getProperty("aws.access.key");
        this.secretKey = properties.getProperty("aws.secret.key");
        this.sessionToken = properties.getProperty("aws.session.token");
        this.credentials= new BasicSessionCredentials(accessKey, secretKey, sessionToken);
        this.rekognitionClient=AmazonRekognitionClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion("us-east-1")
            .build();
    }

    private Properties properties = new Properties();

    //service access credentials
    private String accessKey;
    private String secretKey;
    private String sessionToken;
    private  BasicSessionCredentials credentials;
    
    //rekognition client
    private AmazonRekognition rekognitionClient;

    //function returns true if image is a car with 90% confidence, else false
    public boolean detectCar(Image img, String key){
        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(img)
                .withMaxLabels(10)
                .withMinConfidence(75F);
        DetectLabelsResult result = rekognitionClient.detectLabels(request);

        for (Label label : result.getLabels()) {
            if (label.getName().equals("Car") && label.getConfidence()>90) {
                System.out.println("Image: " + key + " - Confidence: " + label.getConfidence() );
                return true;
            }
        }
        return false;
    }
}
