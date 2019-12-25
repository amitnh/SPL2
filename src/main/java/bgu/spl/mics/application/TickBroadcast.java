package bgu.spl.mics.application;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {

    private Integer now = 0;

    public void Tick(){
        now = now+1;
        System.out.println(now);
    }
    public Integer getTime(){ return now;}
}
