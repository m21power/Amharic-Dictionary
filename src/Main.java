public class Main {
    public static void main(String[] args) {
        // Test all methods
        System.out.println("Testing Dictionary App...\n");

        // Add new words
        DictionaryManager.addWord("Hello", "formal way of saying hi", "ሰላም");

        // delete a word
        DictionaryManager.deleteWord("hello", true);

        // // Retrieve translations
        String amh = DictionaryManager.getTranslation("teacher", true);
        System.out.println("Translation for 'teacher': " + amh);

        String en = DictionaryManager.getTranslation("መምህር", false);
        System.out.println("Translation for 'መምህር': " + en);

        // Display all words
        DictionaryManager.displayAllWords();

    }
}
