package aws.s3.controller;

import aws.s3.service.AwsClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class BucketController {

    private AwsClientService awsClientService;

    @Autowired
    BucketController(AwsClientService awsClientService){
        this.awsClientService = awsClientService;
    }


    @PostMapping("/uploadPic")
    public void uploadPic(@RequestPart (value = "file") String fileUrl){
        awsClientService.uploadPic(fileUrl);

    }

    @DeleteMapping("/deletePic")
    public String deletePic(@RequestPart(value = "url") String fileUrl){
        awsClientService.deletePic(fileUrl);
        return fileUrl + "deleted";
    }

    @GetMapping("/listObjects")
    public List<String> listObjects(@RequestParam String bucketName){
        System.out.println(bucketName);
        return awsClientService.listObjects( bucketName);}

}
