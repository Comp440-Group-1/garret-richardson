package org.garret.columnstore;

import java.util.ArrayList;

public class ColumnCompressor {	
	public static NaiveDictionary dictionaryCompressColumn(Column col){
		NaiveDictionary dic = new NaiveDictionary(col);
		return dic;
	}
	
	public static ValueScale valueCompressColumn(Column col){
		ValueScale valueScale = new ValueScale(col);
		return valueScale;
	}
}
