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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Paweł
 */
public class NavigateMe {

    /**
     * @param args the command line arguments
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) throws MalformedURLException, IOException {
        // TODO code application logic here

        URL url = new URL("http://pi.zetis.pw/krynskip/web-pathfinder/routes?from=WAW&to=KRK");

        URLConnection connection = url.openConnection();

        // get all headers
        Map<String, List<String>> map = connection.getHeaderFields();
        for (Map.Entry<String, List<String>> item : map.entrySet()) {
            System.out.println("Key : " + item.getKey() + " ,Value : " + item.getValue());
        }

        // get Location header
        String location = connection.getHeaderField("Location");
        System.out.println(location);

        URL pathURL = new URL(location);

        BufferedReader in = new BufferedReader(new InputStreamReader(pathURL.openStream()));

        String inputLine;
        String pathResponse = "";
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
            pathResponse = inputLine;
        }
        in.close();
        
        Gson mygson = new Gson();
        
        ArrayList jsonArray = mygson.fromJson(pathResponse, ArrayList.class);
        System.out.println(jsonArray);
        
        for( Object code : jsonArray) {
            System.out.println(code);
        }


    }

}
