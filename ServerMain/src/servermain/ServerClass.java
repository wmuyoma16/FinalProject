/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servermain;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author wilondja
 */
public class ServerClass extends JFrame {

    private JTextField endUser;
    private JTextArea chatSpace;
    private ObjectOutputStream outPutText;
    private ObjectInputStream inPutText;
    private ServerSocket server;
    private Socket connect;

    public ServerClass() {
        super("Server");
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
        add(new JScrollPane(chatSpace));
        setSize(400, 200);
        setVisible(true);

    }
/// run the server

    public void serverRunning() {
        try {
            server = new ServerSocket(6789, 100);
            while (true) {
                try {
                    waitForConnection();
                    setUpStreams();
                    whileChatIsConnected();

                } catch (EOFException eofException) {
                    showMessage("\n Server disconnected ");
                } finally {
                    closeEverything();
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    private void waitForConnection() throws IOException{
        showMessage("Waiting for conection..\n");
        connect= server.accept();
        showMessage("Now connected to "+ connect.getInetAddress().getHostName());
        
    }
    private void setUpStreams() throws IOException{
        outPutText= new ObjectOutputStream(connect.getOutputStream());
        outPutText.flush();
        inPutText= new ObjectInputStream(connect.getInputStream());
        showMessage("\n Streams are set\n");
    }
    private  void whileChatIsConnected() throws IOException{
        String message="You are now connected";
        sendMessage(message);
        allowTyping(true);
        do{
            try{
                message=(String) inPutText.readObject();
                showMessage("\n" + message);
                
            }catch(ClassNotFoundException classNotFoundException){
                showMessage("\n Do not understand that ");
            }
            
        }while(!message.equals("CLIENT - END"));
    }
    private void closeEverything(){
        showMessage("\n Connection ended");
        allowTyping(false);
        try{
            outPutText.close();
            inPutText.close();
            connect.close();
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
        
    }
    private void sendMessage(String message){
        try{
            outPutText.writeObject("SERVER :"+ message);
            outPutText.flush();
            showMessage("\n SERVER :"+ message);
            
        }catch(IOException ioException){
            chatSpace.append("\n ERROR message was not sent\n");
        }
    }
    private void showMessage(final String text){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                       chatSpace.append(text);
                    }
                }
        );
    }
    private void allowTyping(final boolean allow){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        endUser.setEditable(allow);
                    }
                }
        );
        
    }
}
