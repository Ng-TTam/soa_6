package com.example.tax.Utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Utility {
	public static List<Long> getNumberFromString (String income){
		String[]numberStr = income.split(" ");
		List<Long>numbers = new ArrayList<>();
		for(String str : numberStr) {
			if (str.matches("\\d+")) {
				long num = Long.parseLong(str);
				numbers.add(num*1000000);
			}
		}
		if (numbers.size() == 1) {
			if (numbers.get(0) == 5000000)numbers.add(0, (long) 0);
			else numbers.add(1, (long) 1000000000);
		}
		return numbers;
	}
}
