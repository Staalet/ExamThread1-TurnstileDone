/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telnet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author christian
 */
public class TurnstileThread extends Thread {
    Socket socket;
    SharedCounter sc = new SharedCounter();
    public TurnstileThread(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try{
            PrintWriter pw;
            Scanner scan;
            boolean stop = false;
            pw = new PrintWriter(socket.getOutputStream(), true);
            scan = new Scanner(socket.getInputStream());
            Turnstile t = new Turnstile();
            pw.println("hello counter " + Server.ID);
            Server.ID++;
            while(true) {
                pw.println("How meny did this one count today? ");
                String answer = scan.nextLine();//blocking call 
                sc.countSum(Integer.parseInt(answer));
                pw.println("Thanks! press any button to see the total amount of spectators");
               // String answer2 = scan.nextLine();
                pw.println("The total amount of spectators on Royal Arena is " + sc.getCount());
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
