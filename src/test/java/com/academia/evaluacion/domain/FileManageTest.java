package com.academia.evaluacion.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.multipart.MultipartFile;

import com.academia.evaluacion.domain.exceptions.FileException;
import com.academia.evaluacion.domain.services.FileManager;
import com.academia.evaluacion.domain.services.FileValidation;

@ExtendWith(MockitoExtension.class)
class FileManageTest {
	
	@Mock
    private FileValidation fileValidation;

    @InjectMocks
    private FileManager fileManager;
	
	private String fileName_valid	= "alfa123456_anverso_2024-03-05.jpg";
	
	@Test
	void when_saveImage_isValid_then_Ok() throws FileException {
		
		MultipartFile fileMock = mock(MultipartFile.class);
        when(fileMock.getOriginalFilename()).thenReturn(fileName_valid);
        when(fileMock.isEmpty()).thenReturn(false);
        when(fileMock.getContentType()).thenReturn("image/jpeg");
        
        assertDoesNotThrow(() -> fileManager.saveImage(fileMock));
        verify(fileValidation).doValidation(anyString());
    }
	
	@Test
	void when_saveImage_FileIsEmpty_throwFileExceptions() throws FileException {
		
		MultipartFile fileMock = mock(MultipartFile.class);
        when(fileMock.isEmpty()).thenReturn(true);
        
    	assertThrows(FileException.class, () -> fileManager.saveImage(fileMock));
    	verify(fileValidation, never()).doValidation(anyString());
	}
	
	
	@Test
    void when_saveImage_FileIsNotImage_throwFileExceptions() throws Exception {
        
        MultipartFile fileMock = mock(MultipartFile.class);
        when(fileMock.getOriginalFilename()).thenReturn("alfa123456_anverso_2024-03-14.txt");
        when(fileMock.isEmpty()).thenReturn(false);
        when(fileMock.getContentType()).thenReturn("text/plain");

        assertThrows(FileException.class, () -> fileManager.saveImage(fileMock));
        verify(fileValidation, never()).doValidation(anyString());
    }
	
}
