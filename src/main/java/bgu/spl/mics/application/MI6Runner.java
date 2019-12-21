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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/** This is the Main class of the application. You should parse the input file, 
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */

public class MI6Runner {
    public static void main(String[] args) {
        // TODO Implement this
        MessageBroker MB = MessageBrokerImpl.getInstance();
        Q q = new Q();

        q.run();

        Gson gson = new Gson();
        try{
            JsonObject inputjson = gson.fromJson(new FileReader(args[0]), JsonObject.class);

            /*
            /////get inventory
            JsonArray inputinventory = inputjson.get("inventory").getAsJsonArray();
            String[] inputgadgets = new String[inputinventory.size()];
            for(int j=0;j<inputinventory.size();j++)
            {
                inputgadgets[j] = inputinventory.get(j).getAsJsonObject().get("name").getAsString();
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

             */
            ////get services
            JsonObject inputservices = inputjson.get("services").getAsJsonObject();

            //Create M's
            int numofMs = inputservices.getAsJsonObject().get("M").getAsInt();

            //Create Moneypennys
            int numofMoneypennys = inputservices.getAsJsonObject().get("Moneypenny").getAsInt();


            //Create intelligence
            JsonArray inputintelligence = inputservices.get("intelligence").getAsJsonArray();
            List<Intelligence> inteligenses = new LinkedList<>();
            for( int i = 0 ; i < inputintelligence.size() ; i++ ){
                //Intelligence intelligence = new Intelligence();
                //Create Missions:
                JsonArray inputmissions = inputintelligence.get(i).getAsJsonObject().get("missions").getAsJsonArray();

                //Create Missions
                for(int j = 0 ; j < inputmissions.size(); j++){
                    /*MissionInfo mission = new MissionInfo();
                    mission.setDuration(inputmissions.get(j).getAsJsonObject().get("duration").getAsInt());
                    mission.setTimeExpired(inputmissions.get(j).getAsJsonObject().get("timeExpired").getAsInt());
                    mission.setTimeIssued(inputmissions.get(j).getAsJsonObject().get("timeIssued").getAsInt());
                    mission.setMissionName(inputmissions.get(j).getAsJsonObject().get("name").getAsString());
                    mission.setGadget(inputmissions.get(j).getAsJsonObject().get("gadget").getAsString());


                     */
                    List<String> serialAgentsNumbers = new LinkedList<>();
                    JsonArray agentsneeded = inputmissions.get(j).getAsJsonObject().get("serialAgentsNumbers").getAsJsonArray();
                    for(int k = 0 ; k < agentsneeded.size(); k++) {
                        serialAgentsNumbers.add(agentsneeded.get(k).getAsString());
                    }
                   // mission.setSerialAgentsNumbers(serialAgentsNumbers);
                //***************add mission to intelligence
                }

                //inteligenses.add(intelligence);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }
}
