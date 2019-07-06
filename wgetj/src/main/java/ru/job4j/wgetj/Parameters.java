package ru.job4j.wgetj;

import java.net.URL;

class Parameters {
    private URL url;
    private int maxSpeed;

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getMaxSpeed() {
        return maxSpeed * 1000;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}