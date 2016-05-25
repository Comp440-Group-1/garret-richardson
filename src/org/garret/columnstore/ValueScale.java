package org.garret.columnstore;

import java.util.ArrayList;
import java.util.Collections;

public class ValueScale {
	private int scale;
	private int offset;
	private int min;
	private int max;
	
	public ValueScale(Column col){
		if (col.colType.equals("Integer")){
			ArrayList<Integer> values = new ArrayList<Integer>();
			for (int i = 0; i < col.data.size(); i++){
				values.add(Integer.parseInt(col.data.get(i)));
			}
			
			min = Collections.min(values);
			max = Collections.max(values);
			
			if (values.size() > 1){
				scale = (min - max) / ((int) Math.pow(2, values.size() - 1) );
			} else {
				scale = 1;
			}
			
			offset = min + ((int) Math.pow(2, values.size()) - 1)*scale;
			
			
			for (int i = 0; i < col.data.size(); i++){
				values.set(i, Math.round((values.get(i) - offset)/(float) scale));
				col.data.set(i, Integer.toString(values.get(i)));
			}
		} else {
			System.err.println("Wrong type for ValueScale column, not integer");
		}
	}
	
	public void decompress(Column col){
		for (int i = 0; i < col.data.size(); i++){
			int packedVal = Integer.parseInt(col.data.get(i));
			int unpackedVal = packedVal * scale + offset;
			col.data.set(i, Integer.toString(unpackedVal));
		}
	}
}
