package org.garret.columnstore;

import java.util.*;
import java.util.zip.Deflater;
import java.io.*;

import com.opencsv.CSVReader;

public class ColumnStore {
	
	public static String addToDictionary(String s, ArrayList<String> dictionary){
		int location = dictionary.indexOf(s);
		if (location == -1){
			dictionary.add(s);
			location = dictionary.size() - 1;
		}
		
		return Integer.toString(location);
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
	
	public static void printDataArray(String [][] data){
		for (int col = 0; col < data.length; col++){
			System.out.println(Arrays.toString(data[col]));
		}
	}
	
	public static void printDictionaries(ArrayList<ArrayList<String>> dictionaries){
		for (int i = 0; i < dictionaries.size(); i++){
			System.out.println("\nDictionary for col # " + i);
			ArrayList<String> d = dictionaries.get(i); 
			System.out.println("Key:\t\tValue:");
			for (int j = 0; j < d.size(); j++){
				System.out.println(j + "\t\t" + d.get(j));
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		File file = new File("C:\\Users\\Allan\\Desktop\\TestCSVprint.csv");
		String [][] data = processCSVFile(file);
		
		System.out.println("--- Pivoted columns: ---");
		//printDataArray(data);
		
		
		ArrayList<ArrayList<String>> dictionaries = new ArrayList<ArrayList<String>>();
		for (int col = 0; col < data.length; col++){
			System.out.println(Arrays.toString(data[col]));
			ArrayList<String> dictionary = new ArrayList<String>();
			for (int row = 1; row < data[col].length; row++){
				data[col][row] = addToDictionary(data[col][row], dictionary);
			}
			dictionaries.add(dictionary);
		}
		
		System.out.println("--- Dictionary compressed ---");
		printDataArray(data);
		printDictionaries(dictionaries);
		
		
	}
}
