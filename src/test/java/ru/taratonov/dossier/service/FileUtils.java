package ru.taratonov.dossier.service;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {
    public static String getAndReadFromFile(String path) {
        try {
            File file = ResourceUtils.getFile(path);
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
