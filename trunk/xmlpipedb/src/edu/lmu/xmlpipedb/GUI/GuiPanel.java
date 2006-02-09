/*
 * guiPanel.java
 *
 * Created on February 8, 2006, 9:29 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package edu.lmu.xmlpipedb.GUI;

import edu.lmu.xmlpipedb.GUI.util.JLabelComponentFactory;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class GuiPanel extends JPanel{
    
    
    private AbstractAction _startButtonAction; 
    private JButton _loadXmlToDatabaseButton; 
    private JTextField _xmlFileBox;
    private JTextField _dataBaseNameBox; 
    private JTextField _dataBaseUserBox; 
    private JPasswordField _dataBasePasswordBox; 
    private JProgressBar _xmlLoadProgress; 
    private Gui _parent; 
    
    
    public GuiPanel(Gui parent) {
        _parent = parent; 

        createActions();
        createComponents();
        layoutComponents(); 
    }
    
    public void setXmlFilePath(String path)
    {
        _xmlFileBox.setText(path); 
    }
    private void createActions()
    {
        createStartButtonAction(); 
    }
    private void createStartButtonAction()
    {
       
        final GuiPanel me = this;  
        _startButtonAction = new AbstractAction(){
            public void actionPerformed(ActionEvent event)
            {
                
                if(!isThereDataInTheFields())
                {
                    JOptionPane.showMessageDialog(me, "All fields are required", "Input Needed", JOptionPane.ERROR_MESSAGE); 
                    return; 
                }
                try{
                    _parent.startXmlToDataBase(); 
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(me, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
                }
            }
        };        
    }
    private void createComponents()
    {
        _loadXmlToDatabaseButton = new JButton(_startButtonAction); 
        _loadXmlToDatabaseButton.setText("Start"); 
        _dataBaseNameBox = new JTextField(); 
        _dataBaseUserBox = new JTextField(); 
        _dataBasePasswordBox = new JPasswordField();  
        _xmlFileBox = new JTextField(); 
        _xmlLoadProgress = new JProgressBar(); 
        
    }

    private void layoutComponents()
    {
        //SpringLayout layout = new SpringLayout(); 
        //BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS); 
        BorderLayout layout = new BorderLayout(5,5); 
        setLayout(layout); 
        JLabel xmlLabel = new JLabel("XML File Path: "); 
        JLabel nameLabel = new JLabel("Database Name: "); 
        JLabel userLabel = new JLabel("Database User: ");
        JLabel passwordLabel = new JLabel("Database Password: ");
        
        Box label = Box.createVerticalBox();
        label.add(Box.createVerticalGlue()); 
        label.add(xmlLabel); 
        label.add(Box.createVerticalGlue()); 
        label.add(nameLabel); 
        label.add(Box.createVerticalGlue()); 
        label.add(userLabel); 
        label.add(Box.createVerticalGlue()); 
        label.add(passwordLabel); 
        label.add(Box.createVerticalGlue()); 
        add(label, BorderLayout.WEST); 
        
        Box text = Box.createVerticalBox(); 
        text.add(Box.createVerticalGlue()); 
        text.add(_xmlFileBox); 
        text.add(Box.createVerticalGlue()); 
        text.add(_dataBaseNameBox); 
        text.add(Box.createVerticalGlue()); 
        text.add(_dataBaseUserBox); 
        text.add(Box.createVerticalGlue()); 
        text.add(_dataBasePasswordBox); 
        text.add(Box.createVerticalGlue()); 
        add(text, BorderLayout.CENTER); 
        
        Box buttonBox = Box.createHorizontalBox(); 
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(_loadXmlToDatabaseButton); 
        buttonBox.add(Box.createHorizontalStrut(5)); 
        
        Box southBox = Box.createVerticalBox(); 
        southBox.add(Box.createVerticalGlue()); 
        southBox.add(JLabelComponentFactory.createHorizontalLabelComponent("Progress: ", 5, _xmlLoadProgress)); 
        southBox.add(Box.createVerticalGlue()); 
        southBox.add(buttonBox); 
        southBox.add(Box.createVerticalGlue()); 
        add( southBox, BorderLayout.SOUTH);
    
    }
    private boolean isThereDataInTheFields()
    {
       
        return (_xmlFileBox.getText().length()>0)&&(_dataBaseNameBox.getText().length()>0)&&(_dataBaseUserBox.getText().length()>0)&& (_dataBasePasswordBox.getPassword().length>0); 
    }
    
}
