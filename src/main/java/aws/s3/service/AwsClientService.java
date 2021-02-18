package aws.s3.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AwsClientService {

    private AmazonS3 s3client;


    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;


    @PostConstruct
    private void initializeAmazon(){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();

    }

    public void uploadPic(String fileUrl){
        String[] strSplit = fileUrl.split("/");
        this.s3client.putObject("renubucket/pictures", strSplit[strSplit.length - 1], new File(fileUrl));
    }

    public List<String> listObjects(String bucketName){
        ObjectListing objectListing = s3client.listObjects(bucketName);
        List<String> objectName = new ArrayList();
        for(S3ObjectSummary os : objectListing.getObjectSummaries()){
            objectName.add(os.getKey());
        }
        return objectName;
    }


    public void deletePic(String fileUrl){
        s3client.deleteObject("renubucket", fileUrl);
    }




}
