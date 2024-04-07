package com.academia.evaluacion.domain.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.academia.evaluacion.domain.exceptions.FileException;
import com.academia.evaluacion.domain.interfaces.IFileManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileManager implements IFileManager {
	
	@Autowired
	private FileValidation fileValidation;
	
	@Override
	public void saveImage(MultipartFile file) throws FileException {
		
		String filename = "";
		
		if (file.isEmpty()) {
			log.error("[saveImage.file.isEmpty]: Archivo no existente.");
			throw new FileException("[saveImage.file.isEmpty]: Archivo no existente.");
		}
		
		filename = file.getOriginalFilename();
		isImage(file);
		
		log.info("[saveImage.file.getOriginalFilename]: {}", filename);
		log.info("[saveImage.file.getContentType]: {}", file.getContentType());
		log.info("[saveImage.file.getSize]: {} bytes", file.getSize());
		
		try {
			fileValidation.doValidation(filename);
		} catch (Exception e) {
			log.error("[saveImage.fileValidation.doValidation]: La validación rechazo este archivo. | Error: " + e);
			throw new FileException("[saveImage.fileValidation.doValidation]: La validación rechazo este archivo. | Error: " + e);
		}
	}
	
	
	private void isImage(MultipartFile file) throws FileException {    	
    	boolean result = Arrays.asList("image/jpeg", "image/png").contains(file.getContentType());
    	
    	if (!result) {
            throw new FileException("[fileManager.isImage]: El archivo no es una imagen.");
        }
    }
	
	

}
