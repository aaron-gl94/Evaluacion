package com.academia.evaluacion.application.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.academia.evaluacion.domain.exceptions.FileException;

@RestController
@RequestMapping("/document")
public interface IFileController {

	@PostMapping(value = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	ResponseEntity<String> uploadImage(
			@RequestParam("image") MultipartFile image
	) throws FileException;

}