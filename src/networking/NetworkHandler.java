/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import exceptions.GUIException;
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
    private boolean connected=false;
    /* Sends echo message through the network, and awaits response. 
       Returns delay in millis between sending and receiving.
    */
    public long sendEcho () throws IOException
    {
        if(out!=null)
        {
            long time = System.currentTimeMillis();
            sendData(-1); //echo
            int ID_Received = in.readInt(); //not used
            time-=System.currentTimeMillis();
            System.out.println("Echoed for: "+(-time)+" with device "+ID_Received);
            return -time;
        }
        return -1; //Failed
    }
    public void sendEchoResponse () throws IOException
    {
        out.writeInt(-2);
        out.writeInt(deviceData.ID);
    }
    /* Sends basic data about this device. Do not call this method!
    */
    private void sendCommStartUpData () throws IOException
    {
        out.writeInt(deviceData.ID);
        out.writeInt(deviceData.castDeviceCodeToInt(deviceData.deviceCode));
        out.writeUTF(deviceData.deviceName);
    }
    /*
        Call this method to inform external device about event alarmCode, and
        it's magnitude. Size of magnitude depends upon event Type and can be null
    */
    public void sendAlarm(int alarmCode, int[]magnitude) throws IOException
    {
        if(connected)
        {
            out.writeInt(1); //sending info about what we will send next
            out.writeInt(alarmCode);
            if(magnitude!=null)
            {
                out.writeInt(magnitude.length);
                for(int i=0;i<magnitude.length;i++)
                {
                    out.writeInt(magnitude[i]);
                }
            }else{
                out.writeInt(0);
            }
        }
    }
    /*
        Call this method to inform external device about event AlarmEvent
    */
    public void sendAlarm(AlarmEvent e) throws IOException
    {
        if(connected)
        {
            out.writeInt(1); //sending info about what we will send next
            out.writeInt(e.castAlarmCodeToInt(e.alarmCode));
            if(e.magnitude!=null)
            {
                out.writeInt(e.magnitude.length);
                for(int i=0;i<e.magnitude.length;i++)
                {
                    out.writeInt(e.magnitude[i]);
                }
            }else{
                out.writeInt(0);
            }
        }
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
    DeviceData deviceData;
    public NetworkHandler (String ipAdress, int port, DeviceData dev)
    {
        this.ipAdress=ipAdress;
        this.port=port;
        this.deviceData=dev;
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
        try{
            while(true)
            {
            try {
                Client(ipAdress,port);
                int newPort = in.readInt();
                if(newPort==65536)
                    throw new GUIException
                ("Serwer odmówił połączenia za względu na błędną konfigurację");
                killconnection();
                boolean noConn=true;
                while(noConn)
                {
                    try{
                        Client(ipAdress,newPort);
                        sendCommStartUpData();
                        if(in.readInt()<1)
                            throw new GUIException
                ("Serwer odmówił połączenia za względu na błędną konfigurację");
                        noConn=false;
                    }catch(Exception e){
                        if(e.getClass()==GUIException.class)
                            throw e;
                        Thread.sleep(500);
                    }
                }
                connected=true;
                while(true)
                {
                    int number = 0;
                    try {
                         number = in.readInt();
                        String str;
                        switch(number)
                        {
                             case 1: //next is int
                            {
                                int alarmType = in.readInt(); //Type of alarm
                                int magLength = in.readInt(); //length of maginitude
                                int[] magnitude = new int[magLength];
                                for(int i=0;i<magLength;i++)
                                {
                                    magnitude[i]=in.readInt();
                                }
                                //alarmList.add(new AlarmEvent(AlarmEvent.castIntToAlarmCode(alarmType),magnitude));
                                break;
                            }
                            case 2: //next is String, Right now not used
                            {
                                str = in.readUTF();
                        
                                break;
                            }
                            case -1: //next is echo request
                            {
                                sendEchoResponse ();
                        
                                break;
                            }
                            case -2:
                            {
                                
                                break;
                            }
                        }
                    } catch (IOException ex) {
                        //Logger.getLogger(NetworkHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Thread.sleep(2500);
                }
            } catch (Exception e) {
                if(e.getClass()==GUIException.class)
                    throw e;
            }
            try {
                Thread.sleep(2500); //Wait a bit and renew connection attempt
            } catch (InterruptedException ex) {
                //Logger.getLogger(NetworkHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }catch(Exception e){
        
        }
    }
}
