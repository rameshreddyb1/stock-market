package com.app.stock.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorType {

	private String messsge;
	private String code; 
}