package com.academia.evaluacion.domain.services;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.academia.evaluacion.domain.exceptions.FileException;
import com.academia.evaluacion.domain.interfaces.IFileValidation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileValidation implements IFileValidation {
	@Override
	public void doValidation (String file) throws FileException {
		log.info("Archivo recibido:{}", file);
		
		this.fileNameRegex_Validation(file);
		this.fileExtension_Validation(file);
		
		String[]	fileNameSlice	= this.getNameSlices(file);
		String		fileDateString	= fileNameSlice[2].substring(0, fileNameSlice[2].indexOf("."));
		
		this.fileId_Validation(fileNameSlice[0]);
		this.fileDoc_Validation(fileNameSlice[1]);
		this.fileDate_Validation(fileDateString);
	}
	
	public String[] getNameSlices(String fileName) throws FileException {
		
		String[] filenameSplices = fileName.split("_");
		
		log.info("Segmentación: {}", filenameSplices.length);
		
		if (filenameSplices.length == 3) {
			log.info("[getNameSlices]: OK");
			return filenameSplices;
			
		} else {
			log.error("[getNameSlices]: El nombre del archivo no fue segmentado correctamente.");
			throw new FileException("[getNameSlices]: El nombre del archivo no fue segmentado correctamente.");
		}
	}
	
	
	public void fileNameRegex_Validation(String filename) throws FileException {
		Pattern patron	= Pattern.compile(fileNameRegex);
	    Matcher matcher = patron.matcher(filename);
	    
	    Boolean result	= matcher.matches();
	    
	    if (Boolean.FALSE.equals(result)) {
	    	log.error("[filenameRegex_Validation]: El nombre del archivo no cumple con el patrón indicado.");
            throw new FileException("[filenameRegex_Validation]: El nombre del archivo no cumple con el patrón indicado.");
        }
	    
	    log.info("[fileNameRegex_Validation]: OK");
	}
	
	public void fileExtension_Validation(String fileName) throws FileException {
		  Set<String> validExtensions = new HashSet<>(Arrays.asList("jpg", "jpeg", "png"));
		  
		  String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		  
		  log.info("[fileExtension_Validation]: {}", extension);
		  
		  if (!validExtensions.contains(extension)) {
			  log.error("[fileExtension_Validation]: La extension del archivo es invalida.");
			  throw new FileException("[fileExtension_Validation]: La extension del archivo es invalida.");
		  }
	}
	
	public void fileId_Validation(String fileName) throws FileException {
		Pattern patron	= Pattern.compile(IFileValidation.fileIdRegex);
	    Matcher matcher = patron.matcher(fileName);
	    
	    Boolean result	= matcher.matches();
	    
	    if (Boolean.FALSE.equals(result)) {
	    	log.error("[fileIdRegex_Validation]: El Id del archivo no cumple con el patrón indicado.");
	    	throw new FileException("[fileIdRegex_Validation]: El Id del archivo no cumple con el patrón indicado.");
        }
	    
	    log.info("[fileIdRegex_Validation]: OK");
		
	}
	
	public void fileDoc_Validation(String filename) throws FileException {
	    Set<String> validDocs = new HashSet<>(Arrays.asList("anverso", "reverso", "selfie"));

	    if (validDocs.contains(filename)) {
	    	log.info("[fileDoc_Validation]: {}", validDocs.contains(filename));
	    } else {
	    	log.error("[fileDoc_Validation]: Invalido");
			throw new FileException("[fileDoc_Validation]: Tipo de documento inválido. | Error: " + filename);
	    }
	}
	
	public void fileDate_Validation(String fileDate) throws FileException {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    sdf.setLenient(false);
	    
	    try {
	    	Date parsedDate = sdf.parse(fileDate);
	    	sdf.format(parsedDate);
		} catch (Exception e) {
			log.error("[fileDate_Validation]: Fecha del nombre de documento inválida. | Error: " + e);
			throw new FileException("[fileDate_Validation]: Fecha del nombre de documento inválida. | Error: " + e);
		}
	}
	
	

}
