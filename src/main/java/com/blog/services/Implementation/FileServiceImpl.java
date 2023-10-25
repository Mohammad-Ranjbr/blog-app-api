package com.blog.services.Implementation;

import com.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile multipartFile) throws IOException {
        // File name
        String name = multipartFile.getOriginalFilename();
        // Random name generate for file
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(Objects.requireNonNull(name).substring(name.lastIndexOf(".")));
        // Full path             It sets the appropriate separator relative to the os
        String filePath = path + File.separator + fileName ;
        // Create folder if not created
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }
        // File copy
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream getImage(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName ;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream ;
    }
}
