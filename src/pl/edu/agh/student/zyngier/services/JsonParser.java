package pl.edu.agh.student.zyngier.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonParser {
    private final static String fileName = "airports.json";
    private JSONParser jsonParser;
    private FileReader fileReader;
    private Object airportObject;
    private JSONArray airportList;

    public JsonParser(){
        jsonParser = new JSONParser();

        try {
            fileReader = new FileReader(fileName);
            airportObject = jsonParser.parse(fileReader);
            airportList = (JSONArray) airportObject;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public String findValueOfKey(String knownKey, String knownValueOfKey, String keyOfFindingValue){
        for (Object it : airportList) {
            JSONObject jsonObject = (JSONObject) it;
            String value = (String) jsonObject.get(knownKey);
            String findingValue = (String) jsonObject.get(keyOfFindingValue);
            String findingContry = (String) jsonObject.get("iso_country");
            if(value != null) {
                if (value.equals(knownValueOfKey)) {
                    return findingValue;
                }
            }
        }

        return null;
    }
}
