package com.academia.evaluacion.domain.interfaces;

import org.springframework.web.multipart.MultipartFile;

import com.academia.evaluacion.domain.exceptions.FileException;

public interface IFileManager {

	void saveImage(MultipartFile file) throws FileException;

}
