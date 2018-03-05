package com.uhmtech.reader.utils;

import android.content.Context;

public class AppTool {
	static {
		try {
			System.loadLibrary("apptool");
		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
    }
	
	public static native String getMD5String(Context context,String str);
}
