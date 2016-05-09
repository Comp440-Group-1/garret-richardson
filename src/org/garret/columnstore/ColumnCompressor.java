package org.garret.columnstore;

import java.util.ArrayList;

public class ColumnCompressor {	
	public static NaiveDictionary dictionaryCompressColumn(Column col){
		NaiveDictionary dic = new NaiveDictionary();
		for (int i = 0; i < col.data.size(); i++){
			col.data.set(i, Integer.toString(dic.addToDictionary(col.data.get(i))));
		}
		return dic;
	}
	
	
	
	
}
