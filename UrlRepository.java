import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.SQLIntegrityConstraintViolationException;

public class UrlRepository {
    private final Connection conn;

    public UrlRepository() {
        this.conn = DatabaseConnection.getConnection();  // Use the single connection
    }

    public void fetchAllUrls() {
        String query = "SELECT * FROM urlmapping";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("ID | Short URL | Long URL | Created At");
            System.out.println("---------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String shortUrl = rs.getString("shortUrl");
                String longUrl = rs.getString("longUrl");
                String createdAt = rs.getString("createdAt");

                System.out.printf("%d | %s | %s | %s%n", id, shortUrl, longUrl, createdAt);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching data!");
            e.printStackTrace();
        }
    }

    // Method to insert a new URL mapping into the database
    public void insertUrl(String shortUrl, String longUrl) {
        String query = "INSERT INTO urlmapping (shortUrl, longUrl) VALUES ('" + shortUrl + "', '" + longUrl + "')";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
            System.out.println("URL inserted successfully!");
        }
        catch(SQLIntegrityConstraintViolationException e) {
            System.out.println("URL already exists!");

        }
         catch (SQLException e) {
            System.out.println("Error inserting URL!");
            e.printStackTrace();
        }

    }

    // Method to check if a URL exists in the database
    public boolean checkUrl(String longUrl) {
        String query = "SELECT * FROM urlmapping WHERE longUrl = '" + longUrl + "'";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
                
            if (rs.next()) {
                System.out.println("URL exists in the database!");
                return true;
            } else {
                System.out.println("URL does not exist in the database!");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error checking URL!");
            e.printStackTrace();
        }
        return false;
    }

    // Method to get the long URL from the short URL
    public String getLongUrl(String shortUrl) {
        String query = "SELECT longUrl FROM urlmapping WHERE shortUrl = '" + shortUrl + "'";
        String longUrl = null;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                longUrl = rs.getString("longUrl");
            } else {
                System.out.println("Short URL not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching long URL!");
            e.printStackTrace();
        }

        return longUrl;
    }

    // Method to get the short URL from the long URL
    public String getShortUrl(String longUrl) {
        String query = "SELECT shortUrl FROM urlmapping WHERE longUrl = '" + longUrl + "'";
        String shortUrl = null;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                shortUrl = rs.getString("shortUrl");
            } else {
                System.out.println("Long URL not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching short URL!");
            e.printStackTrace();
        }

        return shortUrl;
    }
}
