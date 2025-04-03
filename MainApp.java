public class MainApp {
    public static void main(String[] args) {
        UrlRepository repo = new UrlRepository();
        repo.fetchAllUrls();  // Fetch & print all URLs

        // Close DB connection before exiting
        Runtime.getRuntime().addShutdownHook(new Thread(DatabaseConnection::closeConnection));
    }
}