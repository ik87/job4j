package ru.job4j.concurrency_unblocking;
/**
 * @author Kosolapov Ilya (d_dexter@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Base {
   int id;
   int version;

   public Base(int id, int version) {
      this.id = id;
      this.version = version;
   }
}
