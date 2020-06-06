package com.example.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class AmazonClient {

    private AmazonS3 s3client;

    private String bucketName = "testlpnu";

    private void initializeAmazon() {
        String accessKey = "AKIA2Q3SH7RK6JNO5EPY";
        String secretKey = "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";
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    public String uploadFile(String base64Declaration) {
        String fileUrl = "";
        File file = new File("image.png");
        String base64 = base64Declaration;
        if (base64Declaration.contains(",")) {
            base64 = base64Declaration.split(",")[1];
        }
        byte[] data = Base64.decodeBase64(base64);
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(data);
            String fileName = generateFileName();
            String endpointUrl = "s3.eu-north-1.amazonaws.com";
            fileUrl = "https://" + bucketName + "." + endpointUrl + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    private String generateFileName() {
        return new Date().getTime() + "-" + UUID.randomUUID().toString();
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        initializeAmazon();
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }


}