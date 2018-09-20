/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine.util;

import com.majoolwip.engine.Pix;

public class PixLogger {

	private final StringBuilder log = new StringBuilder();

	private final String INFO = "<INFO>: ";
	private final String ERROR = "<ERROR>: ";

	public void info(String message) {
		log.append(INFO).append(message).append('\n');
		if(Pix.getSettings().isDebug()) {
			System.out.println(INFO + message);
		}
	}

	public void error(String message) {
		log.append(ERROR).append(message).append('\n');
		if(Pix.getSettings().isDebug()) {
			System.out.println(ERROR + message);
		}
	}
}
