import java.util.Scanner;

class url_Shortner{
    
    String longURL = "";

    public void Url_Shrinker(String shortURL){
        String prefix = "https://short.ly/";
        
    }
    public static void main(String[] args) {
        url_Shortner classObj = new url_Shortner();
        System.out.println("Enter your url");
        Scanner scObj  = new Scanner(System.in);
        String shortUrl = scObj.next();
        classObj.Url_Shrinker(shortUrl);
    }
}