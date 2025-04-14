import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleHttpApi {
    public static void main(String[] args) throws IOException {
        // Create a server and start the service at the 8080 port
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Handle /ping
        server.createContext("/ping", exchange -> {
            String response = "Pong!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.createContext("/shortUrl", exchange -> {
            System.out.println("----------------------- Executing the url shortner method ----------------------------");

            // Handle GET request
            if ("GET".equals(exchange.getRequestMethod())) {
                String longUrl = null;
                // Extract the query parameter from the URL
                // e.g. http://localhost:8080/shortUrl?longUrl=http://example.com
                String url = exchange.getRequestURI().getQuery();
                System.out.println("URL: " + url);
                if (url != null && url.startsWith("longUrl=")) {
                    longUrl = url.substring(8);
                    //Validate the url after extracting the url
                    if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")){
                        System.out.println("Please enter a valid URL");
                        exchange.sendResponseHeaders(400, -1);
                        return; 
                    }
                }
                else{
                    System.out.println("Please enter a valid URL");
                    exchange.sendResponseHeaders(400, -1);
                    return;
                }
                // Handle URL shortening logic here
                String response = "Shortened URL: " + MainApp.urlShortner(longUrl);
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }

            else {
                exchange.sendResponseHeaders(405, -1);
            }
        });

        server.createContext("/expandUrl", exchange->{
            System.out.println("------------------ Inside the URL Expander ---------------");
            String shortUrl = null;
        if("GET".equals(exchange.getRequestMethod())){
            String url = exchange.getRequestURI().getQuery();
            System.out.println("URL ="+ url);

            if(null!=url && url.startsWith("shortUrl=")){
                shortUrl = url.substring(9);
                if (!shortUrl.startsWith("http://") && !shortUrl.startsWith("https://")){
                    System.out.println("Please enter a valid URL");
                    exchange.sendResponseHeaders(400, -1);
                    return; 
                }
            }
            else{
                System.out.println("Please enter a valid URL");
                exchange.sendResponseHeaders(400, -1);
                return;  
            }
             // Handle URL expanding logic here
             String response = "Shortened URL: " + MainApp.urlExpander(shortUrl);
             exchange.sendResponseHeaders(200, response.getBytes().length);
             OutputStream os = exchange.getResponseBody();
             os.write(response.getBytes());
             os.close();
        }
    });

        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();
            if ("/".equals(path) || "/index.html".equals(path)) {
                String html = new String(Files.readAllBytes(Paths.get("index.html")));
                exchange.getResponseHeaders().add("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, html.length());
                OutputStream os = exchange.getResponseBody();
                os.write(html.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
        });

        server.createContext("/short.ly" , exchange ->{
            String path = exchange.getRequestURI().getPath(); // e.g. /8gPxNKjwIDF
            System.out.println(path);
            if (path.startsWith("/short.ly")) {
                String prefix  = "http://localhost:8080";
                String shortUrl = prefix+path;
                System.out.println("Short URL: " + shortUrl);
                String longUrl = MainApp.urlExpander(shortUrl);
                System.out.println(longUrl);
                if (longUrl != null) {
                    // ✅ Redirect the browser to the long URL
                    exchange.getResponseHeaders().add("Location", longUrl);
                    exchange.sendResponseHeaders(302, -1); // 302 = Found (Redirect)
                }
                else{
                    String notFound = "Short URL not found!";
                    exchange.sendResponseHeaders(404, notFound.length());
                    exchange.getResponseBody().write(notFound.getBytes());
                }
            }
            else {
                String notFound = "Short URL not found!";
                exchange.sendResponseHeaders(404, notFound.length());
                exchange.getResponseBody().write(notFound.getBytes());
            } 
        });

        server.createContext("/expander", exchange -> {
            String path = exchange.getRequestURI().getPath();
            if ("/expander".equals(path) || "/shortUrlUi.html".equals(path)) {
                String html = new String(Files.readAllBytes(Paths.get("shortUrlUi.html")));
                exchange.getResponseHeaders().add("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, html.length());
                OutputStream os = exchange.getResponseBody();
                os.write(html.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
        });

        server.setExecutor(null); // default executor
        server.start();
        System.out.println("Server started on port 8080");
    }
}
