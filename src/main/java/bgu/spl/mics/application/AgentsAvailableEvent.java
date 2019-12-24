package bgu.spl.mics.application;
import java.util.*;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Squad;

public class AgentsAvailableEvent implements Event<> {
    private List<String> serials;
    public AgentsAvailableEvent(List<String> serials)
    {
        this.serials=serials;
    }

    public List<String> getSerials() {
        return serials;
    }
}
