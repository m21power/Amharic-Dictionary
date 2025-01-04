import javax.swing.*;
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
        JTextArea textArea = new JTextArea(10, 40); // reduced height for the area
        JButton displayButton = new JButton("Display All Words");

        // Styling
        inputLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputField.setMaximumSize(new Dimension(300, 30)); // limit input field size
        translationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        translationField.setMaximumSize(new Dimension(300, 30)); // limit translation field size

        // Set margins and paddings for a compact layout
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        translationLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        isEnglishToAmharic.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

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
            textArea.setText(wordsList);
            JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Dictionary", JOptionPane.INFORMATION_MESSAGE);
        });

        // Adding Components to Panel
        panel.add(inputLabel);
        panel.add(inputField);
        panel.add(translationLabel);
        panel.add(translationField);
        panel.add(isEnglishToAmharic);
        panel.add(translateButton);
        panel.add(displayButton);

        // Adding a text area for displaying words in a scrollable pane
        panel.add(new JScrollPane(textArea));

        // Frame Settings
        frame.add(panel);
        frame.setSize(400, 400); // Set a smaller window size for compactness
        frame.setLocationRelativeTo(null); // Center the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
