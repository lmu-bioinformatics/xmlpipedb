/*
 * Created on May 29, 2005
 *
 */
package gui;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import app.ConfigurationController;

/**
 * @author J.Nicholas
 *
 */
public class ConfigurationPanel extends JPanel {
	   
	
	public ConfigurationPanel(ConfigurationController cc  ){
		_configControl = cc;
        createComponents();
        layoutComponents();

    }
	
	
    private void layoutComponents() {
        this.setLayout(new BorderLayout());
//        add(_testLabel, BorderLayout.CENTER);
//        add(_driverClass, BorderLayout.CENTER);
        Box bx1 = new Box(BoxLayout.Y_AXIS);
        Box bx2 = new Box(BoxLayout.Y_AXIS);
        Box bxMain = new Box(BoxLayout.X_AXIS);
        bx1.add(_testLabel);
        bx2.add(_driverClass);
        bxMain.add(bx1);
        bxMain.add(bx2);
        add(bxMain, BorderLayout.CENTER);
        add(_save, BorderLayout.SOUTH);
        
    }

    /**
     * 
     */
    private void createComponents() {
        _testLabel = new JLabel("Config");
        //"org.postgresql.Driver"
        _driverClass = new JTextArea(_configControl.loadHib_Conf() + "\n\n\n" + _configControl.loadHibProperties());
        _save = new JButton("Save");
        
    }
    
    //### DEFINE VARS ###
    ConfigurationController _configControl;
    JLabel _testLabel;
    JTextArea _driverClass, _dialect, _url, _username, _password, _substitutions;
    JButton _save;
    /*
   hibernate.dialect net.sf.hibernate.dialect.PostgreSQLDialect
hibernate.connection.driver_class org.postgresql.Driver
hibernate.connection.url jdbc:postgresql://localhost:5432/uniprot
hibernate.connection.username username
hibernate.connection.password password
hibernate.query.substitutions yes 'Y', no 'N'
    */
	
} // end class ImportPanel
