/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import networking.StandardDialog;
import javax.swing.ImageIcon;

/**
 *
 * @author kosma
 */
public class GUIException extends Exception{
    public GUIException (String message)
    {
        new StandardDialog(message);
    }
    public GUIException (String message,ImageIcon imIcon)
    {
        new StandardDialog(message,imIcon);
    }
}
