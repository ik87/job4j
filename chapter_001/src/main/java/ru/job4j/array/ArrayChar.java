package ru.job4j.array;
/**
 *@author Kosolapov Ilya (d_dexter@mail.ru)
 *@version $ID$
 *@since 0.1
 */
public class ArrayChar {
  private char[] data;

  public ArrayChar(String line) {
    this.data = line.toCharArray();
  }

  /**
   *Check the word start with prefix
   *@param prefix prefix
   *@return if word start with prefix
   */
  public boolean startWith(String prefix) {
    boolean result = true;
    char[] value = prefix.toCharArray();

    if (value.length > data.length) {
      result = false;
    } else {
      for (int i = 0; i < value.length; i++) {
        if (value[i] != data[i]) {
          result = false;
          break;
        }
      }
    }
    return result;
  }
}