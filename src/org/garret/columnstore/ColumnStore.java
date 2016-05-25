package org.garret.columnstore;

import java.util.*;
import java.util.zip.Deflater;
import java.io.*;

import org.garret.columnstore.NaiveDictionary;

import com.opencsv.CSVReader;

public class ColumnStore {
	
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
	
	/**
	 * This function pulls in the CSV file and formats it as a 2D array to make operations on it easier.
	 * It makes assumptions that the CSV file isn't empty and that all columns are filled out to match the
	 * length of the header row, or aren't longer.
	 * 
	 * @param file - The comma-delimited file object to be turned into a 2D array
	 * @return String[][]
	 * @throws IOException
	 */
	public static String[][] processCSVFile(File file) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(file));
		String [] nextLine;
		nextLine = reader.readNext();
		int columnCount = nextLine.length;
		int lineCount = countLines(file);
		String[][] data = new String [columnCount][lineCount];
		
		/**
		 * We're going to pivot things so we have the first index be the column rather than row.
		 * This slows down the reading process, but makes it easier to pass columns to compression
		 * functions, and more closely mimics the way they are held in SQL server anyways.
		 */
		
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
	
	public static void printOptions(){
		System.out.println("Type an option, followed by a newline: ");
		System.out.println("0 - print all sample data (before compression)");
		System.out.println("1 - dictionary compress a column, print dictionary");
		System.out.println("2 - dictionary decompress a column, show result");
		System.out.println("3 - value compress a column (Integer type only), show result");
		System.out.println("4 - value decompress a column (Integer type only), show result");
		System.out.println("quit - end the program");
	}
	
	
	public static void main(String[] args) throws IOException{
		File file = new File("C:\\Users\\Allan\\workspace\\ColumnStore\\sampleData\\TestCSVprint.csv");
		Scanner input = new Scanner(System.in);
		
		String [][] data = processCSVFile(file);
		
		System.out.println("--- Pivoted columns: ---");
		printDataArray(data);
		
		
		Column [] columns = new Column[data.length];
		int [] beforeSize = new int[data.length];
		int [] afterSize = new int[data.length];
		NaiveDictionary [] dictionaries = new NaiveDictionary[data.length];
		
		for (int col = 0; col < data.length; col++){
			columns[col] = new Column(data[col]);
		}
		
		System.out.println("Columnstore Compression");
		printOptions();
		String in;
		while (!(in = input.nextLine()).equals("quit")){
			switch (in){
				case "0":
					printDataArray(data);
					break;
				case "1":
					System.out.println("Enter the column index (starting at 0): ");
					in = input.nextLine();
					if (in.equals("quit")){
						break;
					}
					
					try {
						int col = Integer.parseInt(in);
						System.out.println("Column " + col + " selected.");
						if (col < columns.length && col >= 0){
							dictionaries[col] = ColumnCompressor.dictionaryCompressColumn(columns[col]);
							dictionaries[col].printDictionary();
							columns[col].printColumn();
							System.out.println("Compressed size: " + columns[col].estimateSize());
						} else {
							System.out.println("Invalid column index entered");
						}
					} catch (NumberFormatException e){
						System.out.println("Invalid column index entered");
					}
					break;
				case "2":
					System.out.println("Enter the column index (starting at 0): ");
					in = input.nextLine();
					if (in.equals("quit")){
						break;
					}
					
					try {
						int col = Integer.parseInt(in);
						if (col < columns.length && col >= 0){
							dictionaries[col].dictionaryDecompress(columns[col]);
							columns[col].printColumn();
							System.out.println("Decompressed size: " + columns[col].estimateSize());
						} else {
							System.out.println("Invalid column index entered");
						}
					} catch (NumberFormatException e){
						System.out.println("Invalid column index entered");
					}
					break;
			}
		}
		
		/*for (int col = 0; col < data.length; col++){
			columns[col] = new Column(data[col]);
			beforeSize[col] = columns[col].estimateSize();
			System.out.println("-- Uncompressed Size: --" + beforeSize[col]);
			columns[col].printColumn();
			dictionaries[col] = ColumnCompressor.dictionaryCompressColumn(columns[col]);
			afterSize[col] = columns[col].estimateSize();
			System.out.println("-- Compressed Size: --" + afterSize[col]);
			columns[col].printColumn();
			System.out.println("Compression ratio (compressed/uncompressed): " 
					+ ((float) afterSize[col]/ (float) beforeSize[col]));
			System.out.println("-- Dictionary: --");
			dictionaries[col].printDictionary();
		}*/
		
		System.out.println("Closing...");
	}
}
