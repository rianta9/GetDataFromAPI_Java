/**
 * 
 */
package application;
import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 * @author rianta9
 * Datecreate: 4 thg 3, 2020 21:20:28
 */

public class Translate{
    public static String translate(String sl, String tl, String text) throws IOException{
        // fetch
        URL url = new URL("https://translate.google.com/#view=home&op=translate&sl=" +
                sl + "&tl=" + tl + "&ie=UTF-8&oe=UTF-8&multires=1&oc=1&otf=2&ssel=0&tsel=0&sc=1&q=" + 
                URLEncoder.encode(text, "UTF-8"));
        System.out.println(url.getContent());
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Something Else");
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String result = br.readLine();
        br.close();
        // parse
        // System.out.println(result);
        result = result.substring(2, result.indexOf("]]") + 1);
        StringBuilder sb = new StringBuilder();
        String[] splits = result.split("(?<!\\\\)\"");
        for(int i = 1; i < splits.length; i += 8)
            sb.append(splits[i]);
        return sb.toString().replace("\\n", "\n").replaceAll("\\\\(.)", "$1");
    }
    public static void main(String[] args) throws IOException{
        Scanner br = new Scanner(System.in);
    	System.out.println("Source language:");
        String sl = br.nextLine();
        System.out.println("Translation language:");
        String tl = br.nextLine();
        System.out.println("Source:");
        String text = br.nextLine(); // read two lines
        System.out.println("Translation:");
        System.out.println(translate(sl, tl, text));
    }
}
