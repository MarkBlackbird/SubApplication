/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *
 * @author kosma
 */
public class StandardDialog extends JDialog implements ActionListener{
    private JButton closeButton;
    public StandardDialog (String message)
    {
        try {
            createAndShowGUI(message,null);
        } catch (IOException ex) {
            //Logger.getLogger(AlarmDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public StandardDialog (String message,ImageIcon imIcon)
    {
        try {
            createAndShowGUI(message,imIcon);
        } catch (IOException ex) {
            //Logger.getLogger(AlarmDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void createAndShowGUI(String message, ImageIcon imIcon) throws IOException
    {
        // Must be called before creating JDialog for
        // the desired effect
        setUndecorated(true);
        setAlwaysOnTop(true);
        // Set some layout
        setLayout(new BorderLayout());
        
        closeButton = new JButton("Ok.");
        closeButton.addActionListener(this);
        add(closeButton, BorderLayout.SOUTH);
        JLabel ico = new JLabel(message);
        if(imIcon!=null)
            ico.setIcon(imIcon);
        add(ico, BorderLayout.CENTER);

        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dialogSize = getSize();
        setLocation(screenSize.width/2-dialogSize.width/2, screenSize.height/2-dialogSize.height/2);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==closeButton)
        {
            dispose();
        }
    }
}
