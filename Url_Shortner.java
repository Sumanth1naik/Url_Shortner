import java.util.Scanner;

class url_Shortner {

    String longURL = "";

    public String Url_Shrinker(String url) {
        if(url == null || url.isEmpty()) {
            System.out.println("Please enter a valid URL");
            return null;
        }
        //Use this url to add timestamp in case of the url is already in the database
        //or else it will be same url
        String dupicateUrl = url;
        // Create a new instance of the UrlRepository class to interact with the database
        UrlRepository repo = null;
        boolean urlExists = false;
        //check if the url is valid or not
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            System.out.println("Please enter a valid URL starting with http:// or https://");
            return null;
        }
        //check if the url is already in the database
        repo = new UrlRepository();
        urlExists= repo.checkUrl(url);
        if (urlExists) {
        //if the url is already in the database, then get the short URL from the database
        return repo.getShortUrl(url);
        }
        //if the url is not in the database, then insert it into the database
        String prefix = "http://localhost:8080/short.ly/";
        String encoded_String = Base62Encoder.stringEncoder(dupicateUrl);
        String shortUrl = prefix + encoded_String;

        //insert the short URL and long URL into the database
        repo.insertUrl(shortUrl, url);
        repo.fetchAllUrls();
       
        // Return the short URL
        return shortUrl;

    }

    public String Url_Expander(String shortUrl) {
        // Create a new instance of the UrlRepository class to interact with the database
        UrlRepository repo = new UrlRepository();
        //check if the url is valid or not
        if (!shortUrl.startsWith("http://localhost:8080/short.ly/")) {
            System.out.println("Please enter a valid short URL starting with http://localhost:8080/short.ly/");
            return null;
        }
        //get the long URL from the short URL
        String longUrl = repo.getLongUrl(shortUrl);
        return longUrl;
    }
}