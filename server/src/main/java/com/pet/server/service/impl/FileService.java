package com.pet.server.service.impl;

import com.pet.server.service.IFileService;
import com.pet.server.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService implements IFileService {

    @Value("${data.path}")
    private String dataPath;

    @Override
    public String uploadFile(MultipartFile file, String namePath) {
        String subFolder = DateUtils.format("yyyy/mm/dd");
        checkSubFolder(subFolder);
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String nameFile = DateUtils.format("yyyymmddHHMMss"+namePath+ext);
        File nFile = new File(dataPath + subFolder + "/" + nameFile);
        try {
            file.transferTo(nFile);
            return subFolder + "/" + nameFile;
        } catch (IOException e) {
            return null;
        }
    }

    private void checkSubFolder(String mSubFolder){
        File theMedia = new File(dataPath +mSubFolder);
        if (!theMedia.exists()){
            theMedia.mkdirs();
        }
    }
}
