package com.pet.server.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String uploadFile(MultipartFile file, String namePath);
}
