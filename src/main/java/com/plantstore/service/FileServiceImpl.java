package com.plantstore.service;

import com.plantstore.entity.File;
import com.plantstore.repository.FileRepo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepo fileRepo;

    @Value("${aws.s3.access.key}")
    private String awsS3AccessKey;
    @Value("${aws.s3.secret.key}")
    private String awsS3SecretKey;


    @Override
    public File saveFile(MultipartFile file, String name, String type) {

        String saveFileURl = saveFileToAWSS3Bucket(file, type);

        File fileToSave = File.builder()
                .fileUrl(saveFileURl)
                .name(name)
                .fileType(type)
                .build();
        return fileRepo.save(fileToSave);
    }

    @Override
    public List<File> getAllFiles() {
        return fileRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }


    private String saveFileToAWSS3Bucket(MultipartFile file, String type) {
        try {
            String originalFileName = file.getOriginalFilename();
            String folder = type.equalsIgnoreCase("model") ? "models" : "images"; // hoặc thêm "other" nếu muốn
            String s3FileName = folder + "/" + originalFileName;


            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecretKey);

            AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_2)
                    .build();

            InputStream inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();

            objectMetadata.setContentType(file.getContentType()); // tự lấy MIME

            String bucketName = "plantstore-web";
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, inputStream, objectMetadata);
            amazonS3Client.putObject(putObjectRequest);
            return "https://" + bucketName + ".s3.amazonaws.com/" + s3FileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}