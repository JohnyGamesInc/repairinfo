package com.atmcasesapp.controllers;

import com.atmcasesapp.exceptions.StorageException;
import com.atmcasesapp.services.AtmCasesService;
import com.atmcasesapp.services.FileService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StorageController {
    @Autowired
    private FileService fileService;
    @Autowired
    private AtmCasesService casesService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        fileService.storeFile(file);
        var atmCases = fileService.parseXls(FileService.rootLocation.toString() + "/" + file.getOriginalFilename());
        casesService.saveAllCases(atmCases);

        redirectAttributes.addFlashAttribute(
                "message",
                String.format("You successfully uploaded %s with %d records",
                        file.getOriginalFilename(), atmCases.size()));

        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteAllData(RedirectAttributes redirectAttributes) {
        casesService.deleteAllCases();
        redirectAttributes.addFlashAttribute(
                "message",
                "All data was successfully deleted");

        return "redirect:/";
    }

    @ExceptionHandler(StorageException.class)
    public String handleStorageFileNotFound(StorageException exc,
                                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(
                "message",
                "Choose file first");
        return "redirect:/";
    }
}
