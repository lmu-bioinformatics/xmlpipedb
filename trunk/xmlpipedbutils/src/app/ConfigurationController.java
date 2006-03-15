package app;

import gui.ConfigurationPanel;
import gui.HibernatePropertiesTableModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.table.TableModel;

import resources.AppResources;

public class ConfigurationController {

	public ConfigurationController() {
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return props;
	}
	
	
	public void storeHibProperties( TableModel mod){
		Properties props = new Properties();
		
		for( int i = 0; i < mod.getRowCount(); i++){
			props.setProperty((String)mod.getValueAt(i, 0), (String)mod.getValueAt(i, 1));
		}
		
		try{
			FileOutputStream fis = new FileOutputStream(AppResources.optionString("hibernate_properties_url"));
			props.store(fis, null);
			
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}// end storeHibProperties
	
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
	
	
	public String loadHib_Conf() {
		Properties hib_conf = new Properties();
		String s = "";
		
		try{
			
			//FileInputStream fis = new FileInputStream(_hibPropFilePath);
			FileInputStream fis = new FileInputStream(AppResources
					.optionString("hibernate_conf_properties_url"));
			hib_conf.load(fis);
			Enumeration e = hib_conf.propertyNames();
			while(e.hasMoreElements()){
				String key = (String)e.nextElement();
				s += "\n" + key + " \t " + hib_conf.getProperty(key);
			}
		
		} catch (FileNotFoundException e){
			e.printStackTrace();
			
		} catch (IOException e){
			
		}
		return s;
	}


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
	
} // end class
