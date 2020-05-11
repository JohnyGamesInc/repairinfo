package com.atmcasesapp.services;

import com.atmcasesapp.entity.AtmCase;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public interface FileService {
    Path rootLocation = Paths.get("src/main/resources/tmp");

    void storeFile(MultipartFile file);

    List<AtmCase> parseXls(String path);
}
