package com.academia.evaluacion.application.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.academia.evaluacion.domain.exceptions.FileException;
import com.academia.evaluacion.domain.services.FileManager;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FileController implements IFileController {
	
	@Autowired
	private FileManager fileManager;
	
	@Operation(summary="Metodo para la carga de archivos de tipo imagen.")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) throws FileException {
		
		fileManager.saveImage(file);
		
		return new ResponseEntity<>("Imágen cargada exitosamente.", HttpStatus.CREATED);
		
    }
	
	@Operation(summary="ExceptionHandler del controlador [DocumentsController]")
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handlerException(Exception exception){
		
		log.error("[documentController.handlerException]: Error captado | Error: "+exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("[documentController.handlerException]: Error captado | Error: "+exception);
		
	}


}
