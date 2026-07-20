package app;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LanguageSelectionFrame().setVisible(true));
    }
}

class LanguageSelectionFrame extends JFrame {
    private final JComboBox<LanguageOption> languageBox;

    public LanguageSelectionFrame() {
        setTitle("Sprachauswahl / Language selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 170);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(3, 1, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel("Bitte Sprache wählen / Please choose a language:");
        languageBox = new JComboBox<>(new LanguageOption[]{
                new LanguageOption("Deutsch", Locale.GERMANY),
                new LanguageOption("English", Locale.UK)
        });
        JButton weiterButton = new JButton("OK");
        weiterButton.addActionListener(e -> openResultWindow());

        panel.add(label);
        panel.add(languageBox);
        panel.add(weiterButton);
        add(panel, BorderLayout.CENTER);
    }

    private void openResultWindow() {
        LanguageOption option = (LanguageOption) languageBox.getSelectedItem();
        if (option != null) {
            new ResultFrame(option.locale()).setVisible(true);
        }
    }
}

class ResultFrame extends JFrame {
    public ResultFrame(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", locale);

        setTitle(bundle.getString("window.output.title"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 280);
        setLocationRelativeTo(null);

        String numberText = NumberFormat.getNumberInstance(locale).format(1000);
        String dateText = LocalDate.now().format(DateTimeFormatter.ofPattern(bundle.getString("date.pattern"), locale));
        String nameText = bundle.getString("person.name");

        JLabel titleLabel = new JLabel(bundle.getString("headline"));
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel nameLabel = new JLabel(bundle.getString("label.name") + nameText);
        JLabel numberLabel = new JLabel(bundle.getString("label.number") + numberText);
        JLabel dateLabel = new JLabel(bundle.getString("label.date") + dateText);

        JPanel content = new JPanel(new GridLayout(4, 1, 10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        content.add(titleLabel);
        content.add(nameLabel);
        content.add(numberLabel);
        content.add(dateLabel);

        add(content);
    }
}

record LanguageOption(String label, Locale locale) {
    @Override
    public String toString() {
        return label;
    }
}
