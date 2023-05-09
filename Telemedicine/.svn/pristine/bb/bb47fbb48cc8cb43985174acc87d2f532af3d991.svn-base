package com.nsdl.ndhm.utility;

import java.io.File;

public class FileUtility {
	
	private FileUtility() {
		throw new IllegalStateException("Utility class");
	}

	public static File[] getResourceFolderFiles(String folder) {
		return new File(Thread.currentThread().getContextClassLoader().getResource(folder).getPath()).listFiles();
	}
}
