package org.garret.columnstore;

import java.util.*;
import java.util.zip.Deflater;
import java.io.*;

import com.opencsv.CSVReader;

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
	
	public static String[][] processCSVFile(File file) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(file));
		String [] nextLine;
		nextLine = reader.readNext();
		int columnCount = nextLine.length;
		int lineCount = countLines(file);
		String[][] data = new String [columnCount][lineCount];
		
		for (int row = 0; row < lineCount; row++){
			for (int col = 0; col < columnCount; col++){
				data[col][row] = nextLine[col];
			}
			nextLine = reader.readNext();
			if (nextLine == null){
				break;
			}
		}
		
		reader.close();
		
		return data;
	}
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\Allan\\Desktop\\TestCSVprint.csv");
		String [][] data = processCSVFile(file);
		
		System.out.println("--- Pivoted columns: ---");
		for (int col = 0; col < data.length; col++){
			System.out.println(Arrays.toString(data[col]));
		}
	}
}
