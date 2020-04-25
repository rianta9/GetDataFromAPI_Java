package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.IOException;
import javazoom.jl.decoder.*;
import javazoom.jl.player.Player;
public class Wikipedia {
	
	private static final Scanner scanner = new Scanner(System.in);
	private static final String encoding = "UTF-8";
	
	//Create TextToSpeech
	private static final TextToSpeech tts = new TextToSpeech();
	
	
	public static void speak(String text, float speed) {
		String texttospeechAPI = "https://texttospeech.responsivevoice.org/v1/text:synthesize?text="+text+"&lang=vi&engine=g1&name=&pitch=0.5&rate="+String.valueOf(speed)+"&volume=1&key=PL3QYYuV&gender=female.mp3";
		//String song = "http://www.ntonyx.com/mp3files/Morning_Flower.mp3";
		Player mp3player = null;
        BufferedInputStream in = null;
        try {
          in = new java.io.BufferedInputStream(new URL(texttospeechAPI).openStream());
          mp3player = new Player(in);
          mp3player.play();
        } catch (MalformedURLException ex) {
        } catch (IOException e) {
        } catch (JavaLayerException e) {
        } catch (NullPointerException ex) {
        }
	}
	
	
	public static String ChuanHoa2(String text) {
		text = text.replace("..", ".");
		text = text.replace("!!", "!");
		text = text.replace("??", "?");
		text = text.replace("!", "!\n");
		text = text.replace("?", "?\n");
		text = text.replace(". ", ".\n");
		text = text.replace(": ", ":\n");
		text = text.replace("; ", ";\n");
		text = text.trim();
		return text.trim();
	}
	
	public static void main(String[] args) {
		
		//Print all the available voices
		tts.getAvailableVoices().stream().forEach(voice -> System.out.println("Voice: " + voice));
		
		// Setting the Current Voice
		tts.setVoice("cmu-rms-hsmm");
		
		boolean exit = false;
		
		//Run until exit =true
		while (!exit) {
			
			try {
				//String searchText = "what is a fish ";
				
				//Wait for user response
				System.out.println("\n\nType something that you want me to search on the internet...");
				String nextLine = scanner.nextLine();
				if (nextLine == null)
					continue;
				tts.stopSpeaking();
				String searchText = nextLine + " wikipedia";
				System.out.println("Searching on the web...." + searchText);
			    
			    
				//Search the google for Wikipedia Links
				Document google = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(searchText, encoding)).userAgent("Mozilla/5.0").get();
				//System.out.println(google);
				//Get the first link about Wikipedia
//				String wikipediaURL = google.getElementsByTag("cite").get(0).text();
				//Use Wikipedia API to get JSON File
//				String wikipediaApiJSON = "https://www.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="
//						+ URLEncoder.encode(wikipediaURL.substring(wikipediaURL.lastIndexOf("/") + 1, wikipediaURL.length()), encoding);
				
				String url = "https://www.google.com/search?q="+nextLine+ " wikipedia";
				Document doc = Jsoup.connect(url).get();
		        Elements links = doc.select("cite");
		        System.out.println(url);

		        String getlink = links.get(0).text();
		        String[] link = getlink.split("[ > ]");
		        String resultID = link[link.length-1];
		        System.out.println(link[link.length-1]);
				
		        String wikipediaApiJSON = "https://vi.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="
						+ resultID;
				
				//Let's see what it found
//				System.out.println(wikipediaURL);
				System.out.println(wikipediaApiJSON);
				
				//"extract":" the summary of the article
				HttpURLConnection httpcon = (HttpURLConnection) new URL(wikipediaApiJSON).openConnection();
				httpcon.addRequestProperty("User-Agent", "Mozilla/5.0");
				BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
				
				//Read line by line
				String responseSB = in.lines().collect(Collectors.joining());
				in.close();
				
				//Print the result for us to see
				//System.out.println(responseSB);
				String result = responseSB.split("extract\":\"")[1];
				
				
				//System.out.println(result);
				
				//Tell only the 150 first characters of the result
//				String textToTell = result.length() > 250 ? result.substring(0, 250) : result;
//				System.out.println(textToTell + "...");
				
				String kq = ChuanHoa2(StringEscapeUtils.unescapeJava(result));
				speak(kq, 0.7f);
				System.out.println(kq);
//				//Speak 
				//tts.speak(kq.substring(0, 200), 1.5f, false, false);
				
			} catch (Exception ex) {
				ex.printStackTrace();
				//Speak 
				speak("Please try searching something other", 0.5f);
			}
			
		}
		
	}

	

}
