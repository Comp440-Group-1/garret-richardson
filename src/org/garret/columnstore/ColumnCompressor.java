package org.garret.columnstore;

import java.util.ArrayList;

public class ColumnCompressor {
	private class Dictionary {
		private ArrayList<String> dictionary;
		
		public Dictionary(){
			dictionary = new ArrayList<String>();
		}
		
		public int addToDictionary(String value){
			int location = dictionary.indexOf(value);
			if (location == -1){
				dictionary.add(value);
				location = dictionary.size() - 1;
			}
			return location;
		}
		
		public void printDictionary(){
			System.out.println("Key:\t\tValue:");
			for (int i = 0; i < dictionary.size(); i++){
				System.out.println(i + "\t\t" + dictionary.get(i));
			}
		}
		
		public String getValue(int i){
			return dictionary.get(i);
		}
	}
	
	private class DictionaryCompressedColumn extends Column{
		private Dictionary dictionary;
		
		public DictionaryCompressedColumn(String[] columnData) {
			super(columnData);
			// TODO Auto-generated constructor stub
			
		}
		
		/**
		 * Compresses a single column in-place using a naive dictionary compression.
		 * 
		 * @param column The Column object to compress the data members of.
		 * @return
		 */
		public Column dictionaryCompress(Column column){
			
			return column;
		}
	}
	
	
	
	
}
