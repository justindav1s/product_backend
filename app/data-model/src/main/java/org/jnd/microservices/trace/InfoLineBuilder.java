package org.jnd.microservices.trace;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * Created by justin on 14/11/2015.
 */
public class InfoLineBuilder {

    private static String hostname = null;
    static {
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static String getLine(String[] args, Map<String,List<String>> map, String[] keys)   {
       StringBuilder builder = new StringBuilder();

        builder.append("|" + hostname);

        for (String arg : args) {
            builder.append("|" + arg);
        }
        for (String key : keys) {
            if (map.get(key) != null) {
                builder.append("|" + map.get(key).get(0));
            }
        }

       return builder.toString();
    }
}
