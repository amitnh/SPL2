package bgu.spl.mics.application.passiveObjects;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Squad {

	private Map<String, Agent> agents;
	private static Squad instance = new Squad();
	/**
	 * Retrieves the single instance of this class.
	 */
	public static Squad getInstance() {
		//TODO: Implement this
		return instance;
	}

	/**
	 * Initializes the squad. This method adds all the agents to the squad.
	 * <p>
	 * @param agents 	Data structure containing all data necessary for initialization
	 * 						of the squad.
	 */
	public  void  load (Agent[] agents) { // TODO: check if needed to be synchronized
		// TODO
		this.agents= new HashMap<>();// clears the map
		for (Agent a:agents)
		{
			this.agents.put(a.getSerialNumber(),a);
		}
	}

	/**
	 * Releases agents.
	 */
	public void releaseAgents(List<String> serials){
		// TODO
		for (String s:serials)
		{
			synchronized (s) {
				agents.get(s).release();
			}
		}
	}

	/**
	 * simulates executing a mission by calling sleep.
	 * @param time   milliseconds to sleep
	 */
	public void sendAgents(List<String> serials, int time){
		// TODO Implement this
		try{Thread.sleep(time);}catch (Exception e){};
		releaseAgents(serials);
	}

	/**
	 * acquires an agent, i.e. holds the agent until the caller is done with it
	 * @param serials   the serial numbers of the agents
	 * @return ‘false’ if an agent of serialNumber ‘serial’ is missing, and ‘true’ otherwise
	 */
	public boolean getAgents(List<String> serials){
		// TODO Implement this
		for (String s:serials)
		{
			synchronized (s) {
				Agent tmp = agents.get(s);
				if (tmp == null) {
					for (String i:serials){agents.get(i).release();} // release all agents and then return false
					return false;
				}
				while (!tmp.isAvailable()) {
					try { tmp.wait(); } catch (Exception e) {}
				}
				tmp.acquire();
			}
		}
		return true;
	}

    /**
     * gets the agents names
     * @param serials the serial numbers of the agents
     * @return a list of the names of the agents with the specified serials.
     */
    public List<String> getAgentsNames(List<String> serials){
        // TODO Implement this
		List<String> names= new LinkedList<>();
		for (String s:serials)
		{
			names.add(agents.get(s).getName());
		}
	    return names;
    }

}
