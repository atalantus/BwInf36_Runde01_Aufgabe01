package GUI;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class ResultOutputDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel header;
    private JPanel body;
    private JLabel title;
    private JList<String> output;
    private JLabel info;
    private JPanel footer;
    private DefaultListModel<String> outputModel;

    ResultOutputDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        outputModel = new DefaultListModel<>();
        output.setModel(outputModel);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    void showResultOutputDialog(Exception e, List<String> outputTexts, String fileName) {
        setTitle("Room Assignment Result");
        if (e != null) {
            title.setText("Error!");
            outputModel.addElement("                                        File: " + fileName);
            outputModel.addElement("");
            outputModel.addElement("");
            outputModel.addElement("");
            outputModel.addElement("Message:");
            outputModel.addElement(" ");
            outputModel.addElement(e.getMessage());
            outputModel.addElement(" ");
            outputModel.addElement(" ");
            outputModel.addElement(" ");

            outputModel.addElement("Stopped Room Constellation:");
            outputModel.addElement(" ");
            if (outputTexts != null) {
                for (String s : outputTexts) {
                    outputModel.addElement(s);
                }
            }

            outputModel.addElement(" ");
            outputModel.addElement(" ");
            outputModel.addElement(" ");
            outputModel.addElement("Stack Trace:");
            outputModel.addElement(" ");
            for (StackTraceElement s : e.getStackTrace()) {
                outputModel.addElement(s.toString());
            }
        } else {
            title.setText("Room Constellation");
            outputModel.addElement("                                        File: " + fileName);
            outputModel.addElement("");
            outputModel.addElement("");
            outputModel.addElement("");
            if (outputTexts != null) {
                for (String s : outputTexts) {
                    outputModel.addElement(s);
                }
            }
        }
    }

    void clearResultOutputDialog() {
        title.setText("");
        outputModel.clear();
    }

    private void onOK() {
        new FileChooser(this);
    }

    private void onCancel() {
        dispose();
    }
}
