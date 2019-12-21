package bgu.spl.mics.application;

import bgu.spl.mics.Event;

public class TickBroadcast implements Event<Integer> {

    private Integer now = 0;

    public Integer getTime(){ return now;}
}
