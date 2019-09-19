package fileoperations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class FileCleanup {

	File tempFile = null;
	String filename = "";
	String filepath = "";
	String folderpath = "";
	String backupfilename = "";
	FileReader reader = null;

	public void listOfFiles() throws FileNotFoundException, IOException {

		Properties p = new Properties();
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		p.load(classLoader.getResourceAsStream("Configuration.properties"));
		folderpath = p.getProperty("FolderPath");
		backupfilename = p.getProperty("BackupFileName");

		System.out.println("folderpath:" + folderpath);
		System.out.println("BackupFileName:" + backupfilename);

		File folder = new File(folderpath);
		File backupFolder = new File(folderpath + File.separator + backupfilename);

		if (!backupFolder.exists()) {
			System.out.println("creating directory: " + backupFolder.getName());
			boolean result = false;

			try {
				backupFolder.mkdir();
				result = true;
			} catch (SecurityException se) {
				se.printStackTrace();
			}
			if (result) {
				System.out.println("DIRECTORY created");
			}
		}

		File[] listOfFiles = folder.listFiles();
		filepath = folder.getAbsolutePath();
		System.out.println("filepath:" + filepath);
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
				filename = listOfFiles[i].getName();
				System.out.println("filename:" + filename);
				checkFileSize(listOfFiles[i]);

			}
		}

	}

	public void checkFileSize(File tempFile) {

		System.out.println("inside checkFileSize()");

		double fileSize = getFileSizeKiloBytes(tempFile);
		long approxsize = Math.round(fileSize);
		System.out.println(approxsize + " bytes");

		if (approxsize > 2048) {

			System.out.println("File size exceeds expected size of 2MB");
			renameFile(tempFile);
		} else {
			System.out.println("File size is as per expected size of below 2MB");
		}

	}

	public void renameFile(File tempFile) {

		System.out.println("inside renameFile()");

		String[] fileNameSplits = filename.split("\\.");

		int extensionIndex = fileNameSplits.length - 1; // extension is assumed
														// to be the last part
		String filename_wo_extn = getFileNameWithoutExtention(filename);

		String currentDatetime = getCurrentDateTime();

		File renamedFile = new File(filepath + File.separator + backupfilename + File.separator + filename_wo_extn + "_"
				+ currentDatetime + "." + fileNameSplits[extensionIndex]);
		boolean renamestatus = tempFile.renameTo(renamedFile);

		System.out.println("renamestatus:" + renamestatus);
		if (renamestatus) {

			try {
				new File(filepath + File.separator + filename).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// and create the new file with previous file name

	private static double getFileSizeKiloBytes(File file) {
		return (double) file.length() / 1024;
	}

	private static String getFileNameWithoutExtention(String filename) {

		if (filename.indexOf(".") > 0) {
			return filename.substring(0, filename.lastIndexOf("."));
		} else {
			return filename;
		}
	}

	private static String getCurrentDateTime() {

		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
		Date date = new Date();
		System.out.println(formatter.format(date));
		return formatter.format(date);
	}

	public static void main(String[] args) throws IOException {

		FileCleanup cleanup = new FileCleanup();

		cleanup.listOfFiles();
	}

}
