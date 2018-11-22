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
	*@param remove array with duplicate.
	*@return array without duplicate.
	*/
	public String[] remove(String[] remove) {
		int num = remove.length; 
		for (int i = 0; i < num; i++) {
			for (int j = i + 1; j < num; j++) {
				if (remove[i].equals(remove[j])) {
					String tmp = remove[j];
					remove[j] = remove[num - 1];
					remove[num - 1] = tmp;
					num--;
					i--;
				}
			}
		}
		return Arrays.copyOf(remove, num);
	}
}