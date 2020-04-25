package application;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Scanner;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WikihowAPI {
	private static final Scanner scanner = new Scanner(System.in);
	private static final String encoding = "UTF-8";
	
	public static String ChuanHoa2(String text) {
		text = text.replace("..", ".");
		text = text.replace("!!", "!");
		text = text.replace("??", "?");
		text = text.replace("!", "!\n");
		text = text.replace("?", "?\n");
		text = text.replace(". ", ".\n");
		text = text.replace(": ", ":\n");
		return text.trim();
	}
	
	public static String ChuanHoa3(String text) {
		text = text.replace("người bạn", "[friend]");
		text = text.replace("đứa bạn", "[friend]");
		text = text.replace("hai bạn", "[twofriends]");
		text = text.replace("2 bạn", "[twofriends]");
		text = text.replace("tình bạn", "[friendship]");
		text = text.replace("con bạn", "[friend]");
		text = text.replace("thằng bạn", "[friend]");
		text = text.replace("ông bạn", "[friend]");
		text = text.replace("cậu bạn", "[friend]");
		text = text.replace("bạn bè", "[friends]");
		text = text.replace("anh bạn", "[guy]");
		text = text.replace("nhóm bạn", "[friends]");
		text = text.replace("bạn thân", "[best friend]");
		text = text.replace("kết bạn", "[add friend]");
		
		text = text.replace("Người bạn", "[Friend]");
		text = text.replace("Đứa bạn", "[Friend]");
		text = text.replace("Hai bạn", "[Twofriends]");
		text = text.replace("Tình bạn", "[Friendship]");
		text = text.replace("Con bạn", "[Friend]");
		text = text.replace("Thằng bạn", "[Friend]");
		text = text.replace("Ông bạn", "[Friend]");
		text = text.replace("Cậu bạn", "[Friend]");
		text = text.replace("Bạn bè", "[Friends]");
		text = text.replace("Bạn bè", "[Friends]");
		text = text.replace("Anh bạn", "[Guy]");
		text = text.replace("Bạn thân", "[Best friend]");
		text = text.replace("Kết bạn", "[Add friend]");
		
		text = text.replace("bạn ", "master ");
		text = text.replace("bạn.", "master.");
		text = text.replace("bạn,", "master.");
		text = text.replace("Bạn ", "Master ");
		
		text = text.replace("[friend]", "người bạn");
		text = text.replace("[twofriends]", "cả hai");
		text = text.replace("[friendship]", "tình bạn");
		text = text.replace("[Twofriends]", "Hai bạn");
		text = text.replace("[Friendship]", "Tình bạn");
		text = text.replace("[friends]", "bạn bè");
		text = text.replace("[best friend]", "bạn thân");
		text = text.replace("[guy]", "anh bạn");
		text = text.replace("[Friend]", "Người bạn");
		text = text.replace("[Friends]", "Bạn bè");
		text = text.replace("[Best friend]", "Bạn thân");
		text = text.replace("[Guy]", "Anh bạn");
		text = text.replace("[add friend]", "kết bạn");
		text = text.replace("[Add friend]", "Kết bạn");
		
		text = text.replace("cái tôi", "[ego]");
		text = text.replace("Cái tôi", "[Ego]");
		text = text.replace("vôi tôi", "[lime]");
		text = text.replace("tôi luyện", "[practice]");
		text = text.replace("Tôi luyện", "[Practice]");
		
		text = text.replace("tôi", "ikaros");
		text = text.replace("Tôi", "Ikaros");
		
		text = text.replace("[ego]", "cái tôi");
		text = text.replace("[Ego]", "Cái tôi");
		text = text.replace("[lime]", "vôi tôi");
		text = text.replace("[practice]", "tôi luyện");
		text = text.replace("[Practice]", "Tôi luyện");
		
		
		return text.trim();
	}
	
	public static void main(String[] args) throws IOException {
//		Document d=Jsoup.connect("http://www.wikihow.com/wikiHowTo?search=Signal+Wifi").timeout(6000).get();
//		Elements ele=d.select("div#searchresults_list");
//		for (Element element : ele.select("div.result")) {
//			String img_url=element.select("div.result_thumb img").attr("src");
//			System.out.println(img_url);
//			
//			String title=element.select("div.result_data a").text();
//			System.out.println(title);
//		}
		
		System.out.println("\n\nBạn muốn tìm gì?");
		String nextLine = scanner.nextLine();
		if (nextLine == null)
			return;
		String searchText = nextLine + " wikihow";
		System.out.println("Searching on the web....");
		System.out.println(searchText);
		//Search the google for Wikipedia Links
		String url = "https://www.google.com/search?q=" + searchText;
		Document google = Jsoup.connect(url).get();
		try {
			
			Elements links = google.select("div.rc > div.r > a");
			for (Element element : links) {
				System.out.println(URLDecoder.decode(element.attr("href"), encoding));
			}
			String firstLink = google.select("div.rc > div.r > a").first().attr("abs:href");
			System.out.println(URLDecoder.decode(firstLink, encoding));
			
			//Document doc = Jsoup.connect("https://www.wikihow.vn/Nhận-biết-một-Cô-gái-có-Cảm-tình-với-Bạn").timeout(6000).get();
			//Document doc = Jsoup.connect("https://www.wikihow.vn/Học-hiệu-quả-hơn").timeout(6000).get();
			
			Document doc = Jsoup.connect(URLDecoder.decode(firstLink, encoding)).userAgent("Mozilla/5.0").timeout(6000).get();
			
			Element title = doc.getElementsByClass("firstHeading").first();
			if(title != null) System.out.println(title.text());
			else System.out.println("null");
				
			Elements sections = doc.select("div.section.steps.sticky");
			int n  = sections.size();
			for (int i=0; i<n; i++) {
				System.out.println();
				Elements method = sections.get(i).select("h3 > div.altblock");
				Elements methodTitle = sections.get(i).select("h3 > span.mw-headline");
				System.out.println(method.text());
				System.out.println(methodTitle.text());
				System.out.println("Các bước:");
				Elements stepsElements = sections.get(i).select("li.hasimage");
				for (Element element : stepsElements) {
					String stepNum = element.select("div.step_num").first().text();
					String stepData = element.select("div.step").text();
					System.out.println("  * Bước " + stepNum + ":");
					System.out.println(ChuanHoa3(ChuanHoa2(stepData)));
				}
			}
		} catch (Exception e) {
			System.out.println("Không tìm thấy kết quả");
		}
		
	}
}

