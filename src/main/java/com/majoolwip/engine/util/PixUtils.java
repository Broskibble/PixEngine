/*
 * Copyright (c) 2018.  Created by Ryan Moore
 */

package com.majoolwip.engine.util;


public class PixUtils {
	public static long getTotalMemory() {
		return Runtime.getRuntime().totalMemory();
	}

	public static long getUsedMemory() {
		Runtime t = Runtime.getRuntime();
		return t.totalMemory() - t.freeMemory();
	}
}
