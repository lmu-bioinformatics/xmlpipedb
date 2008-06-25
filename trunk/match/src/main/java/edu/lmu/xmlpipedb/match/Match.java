package edu.lmu.xmlpipedb.match;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Match is a simple program that takes a regex and counts the number of unique
 * matching strings from standard input.
 * 
 * TODO Very ugly CLI and design for now; but we want to get the functionality
 * down first before cleaning it up.
 * 
 * @author dondi
 */
public class Match {

    /**
     * Given a regex and standard input, counts the number of unique matching
     * strings and displays them.
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String regexStr = args[0];
        InputStream is = System.in;
        
        Map<String, Integer> result = matchUnique(regexStr, new InputStreamReader(is));
        for (String oneMatch: result.keySet()) {
            System.out.println(oneMatch + ": " + result.get(oneMatch));
        }
        System.out.println();
        System.out.println("Total unique matches: " + result.size());
    }

    /**
     * The actual function that does the work. Not much with options right now,
     * particularly whether or not the uniqueness comparison is case sensitive
     * (as a quick fix, we currently are case INsensitive by virtue of
     * converting strings to lowercase first).
     * 
     * @param regexStr
     * @param is
     * @return
     * @throws IOException
     */
    public static Map<String, Integer> matchUnique(String regexStr, Reader reader) throws IOException {
        Pattern regex = Pattern.compile(regexStr);
        Map<String, Integer> result = new HashMap<String, Integer>();

        BufferedReader br = new BufferedReader(reader);
        String line = br.readLine();
        while (line != null) {
            Matcher matcher = regex.matcher(line);
            line = br.readLine();
            while (matcher.find()) {
                String group = matcher.group().toLowerCase();
                if (result.containsKey(group)) {
                    int count = result.get(group);
                    result.put(group, count + 1);
                } else {
                    result.put(group, 1);
                }
            }
        }

        return result;
    }
}
