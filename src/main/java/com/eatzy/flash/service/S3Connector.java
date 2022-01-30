package com.eatzy.flash.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class S3Connector {
    AmazonS3 amazonS3;

    @Inject
    S3Connector( @Named("amazonS3Client") AmazonS3 amazonS3){
        this.amazonS3 = amazonS3;
    }

    public void uploadFile(InputStream is ) throws IOException {
      try {

          //Create a PutObjectRequest
          PutObjectRequest putObjectRequest =
                  new PutObjectRequest("menu-item-bucket", "testing1.png", is, new ObjectMetadata());

          //Perform the upload, and assign the returned result object for further processing
          amazonS3.putObject(putObjectRequest);
      } catch (Exception e) {
            System.out.println(e);
      }finally {
          if (is != null) {
              is.close();
          }
      }
    }

    public  void getUpload() {
        try {
            String bukcketName = "menu-item-bucket";
            ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bukcketName).withPrefix("123/gallery/");
            ListObjectsV2Result listObjectsResult;
            do {

                listObjectsResult = amazonS3.listObjectsV2(req);
                int count = 0;
                for (S3ObjectSummary objectSummary : listObjectsResult.getObjectSummaries()) {
                    System.out.printf(" - %s (size: %d)\n", objectSummary.getKey(), objectSummary.getSize());
                    System.out.println("https://"+bukcketName+".s3.amazonaws.com/"+objectSummary.getKey());
                    // Date lastModifiedDate = objectSummary.getLastModified();

                    // String bucket = objectSummary.getBucketName();
                    String key = objectSummary.getKey();

                    String newKey = "";
                    String newBucket = "";
                    String resultText = "";
                }//end of current bulk

                // If there are more than maxKeys(in this case 999 default) keys in the bucket,
                // get a continuation token
                // and list the next objects.
                String token = listObjectsResult.getNextContinuationToken();
                System.out.println("Next Continuation Token: " + token);
                req.setContinuationToken(token);
            } while (listObjectsResult.isTruncated());
        } catch (Exception exception){
            System.out.println("test "+ exception);
        }
    }

    public List<String> getImageURIList(String bucketName, String prefix){
        ListObjectsV2Request request = new ListObjectsV2Request().
                withBucketName(bucketName).
                withPrefix(prefix);
        ListObjectsV2Result listObjectsResult;
        List<String> prefixURLs = new ArrayList<>();
        do{
            listObjectsResult = amazonS3.listObjectsV2(request);
            for (S3ObjectSummary objectSummary : listObjectsResult.getObjectSummaries()) {
                String imageURI = "https://"+bucketName+".s3.amazonaws.com/"+objectSummary.getKey();
                prefixURLs.add(imageURI);

                System.out.println("Key: " + objectSummary.getKey());
            }
            String token = listObjectsResult.getNextContinuationToken();
            System.out.println("Next Continuation Token: " + token);
            request.setContinuationToken(token);
        } while (listObjectsResult.isTruncated());
        return prefixURLs;
    }
}
