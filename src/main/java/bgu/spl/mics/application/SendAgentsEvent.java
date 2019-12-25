package bgu.spl.mics.application;
import java.util.*;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Squad;
import javafx.util.Pair;

public class SendAgentsEvent implements Event<Boolean> { //first   List<string>, second int
    private List<String> serials;
    private int time;
    public SendAgentsEvent(List<String> serials,int time)
    {
        this.serials=serials;
        this.time=time;
    }


    public List<String> getSerials() {
        return serials;
    }
    public int getTime() {
        return time;
    }

}
