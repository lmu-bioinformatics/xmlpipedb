/*
 * Created on May 21, 2005
 *
 */
package edu.lmu.xmlpipedb.util.demo;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * XMLPipeDBUtils is a runnable application that uses the XMLPipeDBUtils
 * library.
 * 
 * @author J. Nicholas
 */
public class XMLPipeDBUtils {
    public static void main(String[] args) {
        // FIXME: the look and feel should come from the options file
        String crossPlatformLAF = UIManager.getCrossPlatformLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(crossPlatformLAF);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch(InstantiationException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        } catch(UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        (new MainController()).start();
    }
}
