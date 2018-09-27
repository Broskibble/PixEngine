/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine.util;

public class IniFormatException extends Exception {
	private String filePath;
	public IniFormatException(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String getMessage() {
		return "Error in ini file format, file: " + filePath;
	}
}
