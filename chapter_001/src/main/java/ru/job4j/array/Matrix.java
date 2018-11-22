package ru.job4j.array;
/**
*Build Matrix
*
*@author Kosolapov Ilya (d_dexter@mail.ru)
*@version $ID$
*@since 0.1
*/
public class Matrix {
	/**
	*Multiple matrix
	*@param size size matrix
	*@return matrix
	*/
	public int[][] multiple(int size) {
		int[][] data = new int[size][size];
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				data[y][x] = (y + 1) * (x + 1); 
			}
		}
		return data;
	}
}