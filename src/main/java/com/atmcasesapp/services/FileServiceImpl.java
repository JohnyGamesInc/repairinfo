package com.atmcasesapp.services;

import com.atmcasesapp.entity.AtmCase;
import com.atmcasesapp.exceptions.StorageException;
import com.atmcasesapp.utils.XLSParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;

@Slf4j
@Service
@Data
public class FileServiceImpl implements FileService {

    @Override
    @Transactional
    public void storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        File filePath = new File(rootLocation + "/" + filename);
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file ");
        }
        if (filename.contains("..")) {
            // This is a security check
            throw new StorageException(
                    "Cannot store file with relative path outside current directory "
                            + filename);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream,
                    filePath.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }
    }

    public List<AtmCase> parseXls(String path) {
        return XLSParser.parseXls(path);
    }
}
