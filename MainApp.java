import java.util.Scanner;

public class MainApp {

    public static String urlShortner(String longUrl){
        url_Shortner classObj = new url_Shortner();
        String url = longUrl;
        System.out.print("Entered  URL for shorten: "+url);
        // Call the Url_Shrinker method and print the result
        String shortUrl = classObj.Url_Shrinker(url);
        System.out.println("Shortened URL: " + shortUrl);
        return shortUrl;
    }

    public static String urlExpander(String url){
        url_Shortner classObj = new url_Shortner();
        // Prompt the user for a short URL
        System.out.print("Entered short URL for expand: ");
        // Call the Url_Expander method and print the result
        String longUrl = classObj.Url_Expander(url);
        return longUrl;
    }
}