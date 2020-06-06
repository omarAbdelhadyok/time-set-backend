package com.omar.time.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesService {

    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    
    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is not found");
        }
        Path fileNameAndPath = null;
        String fileName = null;
        try {
        	long date = System.currentTimeMillis();;
        	fileName = date + file.getOriginalFilename();
        	fileNameAndPath = Paths.get(uploadDirectory, fileName);
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong");
		}
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (Exception e) {
        	throw new RuntimeException("Something went wrong");
        }
        return fileName;
    }

}
