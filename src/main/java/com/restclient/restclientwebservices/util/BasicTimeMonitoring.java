package com.restclient.restclientwebservices.util;

public class BasicTimeMonitoring {

    private long time;

    public void start(){

        time = System.currentTimeMillis();

    }

    public long stop(){

        return System.currentTimeMillis() - time;

    }

}
