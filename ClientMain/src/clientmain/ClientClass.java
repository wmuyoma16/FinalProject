/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientmain;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author wilondja
 */
public class ClientClass extends JFrame {

    private JTextField endUser;
    private JTextArea chatSpace;
    private ObjectOutputStream outPutText;
    private ObjectInputStream inPutText;
    private String message = "";
    private String serverIP;
    private Socket connect;

    public ClientClass(String host) {
        super("Client");
        serverIP = host;
        endUser = new JTextField();
        endUser.setEditable(false);
        endUser.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        sendMessage(event.getActionCommand());
                        endUser.setText("");
                    }
                }
        );
        add(endUser, BorderLayout.SOUTH);
        chatSpace = new JTextArea();
        add(new JScrollPane(chatSpace), BorderLayout.CENTER);
        setSize(400, 200);
        setVisible(true);
    }

    public void clientRunning() {
        try {
            connectWithServer();
            setStream();
            whileChatIsRunning();
        } catch (EOFException eofException) {
            showMessage("\n User ended session");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            closeEveryThing();
        }
    }

    private void connectWithServer() throws IOException {
        showMessage("\nTrying to connect...\n");
        connect = new Socket(InetAddress.getByName(serverIP), 6789);
        showMessage("\nConnected to " + connect.getInetAddress().getHostName());
    }

    private void setStream() throws IOException {
        outPutText = new ObjectOutputStream(connect.getOutputStream());
        outPutText.flush();
        inPutText = new ObjectInputStream(connect.getInputStream());
        showMessage("\n Streams are set \n");

    }

    private void whileChatIsRunning() throws IOException {
        //sendMessage(message);
        allowTyping(true);
        do {
            try {
                message = (String) inPutText.readObject();
                showMessage("\n" + message);
            } catch (ClassNotFoundException classNotFoundException) {
                showMessage("\n Not sure about what you just sent");

            }

        } while (!message.equals("SERVER-END"));
    }

    private void closeEveryThing() {
        showMessage("\n Connection ended \n");
        allowTyping(false);
        try {
            outPutText.close();
            inPutText.close();
            connect.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        try {
            outPutText.writeObject("ClIENT:" + message);
            outPutText.flush();
            showMessage("\n CLIENT :" + message);
        } catch (IOException ioException) {
            chatSpace.append("\n ERROR we could not send");
        }

    }

    private void showMessage(final String text) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        chatSpace.append(text);
                    }

                }
        );
    }

    private void allowTyping(final boolean trueOrFalse) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        endUser.setEditable(trueOrFalse);
                    }

                }
        );

    }
}
