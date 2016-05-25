package org.garret.columnstore;

import java.util.ArrayList;

public class Column {
	public ArrayList<String> data = null;
	public String header;
	public String colType;
	
	public Column(String[] columnData){
		data = new ArrayList<String>();
		colType = columnData[0];
		header = columnData[1];
		for (int i = 2; i < columnData.length; i++){
			data.add(columnData[i]);
		}
	}
	
	/**
	 * Gives a naive size-estimate of the storage size of the column, assuming each char takes 1 byte.
	 * @return The size estimate in number of bytes, as an integer.
	 */
	public int estimateSize(){
		int size = 0;
		
		for (int i = 0; i < data.size(); i++){
			size += data.get(i).length();
		}
		
		return size;
	}
	
	public void printColumn(){
		System.out.println("-- Column Data --");
		System.out.println("Row:\t\t" + header + ":" + "\t\ttype: " + colType);
		for (int col = 0; col < data.size(); col++){
			System.out.println(col + "\t\t" + data.get(col));
		}
	}
}