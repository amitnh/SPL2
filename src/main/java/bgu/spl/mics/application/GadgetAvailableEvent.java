package bgu.spl.mics.application;

import bgu.spl.mics.Event;

public class GadgetAvailableEvent implements Event<Boolean> {
private String gadget;
        public GadgetAvailableEvent(String gadget)
        {
            this.gadget=gadget;
        }

        public String getGadget() {
            return gadget;
        }
}
