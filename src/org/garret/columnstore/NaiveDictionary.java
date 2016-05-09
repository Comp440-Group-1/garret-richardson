package org.garret.columnstore;

import java.util.ArrayList;

public class NaiveDictionary {
	private ArrayList<String> dictionary;
	
	public NaiveDictionary(){
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
