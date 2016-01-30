/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subapplication;
import networking.*;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

//GUI
import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Krysia
 */
public class AlarmGenerator implements  ActionListener{
     JComboBox alarmChooser;
     JButton sendAlarmButton;
     JButton generateRandomAlarmsButton;
     JButton stopGeneratingRandomAlarmsButton;
     RandomAlarmGenerator randomAlarmGenerator;
     NetworkHandler nethandler;
     
     class RandomAlarmGenerator extends Thread{
         private volatile boolean running = false;
         @Override
         public void run()
         {
             while(true)
             {
                while(running)
                {
                   int interval = ThreadLocalRandom.current().nextInt(3, 10);
                   try{
                      Thread.sleep(interval*1000);
                   } catch (InterruptedException e){
                       Logger.getLogger(NetworkHandler.class.getName()).log(Level.SEVERE, null, e);
                   }
                   generateRandomAlarm();
                }
             }
         }
         public synchronized void pauseThread() throws InterruptedException
         {
            running = false;
         }
         public synchronized void resumeThread()
         {
            running = true;
         }
         public synchronized boolean isRunning()
         {
             return running;
         }
     }
     
     public AlarmGenerator(NetworkHandler handler){
         nethandler = handler;
         randomAlarmGenerator = new RandomAlarmGenerator();
     }

    public JPanel createContentPane (){
        
        JPanel totalGUI = new JPanel();

        AlarmEvent.AlarmCode alarms[] = AlarmEvent.AlarmCode.values();
        alarmChooser = new JComboBox(alarms);
        alarmChooser.setSelectedIndex(0);
        
        JPanel boxPanel = new JPanel(new GridLayout(2, 2, 20, 20));
       
        sendAlarmButton = new JButton("Send the alarm");
        generateRandomAlarmsButton = new JButton("Start generating random alarms");
        stopGeneratingRandomAlarmsButton = new JButton("Stop generating random alarms");
        boxPanel.add(sendAlarmButton);
        sendAlarmButton.addActionListener(this);
        boxPanel.add(generateRandomAlarmsButton);
        generateRandomAlarmsButton.addActionListener(this);
        boxPanel.add(stopGeneratingRandomAlarmsButton);
        stopGeneratingRandomAlarmsButton.addActionListener(this);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
        
        bottomPanel.add(Box.createRigidArea(new Dimension(0,10)));
        bottomPanel.add(alarmChooser);
        bottomPanel.add(Box.createRigidArea(new Dimension(0,20)));
        bottomPanel.add(boxPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0,10)));
        
        totalGUI.add(bottomPanel);
        
        totalGUI.setOpaque(true);
        return totalGUI;
    }
    
    private synchronized void generateRandomAlarm()
    {
        AlarmEvent.AlarmCode alarms[] = AlarmEvent.AlarmCode.values();
        int alarmId = ThreadLocalRandom.current().nextInt(0, alarms.length-1);
        AlarmEvent alarm = new AlarmEvent(alarms[alarmId]);
        AlarmEvent.AlarmCode alrmcd = alarm.getAlarmCode();
        int[] mag = null;
        switch(alrmcd)
        {
            case TEMPERATURE:
            {
                boolean highlow = ThreadLocalRandom.current().nextBoolean();
                mag = new int[1];
                if (highlow == true)
                    mag[0] = ThreadLocalRandom.current().nextInt(0, 19);
                else
                    mag[0] = ThreadLocalRandom.current().nextInt(25, 40);
                break;
            }
            case HUMIDITY:
            {
                mag = new int[1];
                mag[0] = ThreadLocalRandom.current().nextInt(80, 100);
                break;
            }
            case HR:
            {
                boolean highlow = ThreadLocalRandom.current().nextBoolean();
                mag = new int[1];
                if (highlow == true)
                    mag[0] = ThreadLocalRandom.current().nextInt(90, 150);
                else
                    mag[0] = ThreadLocalRandom.current().nextInt(0, 50);
                break;
            }
            case ATM_PRESSURE:
            {
                boolean highlow = ThreadLocalRandom.current().nextBoolean();
                mag = new int[1];
                if (highlow == true)
                    mag[0] = 1100;
                else
                    mag[0] = 900;
                break;
            }
            case BLOOD_PRESSURE:
            {
                boolean highlow = ThreadLocalRandom.current().nextBoolean();
                mag = new int[2];
                if (highlow == true)
                {
                    mag[0] = ThreadLocalRandom.current().nextInt(0, 100);
                    if (mag[0] == 0)
                    {
                        mag[1] = 0;
                        break;
                    }
                    if (mag[0] < 11)
                        mag[0] = 11;
                    mag[1] = ThreadLocalRandom.current().nextInt(1, 60);
                    if(mag[1] > (mag[0]-10))
                        mag[1] = mag[0] - 10;
                    break;
                }
                else
                {
                    mag[0] = ThreadLocalRandom.current().nextInt(140, 240);
                    mag[1] = ThreadLocalRandom.current().nextInt(90, 150);
                    if(mag[1] > (mag[0]-10))
                        mag[1] = mag[0]-10;
                    break;
                }
            }
            case OXYGEN_CONTENT:
            {
                mag = new int[1];
                mag[0] = ThreadLocalRandom.current().nextInt(0, 20);
                break;
            }
            case FUMES_CONTENT:
            {
                mag = new int[1];
                mag[0] = ThreadLocalRandom.current().nextInt(1, 20);
                break;
            }
            case RADIATION:
            {
                //in microsiverts:
                mag = new int[1];
                mag[0] = ThreadLocalRandom.current().nextInt(1, 2000000);
                break;
            }
            case BLOOD_FLOW:
            {
                boolean highlow = ThreadLocalRandom.current().nextBoolean();
                mag = new int[1];
                //unit: ml/min (cardiac output)
                if (highlow == true)
                    mag[0] = ThreadLocalRandom.current().nextInt(0, 4000);
                else
                    mag[0] = ThreadLocalRandom.current().nextInt(8000, 14000);
                break;
            }
            case SATURATION:
            {
                mag = new int[1];
                mag[0] = ThreadLocalRandom.current().nextInt(0, 90);
                break;
            }
            case BODY_TEMPERATURE:
            {
                boolean highlow = ThreadLocalRandom.current().nextBoolean();
                mag = new int[1];
                if (highlow == true)
                    mag[0] = ThreadLocalRandom.current().nextInt(20, 35);
                else
                    mag[0] = ThreadLocalRandom.current().nextInt(37, 42);
                break;
            }

        }
        if(mag != null)
            alarm.setMagnitude(mag);
        
        try{
            nethandler.sendAlarm(alarm);
        } catch (IOException ex){
            Logger.getLogger(NetworkHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sendAlarmButton)
        {
            int temp = alarmChooser.getSelectedIndex();
            AlarmEvent.AlarmCode alarms[] = AlarmEvent.AlarmCode.values();
            try{
            nethandler.sendAlarm(new AlarmEvent(alarms[temp]));
            } catch (IOException ex){
                Logger.getLogger(NetworkHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        if(e.getSource() == generateRandomAlarmsButton)
        {
            if(randomAlarmGenerator.isRunning() == false)
                randomAlarmGenerator.resumeThread();
            return;
        }
         if(e.getSource() == stopGeneratingRandomAlarmsButton)
        {
            if(randomAlarmGenerator.isRunning() == true)
                try{
                    randomAlarmGenerator.pauseThread();
                } catch (InterruptedException ex){
                    Logger.getLogger(NetworkHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    
    public void createAndShowGUI() {
    
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Alarm generator");
        frame.setContentPane(createContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        randomAlarmGenerator.start();
    }
}
