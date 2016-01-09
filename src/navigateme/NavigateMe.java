/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package navigateme;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() + " ,Value : " + entry.getValue());
        }

        // get Location header
        String location = connection.getHeaderField("Location");
        System.out.println(location);

    }

}
