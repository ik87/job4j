package ru.job4j.wgetj;

import java.net.URL;

class Parameters {
    URL url;
    int maxSpeed;

     Parameters(URL url, int maxSpeed) {
        this.url = url;
        this.maxSpeed = maxSpeed * 1000;
    }
}