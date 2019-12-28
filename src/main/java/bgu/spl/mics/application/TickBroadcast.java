package bgu.spl.mics.application;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {


    public TickBroadcast(long now){
        this.now = now;
    }
    private static long now = 0;


    public long getTime(){ return now;}
}
