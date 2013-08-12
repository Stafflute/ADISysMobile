package util;

import android.util.Log;

import java.io.File;

public class FolderManager {

	private FolderManager() {

	}

	public static void insertFolderIfNotExists(String folder) {
		File foldFile = new File(folder);
		
		if (!foldFile.exists()) {
			
			if(!foldFile.mkdirs()) {
                Log.d("AndroidRuntime", "Error creating " + foldFile.getAbsolutePath());
            }
			
		} else if (foldFile.isFile()) {
			throw new InvalidPathExeption("File instead a folder");
		}
	}
}
