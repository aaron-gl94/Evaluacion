package com.academia.evaluacion.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.academia.evaluacion.domain.exceptions.FileException;
import com.academia.evaluacion.domain.services.FileValidation;

@ExtendWith(MockitoExtension.class)
class FileValidationTest {

	@InjectMocks
	FileValidation fileValidation;
	
	private String fileName_valid	= "alfa123456_anverso_2024-03-05.jpg";
	
	@Test
	void when_doValidation_then_Ok() throws FileException {
		assertDoesNotThrow(() -> {
			fileValidation.doValidation(this.fileName_valid);
		});
	}
	
	@Test
	void when_doValidation_filename_isInvalid_then_Error() throws FileException {

		String fileName_invalid = "test!_otro_2024-13-05.jpg";
		
		assertThrows(FileException.class, () -> fileValidation.doValidation(fileName_invalid));
	}
	
	@Test
    void when_getNameSlices_InvalidFileName_WrongFormat() throws FileException {
        String invalidFileName = "ABC12345678";
		
        assertThrows(FileException.class, () -> fileValidation.getNameSlices(invalidFileName));
    }

	@Test
	void when_fileNameRegexValidation_isInvalid_then_ThrowsException() throws FileException {
		String fileName_invalid = "test!_anverso_2024-13-05.jpg";
		
		assertThrows(FileException.class, () -> fileValidation.doValidation(fileName_invalid));
	}
	
	@Test
	void when_fileExtensionValidation_isInvalid_then_ThrowsException() throws FileException {
		String fileName_invalid = "alfa123456_anverso_2024-04-05.pdf";
		assertThrows(FileException.class, () -> fileValidation.fileExtension_Validation(fileName_invalid));
	}
	
	@Test
	void when_fileIdValidation_isInvalid_then_ThrowsException() throws FileException {
		String id_invalid = "alfa_123456";
		assertThrows(FileException.class, () -> fileValidation.fileId_Validation(id_invalid));
	}
	
	@Test
	void when_fileDocValidation_isInvalid_then_ThrowsException() throws FileException {
		assertThrows(FileException.class, () -> fileValidation.fileDoc_Validation("otro"));
	}
	
	@Test
	void when_fileDateValidation_isInvalid_then_ThrowsException() throws FileException {
		String date_invalid = "05-04-2024";
		assertThrows(FileException.class, () -> fileValidation.fileDate_Validation(date_invalid));
	}
	
}
