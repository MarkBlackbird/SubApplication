/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kosma
 */
public class NetworkHandler extends Thread
{
    /* Sends echo message through the network, and awaits response. 
       Returns delay between sending and receiving.
    */
    public int sendEcho ()
    {
        if(out!=null)
            sendData(-1); //echo
        return 0; //TODO
    }
    /* Sends basic data about this device. Do not call this method!
    */
    private void sendCommStartUpData ()
    {
        
    }
    /*
        Call this method to inform external device about event alarmCode, and
        it's magnitude. Size of magnitude depends upon event Type and can be null
    */
    public void sendAlarm(int alarmCode, int[]magnitude)
    {
        
    }
    /*
        Call this method to inform external device about event AlarmEvent
    */
    public void sendAlarm(AlarmEvent e)
    {
        
    }
    /*
        If external device sends some data it will be put in buffer. This method
        allows recovery of this buffer. If buffer is empty exception will be 
        thrown.
    */
    public int[] recoverBuffer()
    {
        int []dataBuffer2 = dataBuffer;
        dataBuffer=null;
        return dataBuffer2;
    }
    
    
    DataOutputStream out;
    DataInputStream in;
    Socket clientSocket;
    String ipAdress; 
    int port;
    int []dataBuffer;
    public NetworkHandler (String ipAdress, int port)
    {
        this.ipAdress=ipAdress;
        this.port=port;
        start();
    }
    private void Client(String hostName,int portNumber) throws IOException
    {
        clientSocket = new Socket(hostName, portNumber);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());
    }
    private void killconnection() throws IOException
    {
        if(clientSocket!=null)
        {
            clientSocket.close();
            clientSocket=null;
        }
    }
    public void sendData (int number)
    {
        try {
            if(out!=null)
            {
                out.writeInt(1); //info about data send: integer
                out.writeInt(number);
            }
        } catch (IOException ex) {
            Logger.getLogger(NetworkHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void sendData (String str)
    {
        try {
            if(out!=null)
            {
                out.writeInt(2);//info about data send: String
                out.writeUTF(str);
            }
        } catch (IOException ex) {
            Logger.getLogger(NetworkHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void shutdown ()
    {
        try {
            if(out!=null)
            {
                out.close();
                out=null;
            }
            if(in!=null)
            {
                in.close();
                in=null;
            }
            killconnection();
            
        } catch (IOException ex) {
            Logger.getLogger(NetworkHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run()
    {
        while(true)
        {
            try {
                Client(ipAdress,port);
            } catch (Exception ex) {
                //Logger.getLogger(NetworkHandler.class.getName()).log(Level.OFF, null, ex);
            }
            try {
                Thread.sleep(2500); //Wait a bit and renew connection attempt
            } catch (InterruptedException ex) {
                Logger.getLogger(NetworkHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
