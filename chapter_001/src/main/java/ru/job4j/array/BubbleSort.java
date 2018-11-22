package ru.job4j.array;
/**
*Bubble sort
*@author Kosolapov Ilya (d_dexter@mail.ru)
*@version $ID$
*@since 0.1
*/
public class BubbleSort {
  
  /**
  *Bubble sort
  *@param data no sorted array
  *@return sorted array
  */
  public int[] sort(int[] data) {
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data.length - i - 1; j++) {
        if (data[j] > data[j + 1]) {
          int tmp = data[j];
          data[j] = data[j + 1];
          data[j + 1] = tmp;
        }
      }
    }
    return data;
  }
}