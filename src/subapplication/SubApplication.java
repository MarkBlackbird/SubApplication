/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subapplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//<<<<<<< HEAD
//=======
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.AlarmEvent;
//>>>>>>> origin/master
import networking.NetworkHandler;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;


/**
 *
 * @author kosma
 */
public class SubApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//<<<<<<< HEAD
        // FileReader reads text files in the default encoding.
        FileReader fileReader;
        try {
            fileReader = new FileReader("resolv.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String netinfo[] = bufferedReader.readLine().split("\\s+");
            NetworkHandler nh = new NetworkHandler(netinfo[0], Integer.parseInt(netinfo[1]));
            AlarmGenerator gen = new AlarmGenerator(nh);
            gen.createAndShowGUI();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SubApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SubApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
//=======
        /*NetworkHandler nh = new NetworkHandler("127.0.0.1",10800);
        try {
            nh.sendAlarm(new AlarmEvent(AlarmEvent.AlarmCode.FLOOD,null));
        } catch (IOException ex) {
            Logger.getLogger(SubApplication.class.getName()).log(Level.SEVERE, null, ex);
        }*/
//>>>>>>> origin/master
    }
    
}
