package com.tim.locationreminder.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class IOUtil {

	private IOUtil() {
		
	}
	
	public static void copyFile(String from, String to) throws IOException {
		copyFile(new File(from), new File(to));
	}
	
	public static void copyFile(File from, File to) throws IOException {
		final FileInputStream fis = new FileInputStream(from);
		final FileOutputStream fos = new FileOutputStream(to);
		writeFile(fis, fos);
		closeIOStream(fos);
		closeIOStream(fis);
	}
	
	public static void saveInputStreamAsFile(InputStream inputStream, File target) throws IOException {
		
		final FileOutputStream fos = new FileOutputStream(target);
		writeFile(inputStream, fos);
		closeIOStream(fos);
		closeIOStream(inputStream);
	}
	
	private static void writeFile(InputStream inputStream, OutputStream outputStream) throws IOException {
		final byte[] buf = new byte[1024];
		int len;
		while((len = inputStream.read(buf)) > 0) {
			outputStream.write(buf, 0, len);
		}
		closeIOStream(outputStream);
		closeIOStream(inputStream);
	}
	
	public static void closeIOStream(Closeable stream) {
		if(stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
