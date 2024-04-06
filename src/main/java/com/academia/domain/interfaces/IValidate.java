package com.academia.domain.interfaces;

import com.academia.domain.exceptions.FileException;

public interface IValidate {
	
	String	fileNameRegex = "^([a-zA-Z0-9]{10})(?:_(anverso|reverso|selfie))?_([0-9]{4}-[0-9]{2}-[0-9]{2})\\.(jpg|jpeg|png)$";

	void	doValidation(String file) throws FileException;

}
