package ru.job4j.bridging;

import org.junit.Test;
import java.util.List;

public class BridgingTest {

    List<String> list = List.of(
            "111;123;222",
            "200;123;100",
            "300;;100",
            "400;000;345",
            "111;123;222",
            "200;123;100"
    );



    @Test
    public void var1() {
        Bridging bridging = new Bridging();
        bridging.putRows(list);
        System.out.println(bridging);
    }

}