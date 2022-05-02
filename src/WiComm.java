import com.jcraft.jsch.*;

import javax.swing.*;
import java.io.ByteArrayOutputStream;

public class WiComm {
    public static void commitBlend(String username, String ip, String password, String sshPort, String ftpPort, String filePath, String outputFileName, String engine, String frame) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int frameNum = Main.tryParse(frame, 1);

                ftpSessionSend(username, ip, password, sshPort, filePath);

                String blendFile = filePath.substring(filePath.lastIndexOf('\\'));
                String blendPath = "/tmp/" + blendFile;

                sshBlender(username, ip, password, sshPort, blendPath, outputFileName, engine, frameNum);

                ftpSessionDownload(username, ip, password, ftpPort, filePath, outputFileName + ".png", frameNum);

                ftpSessionDelete(username, ip, password, ftpPort, blendFile);

                ftpSessionDelete(username, ip, password, ftpPort, addFrame(outputFileName + ".png", frameNum));
            }
        }).start();

    }

    public static void sshBlender(String username, String ip, String password, String portStr, String path, String outputFileName, String engine, int frame) {
        Session session = null;
        ChannelExec channel = null;

        int port = Main.tryParse(portStr, 22);

        try {
            session = new JSch().getSession(username, ip, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");

            String command = String.format("blender -b %s -E %s -o //%s -f %s", path, engine, outputFileName, frame);
            channel.setCommand(command);

            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.connect();

            SSHFeed feed = new SSHFeed("Progress");
            while (channel.isConnected()) {
                Thread.sleep(100);
                feed.updateText(responseStream.toString());
            }


            if (channel.getExitStatus() == 127) {
                JOptionPane.showMessageDialog(null, "Blender isn't in your PATH environment variable or isn't installed. Please fix and try again.");
            }

            String responseString = responseStream.toString().replace("Finished", "<br> Finished");
            feed.updateText("<br>" + responseString);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ensure that SSH is open on port " + port + "!", "Uh owh, ewwow! ÒwÓ", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    public static void ftpSessionSend(String username, String ip, String password, String portStr, String path) {
        Session session = null;
        ChannelSftp channel = null;

        int port = Main.tryParse(portStr, 20);

        try {
            session = new JSch().getSession(username, ip);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();
            System.out.println("pot");

            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();

            String fileName = path.substring(path.lastIndexOf('\\'));

            channel.put(path, "/tmp/" + fileName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ensure that (S)FTP is open on port " + port + "!", "Uh owh, ewwow! ÒnÓ", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    public static void ftpSessionDownload(String username, String ip, String password, String portStr, String path, String downFile, int frame) {
        Session session = null;
        ChannelSftp channel = null;

        int port = Main.tryParse(portStr, 20);

        try {
            session = new JSch().getSession(username, ip);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();

            String outPath = path.substring(0, path.lastIndexOf('\\')) + "\\" + downFile.substring(0, downFile.lastIndexOf('.')) + String.format("%4d", frame).replace(' ', '0') + downFile.substring(downFile.lastIndexOf('.'));

            channel.get("/tmp/" + addFrame(downFile, frame), outPath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ensure that (S)FTP is open on port " + port + "!", "Uh owh, ewwow! ÒnÓ", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    public static void ftpSessionDelete(String username, String ip, String password, String portStr, String delFile) {
        Session session = null;
        ChannelSftp channel = null;

        int port = Main.tryParse(portStr, 20);

        try {
            session = new JSch().getSession(username, ip);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();

            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();

            channel.rm("/tmp/" + delFile);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ensure that (S)FTP is open on port " + port + "!", "Uh owh, ewwow! ÒnÓ", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    public static String addFrame(String file, int frame) {
        return file.substring(0, file.lastIndexOf('.')) + String.format("%4d", frame).replace(' ', '0') + file.substring(file.lastIndexOf('.'));
    }
}
