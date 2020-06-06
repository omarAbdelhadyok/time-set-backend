package com.omar.time.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.omar.time.service.FilesService;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

	
    @Autowired
    private FilesService filesService;

    @RequestMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        return filesService.saveFile(file);
    }
	
}
