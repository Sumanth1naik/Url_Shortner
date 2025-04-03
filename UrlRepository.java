import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
