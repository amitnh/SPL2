package bgu.spl.mics.application;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {

    private double tick;

    public TickBroadcast(double tick){
        this.tick = tick;
    }
    private static Integer now = 0;


    public Integer getTime(){ return now;}
}
