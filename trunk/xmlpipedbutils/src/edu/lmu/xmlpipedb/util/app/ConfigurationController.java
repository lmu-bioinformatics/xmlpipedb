package edu.lmu.xmlpipedb.util.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import edu.lmu.xmlpipedb.util.gui.ConfigurationPanel;
import edu.lmu.xmlpipedb.util.gui.HibernatePropertiesTableModel;
import edu.lmu.xmlpipedb.util.resources.AppResources;


public class ConfigurationController {

	
	public ConfigurationController(String currentPropsUrl) {
		//getHibernateConfigPanel(null);
		_currentHibProps = loadHibProperties(currentPropsUrl);
	}
	
	public ConfigurationController(String currentPropsUrl, ConfigurationPanel cp) {
		//getHibernateConfigPanel(null);
		_currentHibProps = loadHibProperties(currentPropsUrl);
		_callingPanel = cp;
	}

	
	private Properties loadHibProperties(String propertiesPath){
		Properties props = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream(propertiesPath);
			props.load(fis);
			if( _firstHibPropLoad ){
				_hibRevertProperties = new Properties();
				_hibRevertProperties = props;
				_firstHibPropLoad = false;
			}
		} catch (FileNotFoundException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
		return props;
	}
	
	private Properties loadProperties(String propertiesPath){
		Properties props = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream(propertiesPath);
			props.load(fis);

		} catch (FileNotFoundException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
		return props;
	}
	
	public JPanel getHibernateConfigPanel(String folderUrl, String currFile){
		JPanel config = new JPanel();
		_currFolder = folderUrl;
		_currFile = currFile;
		
		config.setLayout(new BorderLayout());
		
		File f = new File(folderUrl);
		//System.out.printf("can read %s, absolute path %s\n", f.canRead(), f.getAbsolutePath() );
		File[] files = f.listFiles(new PropertiesFileNameFilter());
		if( files.length > 1 ){
			ArrayList fileList = new ArrayList();
			String selected = null;
						
			for( int i = 0; i < files.length; i++){
				String filename = files[i].getName();
				fileList.add(filename.substring(0, filename.lastIndexOf(".")));
				if( filename.equals(currFile)){
					createConfigPanel(config, files[i].getPath());
					selected = filename.substring(0, filename.lastIndexOf("."));
				}
			}// end for
			JComboBox head = new JComboBox(fileList.toArray());
			head.setSelectedItem(selected);
//			head.addItemListener(new MyItemListener());
			config.add(head, BorderLayout.NORTH);
			
		}else{
			if( files[0].isFile())
				createConfigPanel(config, files[0].getPath());
		}
		
		return config;
	}
	
//	private class MyItemListener implements ItemListener{
//
//		public void itemStateChanged(ItemEvent iEvent) {
//			_callingPanel.reloadConfigPanel( iEvent.getItem() + ".properties");
//			
//		}
//		
//	}
	
	private void createConfigPanel(JPanel config, String path) {
		Properties props = loadProperties(path);
		Box boxes = new Box(BoxLayout.Y_AXIS);
		
		Enumeration e = props.propertyNames();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			Enumeration eCur = _currentHibProps.propertyNames();
			JCheckBox isSaved = new JCheckBox();
			JLabel label = new JLabel(key);
			JTextField value = new JTextField();
			while(eCur.hasMoreElements()){
				String curKey = (String)eCur.nextElement();
				if( key.equals(curKey)){
					isSaved.setSelected(true);
					value.setText(_currentHibProps.getProperty(curKey));
					break;
				}
			}
			Box box = new Box(BoxLayout.X_AXIS);
			box.add(isSaved);
			box.add(label);
			box.add(value);
			boxes.add(box);
			boxes.add(Box.createVerticalStrut(5));
//			Property p = new Property( key, props.getProperty(key));
//			hptm.addProperty(p);
		}
		boxes.add(Box.createVerticalGlue());
		config.add(boxes, BorderLayout.CENTER);
	} // end createConfigPanel


	public void storeHibProperties( Properties props){
//		Properties props = new Properties();
		
//		for( int i = 0; i < mod.getRowCount(); i++){
//			props.setProperty((String)mod.getValueAt(i, 0), (String)mod.getValueAt(i, 1));
//		}
		
		try{
			FileOutputStream fis = new FileOutputStream(AppResources.optionString("hibernate_properties_url"));
			props.store(fis, null);
			_currentHibProps = props;
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}// end storeHibProperties
	
	public void saveProperties(JPanel config){
		Properties props = _currentHibProps;
		Component[] box = config.getComponents();
		Box bx = (Box)box[0];
		
		//Component[] comps = box[0].();
		for( int i = 0; i < bx.getComponentCount(); i++){
			Box b = (Box)bx.getComponent(i);
			boolean cb = ((JCheckBox)b.getComponent(0)).isSelected();
			String label = ((JLabel)b.getComponent(1)).getText();
			if( cb ){
				String value = ((JTextField)b.getComponent(2)).getText();
				props.setProperty(label, value);
			}else{
				props.remove(label);
			}
		}
		storeHibProperties(props);
		
	}
	
	public TableModel loadOriginalHibProps(){
		return getHibProperties(loadHibProperties(AppResources
				.optionString("original_hibernate_properties_url")));
	}

	public TableModel loadCurrentHibProps(){
		return getHibProperties(loadHibProperties(AppResources
				.optionString("hibernate_properties_url")));
	}

	public TableModel loadRevertedHibProps(){
		return getHibProperties(_hibRevertProperties);
	}
	
	
//	public String loadHib_Conf() {
//		Properties hib_conf = new Properties();
//		String s = "";
//		
//		try{
//			
//			//FileInputStream fis = new FileInputStream(_hibPropFilePath);
//			FileInputStream fis = new FileInputStream(AppResources
//					.optionString("hibernate_conf_properties_url"));
//			hib_conf.load(fis);
//			Enumeration e = hib_conf.propertyNames();
//			while(e.hasMoreElements()){
//				String key = (String)e.nextElement();
//				s += "\n" + key + " \t " + hib_conf.getProperty(key);
//			}
//		
//		} catch (FileNotFoundException e){
//			e.printStackTrace();
//			
//		} catch (IOException e){
//			
//		}
//		return s;
//	}


	private HibernatePropertiesTableModel getHibProperties( Properties props ){
		HibernatePropertiesTableModel hptm = new HibernatePropertiesTableModel();
		
		Enumeration e = props.propertyNames();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			Property p = new Property( key, props.getProperty(key));
			hptm.addProperty(p);
		}
		
		
		return hptm;
	}
	
	//#### DEFINE VARS ####
	
	//Properties _props;
	Properties _hibRevertProperties;
	boolean _firstHibPropLoad = true;
	Properties _currentHibProps;
	private ConfigurationPanel _callingPanel;
	
	String _currFile;
	String _currFolder;
	
} // end class
