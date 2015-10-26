/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subapplication;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.AlarmEvent;
import networking.NetworkHandler;

/**
 *
 * @author kosma
 */
public class SubApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NetworkHandler nh = new NetworkHandler("127.0.0.1",10800);
        try {
            nh.sendAlarm(new AlarmEvent(AlarmEvent.AlarmCode.FLOOD,null));
        } catch (IOException ex) {
            Logger.getLogger(SubApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
