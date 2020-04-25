package application;


import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Example program to list links from a URL.
 */
public class test {
	private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
    	System.out.println("\n\nType something that you want me to search on the internet...");
		String nextLine = scanner.nextLine();

        String url = "https://www.google.com/search?q="+nextLine+ " wikipedia";
        print("Fetching %s...", url);

        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("cite");
        

        String getlink = links.get(0).text();
        String[] link = getlink.split("[ > ]");
        System.out.println(link[link.length-1]);
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}