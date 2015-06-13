package com.ssb.droidsound.utils;


public class UnRar {
	private static final String TAG = UnRar.class.getSimpleName();

	private String archive;

	public UnRar(String fileName) {
		archive = fileName;
	}
	
	public boolean extractTo(String target) {
		int rc = N_extractAll(archive, target);
		return rc == 0;
	}

	native int N_extractAll(String archive, String target);	
	native int N_open(String archive);
	native int N_skip();
	native int N_extract(String targetDir);
	native String N_getFileName();
	native int N_getFileSize();

}
