import javax.swing.*;
import java.awt.*;

public class BarField extends JPanel {
    // TODO: RECONFIGURE WITH GRIDBAGLAYOUT
    private final JLabel title;
    private final JTextField field;

    public BarField(String _title, boolean _password) {
        super(new GridBagLayout());
        title = new JLabel(_title);
        if (_password) {
            field = new JPasswordField();
        } else {
            field = new JTextField();
        }

        GridBagConstraints titleGBC = new GridBagConstraints();
        titleGBC.gridx = 0;
        titleGBC.weightx = .05;
        titleGBC.gridwidth = 1;
        titleGBC.anchor = GridBagConstraints.WEST;

        GridBagConstraints fieldGBC = new GridBagConstraints();
        fieldGBC.gridheight = 1;
        fieldGBC.weightx = 100;
        fieldGBC.gridwidth = GridBagConstraints.REMAINDER;
        fieldGBC.fill = GridBagConstraints.BOTH;

        this.add(title, titleGBC);
        this.add(field, fieldGBC);

    }

    public String getField() {
        return field.getText();
    }

    public enum Position {
        LABEL_FIRST,
        FIELD_FIRST
    }
}
