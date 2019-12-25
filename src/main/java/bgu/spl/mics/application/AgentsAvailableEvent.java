package bgu.spl.mics.application;
import java.util.*;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Squad;
import javafx.util.Pair;

public class AgentsAvailableEvent implements Event<List<Object>> { //first boolean second List<string>, third int
    private List<String> serials;
    public AgentsAvailableEvent(List<String> serials)
    {
        this.serials=serials;
    }


    public List<String> getSerials() {
        return serials;
    }
}
