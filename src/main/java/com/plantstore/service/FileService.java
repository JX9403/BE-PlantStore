package com.plantstore.service;

import com.plantstore.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    File saveFile(MultipartFile file, String name, String type);
    List<File> getAllFiles();
}
