package com.academia.evaluacion.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.academia.evaluacion.application.rest.FileController;
import com.academia.evaluacion.domain.exceptions.FileException;
import com.academia.evaluacion.domain.services.FileManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(FileController.class)
@ExtendWith(MockitoExtension.class)
class FileControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private FileManager fileManager;
	
	@Test
    void when_uploadImage_validRequest_returnsCreated() throws Exception {
		
    	String fileNameMock = "alfa123456_anverso_2024-04-05.jpg";
    	
        MockMultipartFile fileImageMock = new MockMultipartFile("image", fileNameMock, "image/jpeg", "Test content for image param".getBytes());
        
        log.info("fileImageMock: {}", fileImageMock);
        
        assertDoesNotThrow(() -> fileManager.saveImage(any()));
        
        mockMvc.perform(multipart("/document/uploadImage")
                .file(fileImageMock)
                .accept(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isCreated())
                .andExpect(result -> result.getResponse().getContentAsString().equalsIgnoreCase("Imágen cargada exitosamente."));
        
    }
	
	@Test
    void when_uploadImage_validRequest_returnsInternalServerError() throws Exception {
		String fileNameMock = "error!_otro_2024-02-31.jpeg";
		
		MockMultipartFile fileImageMock = new MockMultipartFile("image", fileNameMock, "image/jpeg", "Test content for image param".getBytes());
		
		doThrow(new FileException("[fileControllerTest.FileException]: Error simulado")).when(fileManager).saveImage(any());
        
        mockMvc.perform(multipart("/document/uploadImage")
                .file(fileImageMock)
                .accept(MediaType.MULTIPART_FORM_DATA_VALUE))
        		.andExpect(status().isInternalServerError());
    }

}
