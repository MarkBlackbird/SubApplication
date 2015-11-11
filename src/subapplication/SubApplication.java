/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subapplication;

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
        List<String> filecontent = null;
        try{
            filecontent = Files.readAllLines(FileSystems.getDefault().getPath("resolv.txt"));
        } catch (IOException ex){
            Logger.getLogger(NetworkHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        String netinfo[] = filecontent.get(0).split("\\s+");
        NetworkHandler nh = new NetworkHandler(netinfo[0], Integer.parseInt(netinfo[1]));
        AlarmGenerator gen = new AlarmGenerator(nh);
        gen.createAndShowGUI();
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
