import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class DictionaryUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dictionary Manager");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Components
        JLabel inputLabel = new JLabel("Enter Word:");
        JTextField inputField = new JTextField(20);
        JLabel translationLabel = new JLabel("Translation:");
        JTextField translationField = new JTextField(20);
        JCheckBox isEnglishToAmharic = new JCheckBox("English to Amharic");
        JButton translateButton = new JButton("Translate");
        JButton displayButton = new JButton("Display All Words");

        JLabel suggestionsLabel = new JLabel("Suggestions:");
        DefaultListModel<String> suggestionsModel = new DefaultListModel<>();
        JList<String> suggestionsList = new JList<>(suggestionsModel);
        suggestionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suggestionsList.setVisibleRowCount(5);
        JScrollPane suggestionsScrollPane = new JScrollPane(suggestionsList);

        // Styling
        inputField.setMaximumSize(new Dimension(300, 30));
        translationField.setMaximumSize(new Dimension(300, 30));

        // Translate Button ActionListener
        translateButton.addActionListener(e -> {
            String inputWord = inputField.getText();
            boolean isEnglish = isEnglishToAmharic.isSelected();
            String translation = DictionaryManager.getTranslation(inputWord, isEnglish);
            translationField.setText(translation);
        });

        // Display Button ActionListener
        displayButton.addActionListener(e -> {
            String wordsList = DictionaryManager.displayAllWords();
            JOptionPane.showMessageDialog(frame, new JTextArea(wordsList), "Dictionary", JOptionPane.INFORMATION_MESSAGE);
        });

        // Add DocumentListener to inputField for suggestions
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updateSuggestions() {
                String prefix = inputField.getText();
                suggestionsModel.clear(); // Clear previous suggestions
                if (!prefix.isEmpty()) {
                    boolean isEnglish = isEnglishToAmharic.isSelected();
                    String[] suggestions = DictionaryManager.getWordsStartingWith(prefix, isEnglish).split("\n");
                    for (String suggestion : suggestions) {
                        if (!suggestion.isEmpty()) {
                            suggestionsModel.addElement(suggestion);
                        }
                    }
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();
            }
        });

        // Add MouseListener to suggestionsList for click functionality
        suggestionsList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Double-click event
                    String selectedWord = suggestionsList.getSelectedValue();
                    if (selectedWord != null) {
                        inputField.setText(selectedWord);
                    }
                }
            }
        });

        // Adding Components to Panel
        panel.add(inputLabel);
        panel.add(inputField);
        panel.add(translationLabel);
        panel.add(translationField);
        panel.add(isEnglishToAmharic);
        panel.add(translateButton);
        panel.add(displayButton);
        panel.add(suggestionsLabel);
        panel.add(suggestionsScrollPane);

        // Frame Settings
        frame.add(panel);
        frame.setSize(500, 500); // Adjust window size
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
