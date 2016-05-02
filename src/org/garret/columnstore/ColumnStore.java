package org.garret.columnstore;

import java.util.*;
import java.util.zip.Deflater;
import java.io.*;

public class ColumnStore {
	
	private class DictionaryEncoder {
		Map<String, Integer> dictionary = new Hashtable<String, Integer>();
		
	}
	
	
	public static int countLines(File file) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(file));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
	
	public static List<String[]> processCSVFile(File file) throws FileNotFoundException{
		List<String[]> csvData = null;
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		int lineCount = countLines(file);
		try {
			while ((line = br.readLine()) != null){
				String[] columns = line.split(",");
				System.out.println(Arrays.toString(columns));				
			}
			Deflater compressor = new Deflater();
			compressor.setInput(b);
			br.close();
		} catch (IOException e){
			e.printStackTrace();
		} 
		return csvData;
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\Allan\\Desktop\\TestCSVprint.csv");
		processCSVFile(file);
	}
}
