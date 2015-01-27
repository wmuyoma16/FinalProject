/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servermain;

import javax.swing.JFrame;

/**
 *
 * @author wilondja
 */
public class ServerMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
           ServerClass serverObject=new ServerClass();
           serverObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           serverObject.serverRunning();
           
    }
    
}
