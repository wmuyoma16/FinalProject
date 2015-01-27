/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientmain;

import javax.swing.JFrame;

/**
 *
 * @author wilondja
 */
public class ClientMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClientClass clientObject;
        clientObject= new ClientClass("127.0.0.1");
        clientObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientObject.clientRunning();
   
    }
}
