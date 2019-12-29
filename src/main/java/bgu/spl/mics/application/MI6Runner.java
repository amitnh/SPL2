package bgu.spl.mics.application;

import bgu.spl.mics.application.subscribers.*;
import bgu.spl.mics.application.publishers.*;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.*;
import bgu.spl.mics.application.*;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.Event;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import sun.awt.image.ImageWatched;

/** This is the Main class of the application. You should parse the input file, 
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */

public class MI6Runner {
    public static void main(String[] args) throws IOException {

        // TODO Implement this
        MessageBroker MB = MessageBrokerImpl.getInstance();
        int numofMoneypennys=0;
        int numofMs=0;
        long time=0;
        LinkedList<Intelligence> Intelligences = new LinkedList<>();

        Gson gson = new Gson();
        try{
            JsonObject inputjson = gson.fromJson(new FileReader(args[0]), JsonObject.class);


            /////get inventory
            JsonArray inputinventory = inputjson.get("inventory").getAsJsonArray();
            String[] inputgadgets = new String[inputinventory.size()];
            for(int i=0;i<inputinventory.size();i++)
            {
                inputgadgets[i] = inputinventory.get(i).getAsString();
            }
            Inventory inventory = Inventory.getInstance();
            inventory.load(inputgadgets);



            /////get squad

            JsonArray inputsquad = inputjson.get("squad").getAsJsonArray();
            Squad squad = Squad.getInstance();
            Agent[] inputagents = new Agent[inputsquad.size()];

            for(int i=0;i<inputsquad.size();i++) {
                Agent oo = new Agent();
                oo.setName(inputsquad.get(i).getAsJsonObject().get("name").getAsString());
                oo.setSerialNumber(inputsquad.get(i).getAsJsonObject().get("serialNumber").getAsString());
                inputagents[i] =oo;

            }
            squad.load(inputagents);


            ////get services
            JsonObject inputservices = inputjson.get("services").getAsJsonObject();

            //Create M's
            numofMs = inputservices.getAsJsonObject().get("M").getAsInt();

            //Create Moneypennys
            numofMoneypennys = inputservices.getAsJsonObject().get("Moneypenny").getAsInt();

            //get time
            time = inputservices.getAsJsonObject().get("time").getAsLong();

            //Create intelligence
            JsonArray inputintelligence = inputservices.get("intelligence").getAsJsonArray();
            for( int i = 0 ; i < inputintelligence.size() ; i++ ){

                //Create Missions:
                JsonArray inputmissions = inputintelligence.get(i).getAsJsonObject().get("missions").getAsJsonArray();
                LinkedList<MissionInfo> missions = new LinkedList<>();
                for(int j = 0 ; j < inputmissions.size(); j++){
                    MissionInfo mission = new MissionInfo();
                    mission.setDuration(inputmissions.get(j).getAsJsonObject().get("duration").getAsInt());
                    mission.setTimeExpired(inputmissions.get(j).getAsJsonObject().get("timeExpired").getAsInt());
                    mission.setTimeIssued(inputmissions.get(j).getAsJsonObject().get("timeIssued").getAsInt());
                    mission.setMissionName(inputmissions.get(j).getAsJsonObject().get("name").getAsString());
                    mission.setGadget(inputmissions.get(j).getAsJsonObject().get("gadget").getAsString());
                    List<String> serialAgentsNumbers = new LinkedList<>();
                    JsonArray agentsneeded = inputmissions.get(j).getAsJsonObject().get("serialAgentsNumbers").getAsJsonArray();
                    for(int k = 0 ; k < agentsneeded.size(); k++) {
                        serialAgentsNumbers.add(agentsneeded.get(k).getAsString());
                    }
                    mission.setSerialAgentsNumbers(serialAgentsNumbers);

                    missions.add(mission);
                }


                Intelligences.add(new Intelligence(missions));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }


        List<Subscriber> subscriberList= new LinkedList<>();
        for(int i=0; i<numofMs; i++)
        {
            new Thread(new M()).start();
        }
        for(int i=0; i<numofMoneypennys; i++)
        {
            new Thread(new Moneypenny()).start();

        }
        for(Intelligence x : Intelligences)
            new Thread(x).start();
        new Thread(new Q()).start();

        //Make Time Serviece
        Thread timeSer = new Thread(new TimeService(time));
        timeSer.start();

        try{timeSer.join();} catch (Exception exp){} // Main waits for TimeService to Die slowly


        Diary.getInstance().printToFile("diaryOutputFile.json");
        Inventory.getInstance().printToFile("inventoryOutputFile.json");

        System.out.println(Thread.activeCount() +" Threads active");
    }

}
