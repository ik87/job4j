package ru.job4j.array;

import java.util.Arrays;

/**
*Remove duplicate
*
*@author Kosolapov Ilya (d_dexter@mail.ru)
*@version $ID$
*@since 0.1
*/
public class ArrayDuplicate {
	/**
	*Remove duplicate from string array.
	*@param data array with duplicate.
	*@return array without duplicate.
	*/
	public String[] remove(String[] data) {
		int length = data.length;
		for (int i = 0; i < length; i++) {
			for (int j = i + 1; j < length; j++) {
				if (data[i].equals(data[j])) {
					String tmp = data[j];
					data[j] = data[length - 1];
					data[length - 1] = tmp;
					length--;
					i--;
				}
			}
		}
		return Arrays.copyOf(data, length);
	}
}