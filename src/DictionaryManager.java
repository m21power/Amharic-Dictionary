import java.sql.*;

public class DictionaryManager {

    // Retrieve the translation of a word
    public static String getTranslation(String inputWord, boolean isEnglishToAmharic) {
        String query = null;
        if (isEnglishToAmharic) {
            query = "SELECT AMH FROM dictionary WHERE _id = ? COLLATE NOCASE";
        } else {
            query = "SELECT EN FROM dictionary WHERE AMH = ?";
        }
        String result = "Translation not found";

        try (Connection conn = SQLiteConnector.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, inputWord.trim()); // trim to remove trailing and leading space of input word
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getString(1); // the first column
            } else {
                System.out.println("No translation found for: " + inputWord);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return result;
    }

    // Add a new word pair to the dictionary
    public static void addWord(String english, String description, String amharic) {
        String query = "INSERT INTO dictionary (_id, EN,AMH) VALUES(?, ?, ?)";

        try (Connection conn = SQLiteConnector.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, english);
            pstmt.setString(2, description);
            pstmt.setString(3, amharic);
            pstmt.executeUpdate();
            System.out.println("Word added successfully: " + english + " - " + amharic);
        } catch (SQLException e) {
            System.err.println("Error adding word: " + e.getMessage());
        }
    }

    // Delete a word by English or Amharic value
    public static void deleteWord(String word, boolean isEnglish) {
        String query = null;
        if (isEnglish) {
            query = "DELETE FROM dictionary WHERE _id = ? COLLATE NOCASE";
        } else {
            query = "DELETE FROM dictionary WHERE AMH = ?";
        }

        try (Connection conn = SQLiteConnector.connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, word);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Word deleted successfully: " + word);
            } else {
                System.out.println("Word not found: " + word);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting word: " + e.getMessage());
        }
    }

    // Display all words in the dictionary
    public static void displayAllWords() {
        String query = "SELECT _id, AMH FROM dictionary";

        try (Connection conn = SQLiteConnector.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("English - Amharic Words:");
            while (rs.next()) {
                System.out.println(rs.getString("_id") + " - " + rs.getString("AMH"));
            }
        } catch (SQLException e) {
            System.err.println("Error displaying words: " + e.getMessage());
        }
    }
}
