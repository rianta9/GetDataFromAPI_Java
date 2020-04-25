package application;

import java.io.IOException;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WeatherAPI {
	private static final Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) throws IOException {
		System.out.println("\n\nBạn muốn tìm gì?");
		String nextLine = scanner.nextLine();
		if (nextLine == null)
			return;
		String quere = nextLine;
		String url = "https://www.google.com/search?q=" + quere;
		try {
			
			Document google = Jsoup.connect(url).get();
			// calculation result
			String location = "Vị trí: " + google.select("#wob_loc").text();
			String dateOfWeek = "Thời điểm: " + google.select("#wob_dts").text();
			String temperature = "Nhiệt độ: " + google.select("#wob_tm").text();
			String status = "Dự báo: " + google.select("#wob_dc").text();
			String precipitation = "Khả năng có mưa: " + google.select("#wob_pp").text();
			String humidity = "Độ ẩm: " + google.select("#wob_hm").text();
			
			String result = location+"\n"+dateOfWeek+"\n"+temperature+"\n"+status+"\n"+precipitation+"\n"+humidity;
			System.out.println(result);
		} catch (IOException e) {
			System.out.println("Ikaros không truy xuất được kết quả vào hiện tại!");
		} catch (NullPointerException e) {
			System.out.println("Ikaros không truy xuất được kết quả vào hiện tại!");
		}
		
	}
}
