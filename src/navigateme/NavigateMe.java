/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package navigateme;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Paweł
 */
public class NavigateMe {

    TreeMap<String, String> cities = new TreeMap<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        NavigateMe navigateMe = new NavigateMe();

        try {
            navigateMe.getCities();
        } catch (IOException ex) {
            System.err.println("Nie mogę pobrać listy miast.");
        }
        
        //navigateMe.ListCities();
        
        ArrayList<String> path = null;
        try {
            path = navigateMe.getPath("ELK", "SZZ");
        } catch (IOException ex) {
            System.err.println("Nie mogę pobrać trasy.");
        }
        
        System.out.println("Twoja trasa:");
        
        for(String item : path) {
            //System.out.println(item);
            
            String fullName = (String) navigateMe.getCityMap().get(item);
            
            System.out.println("- " + fullName);
        }

    }

    public void addToCityMap(String key, String value) {

        this.cities.put(key, value);
    }
    
    public TreeMap getCityMap() {
        return this.cities;
    }

    public void getCities() throws IOException {

        URL cityListURL = new URL("http://pi.zetis.pw/krynskip/web-pathfinder/cities");

        BufferedReader in = new BufferedReader(new InputStreamReader(cityListURL.openStream()));

        String inputLine;
        String input = "";
        while ((inputLine = in.readLine()) != null) {
            //System.out.println(inputLine);
            input = inputLine;
        }

        //System.out.println(input);
        Gson gson = new Gson();

        //Type type = new TypeToken<Map<String, String>>() {
        //}.getType();
        //Map<String, String> myMap = gson.fromJson(input, type);
        ArrayList jsonArray = gson.fromJson(input, ArrayList.class);

        for (Object item : jsonArray) {

            String city = item.toString();
            //System.out.println(city);

            String pair = city.replace("{", "").replace("}", "").replace(",", "").replace("id=", "").replace("name=", "");
            //System.out.println(pair);

            String parts[] = pair.split(" ");

            //System.out.println("Key: " + parts[0] + " Value: " + parts[1]);
            addToCityMap(parts[0], parts[1]);

        }

    }
    
    public void ListCities() {
        
        System.out.println("Dostępna miasta:");
        
        for( String key : this.cities.keySet() ) {
            System.out.println("- " + this.cities.get(key));
        }
        
    }

    public ArrayList<String> getPath(String from, String to) throws IOException {

        ArrayList path = new ArrayList();
        
        String preURL = "http://pi.zetis.pw/krynskip/web-pathfinder/routes?from=" + from + "&to=" + to;

        URL url = new URL(preURL);

        URLConnection connection = url.openConnection();

        // get all headers
        Map<String, List<String>> map = connection.getHeaderFields();
        /*
         for (Map.Entry<String, List<String>> item : map.entrySet()) {
         System.out.println("Key : " + item.getKey() + " ,Value : " + item.getValue());
         }*/

        // get Location header
        String location = connection.getHeaderField("Location");
        //System.out.println(location);

        URL pathURL = new URL(location);

        BufferedReader in = new BufferedReader(new InputStreamReader(pathURL.openStream()));

        String inputLine;
        String pathResponse = "";
        while ((inputLine = in.readLine()) != null) {
            //System.out.println(inputLine);
            pathResponse = inputLine;
        }
        in.close();

        Gson mygson = new Gson();

        ArrayList jsonArray = mygson.fromJson(pathResponse, ArrayList.class);
        //System.out.println(jsonArray);

        for (Object code : jsonArray) {
            //System.out.println(code.toString());
            path.add(code.toString());
        }
        
        return path;
    }

}
