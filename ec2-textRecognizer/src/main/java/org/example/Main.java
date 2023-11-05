package org.example;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
//import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.rekognition.model.S3Object;
import org.example.S3Handler;
import org.example.WriteToFile;

import java.io.FileOutputStream;
import java.io.PrintWriter;


public class Main {
    public static void main(String[] args) {
        S3Handler s3Handler = new S3Handler();
        WriteToFile wtf = new WriteToFile();
        String result = s3Handler.messageProcesser();
        wtf.write(result);
        System.out.println(result);
    }
}