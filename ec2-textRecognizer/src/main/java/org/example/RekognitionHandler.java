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
import com.amazonaws.services.rekognition.model.TextDetection;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;




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
    private String accessKey;
    private String secretKey;
    private String sessionToken;
    private  BasicSessionCredentials credentials;
    private AmazonRekognition rekognitionClient;
    
    public String detectCarText(Image img){
        StringBuilder stringBuilder = new StringBuilder();
        DetectTextRequest request = new DetectTextRequest()
                .withImage(img);
        DetectTextResult result = rekognitionClient.detectText(request);
        for (TextDetection text : result.getTextDetections()){
            System.out.println(text.getDetectedText());
            stringBuilder.append(" ");
            stringBuilder.append(text.getDetectedText());
        }
        String str =stringBuilder.toString();
        if(!str.equals("")){
            return str;
        }
        else return null;
    }
}
