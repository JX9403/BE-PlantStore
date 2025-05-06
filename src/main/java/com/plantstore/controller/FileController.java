package com.plantstore.controller;

import com.plantstore.entity.File;
import com.plantstore.service.FileService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Upload API")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Operation(summary = "Upload a file", description = "Tải lên một tệp tin với các thông tin đi kèm như tên và loại")
    @PostMapping("/upload")
    public ResponseEntity<Object> saveFile(@RequestParam(required = false) MultipartFile file,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String type) {
        if (file == null || file.isEmpty() || name == null || name.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File and Name are required");
        }
        return ResponseEntity.ok(fileService.saveFile(file, name, type));
    }

    @Operation(summary = "Get all uploaded files", description = "Lấy danh sách tất cả các tệp đã được tải lên")
    @GetMapping("/get-all")
    public ResponseEntity<List<File>> getAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }
}
