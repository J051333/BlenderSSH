import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FilePicker extends JPanel {
    private JLabel pickerLabel;
    private JTextField pickerField;
    private JButton browseButton;
    private JFileChooser fileChooser;

    public FilePicker() {

        // Prelim Setup

        this.setLayout(new GridBagLayout());

        // GridBagConstraints

        GridBagConstraints pathLGBC = new GridBagConstraints();
        pathLGBC.gridx = 0;
        pathLGBC.weightx = .05;
        pathLGBC.gridwidth = 1;
        pathLGBC.anchor = GridBagConstraints.WEST;

        GridBagConstraints pathFGBC = new GridBagConstraints();
        pathFGBC.gridheight = 1;
        pathFGBC.weightx = 100;
        pathFGBC.fill = GridBagConstraints.BOTH;
        pathFGBC.insets = new Insets(0, 0, 0, 5);

        GridBagConstraints pathBGBC = new GridBagConstraints();
        pathBGBC.gridheight = 1;
        pathBGBC.gridwidth = GridBagConstraints.REMAINDER;
        pathBGBC.weightx = .5;
        pathBGBC.fill = GridBagConstraints.BOTH;

        // Init

        pickerLabel = new JLabel("File to Render: ");
        pickerField = new JTextField();
        browseButton = new JButton("Browse");
        fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Blender Files", "blend"));

        browseButton.addActionListener(e -> {
            fileChooser.showOpenDialog(null);
            if (fileChooser.getSelectedFile() != null) {
                pickerField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        // Add

        this.add(pickerLabel, pathLGBC);
        this.add(pickerField, pathFGBC);
        this.add(browseButton, pathBGBC);
    }

    public String getPickedFilePath() {
        return pickerField.getText();
    }
}
