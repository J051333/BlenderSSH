import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Window extends JFrame {
    public JPanel entire;
    public JPanel top;
    public JPanel midOne;
    public JPanel midTwo;
    public JPanel bottom;

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static final String[] engines = {"CYCLES", "BLENDER_EEVEE"};

    public JComboBox<String> renderEngine;

    public BarField username;
    public BarField ipAddr;
    public BarField password;
    public FilePicker pathPicker;
    public BarField outputName;
    public BarField frameNumber;
    public BarField sshPort;
    public BarField ftpPort;

    public JButton button;

    public Window(int w, int h) {
        // Preliminary Setup

        super("Blender SSH");

        // Component Setup

        entire = new JPanel(new GridLayout(4, 1, 10, 2));
        top = new JPanel(new GridLayout(1, 3, 10, 2));
        midOne = new JPanel(new GridLayout());
        midTwo = new JPanel(new GridLayout());
        bottom = new JPanel(new GridLayout(1, 3, 10, 2));

        username = new BarField("User Name: ", false);
        ipAddr = new BarField("SSH IP: ", false);
        password = new BarField("User Password: ", true);
        pathPicker = new FilePicker();
        outputName = new BarField("Image Name: ", false);
        frameNumber = new BarField("Frame Number: ", false);
        sshPort = new BarField("SSH Port (Default 22): ", false);
        ftpPort = new BarField("FTP Port (Default 20): ", false);

        renderEngine = new JComboBox<>(engines);

        button = new JButton("SSH and Render");

        button.addActionListener(e -> WiComm.commitBlend(username.getField(), ipAddr.getField(), password.getField(), sshPort.getField(), ftpPort.getField(), pathPicker.getPickedFilePath(), outputName.getField(), (String) renderEngine.getSelectedItem(), frameNumber.getField()));

        // Component Addition

        this.add(entire);
        entire.add(top);
        entire.add(midOne);
        entire.add(midTwo);
        entire.add(bottom);

        top.add(username);
        top.add(ipAddr);
        top.add(password);

        midOne.add(pathPicker);

        midTwo.add(outputName);
        midTwo.add(frameNumber);

        bottom.add(sshPort);
        bottom.add(ftpPort);
        bottom.add(renderEngine);
        bottom.add(button);

        entire.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Frame Setup

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(w, h);
        this.setResizable(false);
        this.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2);
        this.setVisible(true);
    }

    public Window() {
        this(1000, 215);
    }

}
