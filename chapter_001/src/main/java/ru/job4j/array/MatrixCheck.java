package ru.job4j.array;
/**
*Check matrix
*
*@author Kosolapov Ilya (d_dexter@mail.ru)
*@version $ID$
*@since 0.1
*/
public class MatrixCheck {
	/**
	*Check matrix diagonal
	*@param data matrix
	*@return check
	*/
	public boolean mono(boolean[][] data) {
		boolean result = true;
		int size = data.length - 1;
		for (int i = 1; i <= size; i++) {
			if (data[0][0] != data[i][i]) {
				result = false;
				break;
			}
			if (data[0][size] != data[i][size - i]) {
				result = false;
				break;
			}
		}
		return result;
	}
}