import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class SSHFeed extends JFrame {

    private final JLabel feed;

    public SSHFeed(String _feed) {
        feed = new JLabel("<html><body style='width: %1spx'>" + _feed + "</html>");

        JScrollPane scrollPane = new JScrollPane(feed);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> e.getAdjustable().setValue(e.getAdjustable().getMaximum()));

        this.getContentPane().add(scrollPane);

        this.setSize(1300, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(Window.screenSize.width / 2 - this.getSize().width / 2, Window.screenSize.height / 2 - this.getSize().height / 2);

        this.setVisible(true);
    }

    public void updateText(String append) {
        String tempText = feed.getText().substring(0, feed.getText().lastIndexOf("</html>"));
        append = append.replace("Fra", "<br> Fra");
        tempText += "<br>" + append + "</html>";
        feed.setText(tempText);
    }

}
