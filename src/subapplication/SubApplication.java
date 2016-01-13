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
import java.nio.file.Files;
//<<<<<<< HEAD
//=======
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.AlarmEvent;
//>>>>>>> origin/master
import networking.*;
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
            //List<String> devdata = Files.readAllLines(FileSystems.getDefault().getPath("resolv.txt"), "ISO-8859-1");
            String netinfo[] = bufferedReader.readLine().split("\\s+");
            int devid = Integer.parseInt(bufferedReader.readLine());
            String devname = bufferedReader.readLine();
            int devcode = Integer.parseInt(bufferedReader.readLine());
            DeviceData dev = new DeviceData(devid, devname, devcode);
            NetworkHandler nh = new NetworkHandler(netinfo[0], Integer.parseInt(netinfo[1]), dev);
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
