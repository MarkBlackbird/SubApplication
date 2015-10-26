/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subapplication;
import networking.*;
import java.io.IOException;

//GUI
import java.awt.Color;
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
     NetworkHandler nethandler;
     
     public AlarmGenerator(NetworkHandler handler){
         nethandler = handler;
     }

    public JPanel createContentPane (){
        
        JPanel totalGUI = new JPanel();

        AlarmEvent.AlarmCode alarms[] = AlarmEvent.AlarmCode.values();
        alarmChooser = new JComboBox(alarms);
        alarmChooser.setSelectedIndex(0);
        
        JPanel boxPanel = new JPanel(new GridLayout(2, 2, 20, 20));
       
        sendAlarmButton = new JButton("Send the alarm");
        boxPanel.add(sendAlarmButton);
        sendAlarmButton.addActionListener(this);

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
        }
    }

    public void createAndShowGUI() {
    
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Alarm generator");
        frame.setContentPane(createContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
