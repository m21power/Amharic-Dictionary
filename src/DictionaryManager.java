import java.sql.*;

public class DictionaryManager {

    // Retrieve the translation of a word
    public static String getTranslation(String inputWord, boolean isEnglishToAmharic) {
        String query = null;
        if (isEnglishToAmharic) {
            query = "SELECT AMH FROM dictionary WHERE _id = ? COLLATE NOCASE";
        } else {
            query = "SELECT _id FROM dictionary WHERE AMH = ?";
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

    // Retrieve words starting with a given prefix
public static String getWordsStartingWith(String prefix, boolean isEnglish) {
    StringBuilder result = new StringBuilder();
    String query = null;

    if (isEnglish) {
        query = "SELECT _id FROM dictionary WHERE _id LIKE ? COLLATE NOCASE";
    } else {
        query = "SELECT AMH FROM dictionary WHERE AMH LIKE ?";
    }

    try (Connection conn = SQLiteConnector.connect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setString(1, prefix.trim() + "%"); // '%' matches any number of characters
        ResultSet rs = pstmt.executeQuery();

        boolean found = false;
        while (rs.next()) {
            found = true;
            result.append(rs.getString(1)).append("\n"); // Fetches the word
        }

        if (!found) {
            result.append("No words found with the prefix: ").append(prefix);
        }

    } catch (SQLException e) {
        result.append("Error retrieving words: ").append(e.getMessage());
    }

    return result.toString();
}

    // Add a new word pair to the dictionary
    public static void addWord(String english, String description, String amharic) {
        String query = "INSERT INTO dictionary (_id, EN, AMH) VALUES(?, ?, ?)";

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

    // Return all words in the dictionary
    public static String displayAllWords() {
        StringBuilder content = new StringBuilder("English - Amharic Words:\n");

        String query = "SELECT _id, AMH FROM dictionary";
        try (Connection conn = SQLiteConnector.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                content.append(rs.getString("_id")).append(" - ").append(rs.getString("AMH")).append("\n");
            }
        } catch (SQLException e) {
            content.append("Error displaying words: ").append(e.getMessage());
        }

        return content.toString();
    }
}
