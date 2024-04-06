package com.academia.domain.services;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.academia.domain.exceptions.FileException;
import com.academia.domain.interfaces.IValidate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileValidation implements IValidate {
	@Override
	public void doValidation (String file) throws FileException {
		log.info("Archivo recibido:{}", file);
		
		this.fileNameRegex_Validation(file);
		this.fileExtension_Validation(file);
		
		// Separando nomenclatura del archivo
		String[]	fileNameSlice	= this.getNameSlices(file);
		String		fileDateString	= fileNameSlice[2].substring(0, fileNameSlice[2].indexOf("."));
		
		this.fileUUID_Validation(fileNameSlice[0]);
		this.fileImage_Validation(fileNameSlice[1]);
		this.fileDate_Validation(fileDateString);
	}
	
	private String[] getNameSlices(String fileName) throws FileException {
		
		String[] filenameSplices = fileName.split("_");
		
		if (filenameSplices.length == 3) {
			log.info("[getNameSlices]: OK");
			return filenameSplices;
			
		} else {
			throw new FileException("[getNameSlices]: El nombre del archivo no fue segmentado correctamente.");
		}
	}
	
	
	private void fileNameRegex_Validation(String filename) throws FileException {
		Pattern patron	= Pattern.compile(IValidate.fileNameRegex);
	    Matcher matcher = patron.matcher(filename);
	    
	    Boolean result	= matcher.matches();
	    
	    if (Boolean.FALSE.equals(result)) {
            throw new FileException("[filenameRegexValidation]: El nombre del archivo no cumple con el patrón indicado.");
        }
	    
	    log.info("[fileNameRegexValidation]: OK");
	}
	
	private void fileExtension_Validation(String fileName) throws FileException {
		  Set<String> validExtensions = new HashSet<>(Arrays.asList("jpg", "jpeg", "png"));
		  
		  String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		  
		  log.info("[fileExtensionValidation]: {}", extension);
		  
		  if (validExtensions.contains(extension)) {
			  log.info("[fileExtensionValidation]: La extension del archivo es invalida.");
			  throw new FileException("[fileExtensionValidation]: La extension del archivo es invalida.");
		  }
	}
	
	private void fileUUID_Validation(String fileName) throws FileException {
		
		try {
			String uuidResult = UUID.fromString(fileName).toString();
			log.info("[fileUUIDValidation]: {}", uuidResult);
			
		} catch(IllegalArgumentException e) {
			log.error("[fileUUIDValidation]: Invalido. | Error: {}", e);
			throw new FileException("[fileUUIDValidation]: Invalido. | Error: "+e);
		}
		
	}
	
	private void fileImage_Validation(String filename) throws FileException {
	    Set<String> validDocs = new HashSet<>(Arrays.asList("anverso", "reverso", "selfie"));

	    if (validDocs.contains(filename)) {
	    	log.info("[fileCustomerDocValidation]: {}", validDocs.contains(filename));
	    } else {
	    	log.error("[fileCustomerDocValidation]: Invalido");
			throw new FileException("[fileCustomerDocValidation]: Tipo de documento inválido. | Error: " + filename);
	    }
	}
	
	private void fileDate_Validation(String fileDate) throws FileException {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    sdf.setLenient(false);
	    
	    try {
	    	Date parsedDate = sdf.parse(fileDate);
	    	sdf.format(parsedDate);
		} catch (Exception e) {
			throw new FileException("[fileDateReviewValidation]: Fecha del nombre de documento inválida. | Error: " + e);
		}
	}
	
	

}
