package app;

import gui.ConfigurationPanel;
import gui.HibernatePropertiesTableModel;

import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

public class ConfigurationController {

	public ConfigurationController(String hibernatePropertiesFilePath) {
		_hibPropFilePath = hibernatePropertiesFilePath;
	}

	
	public Properties loadHibProperties(){
		Properties props = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream(_hibPropFilePath);
			props.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return props;
	}
	

	public String loadHib_Conf() {
		Properties hib_conf = new Properties();
		String s = "";
		
		try{
			
			//FileInputStream fis = new FileInputStream(_hibPropFilePath);
			FileInputStream fis = new FileInputStream("src/resources/hib_conf.properties");
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


	public HibernatePropertiesTableModel getHibProperties(){
		HibernatePropertiesTableModel hptm = new HibernatePropertiesTableModel();
		
		Properties props = this.loadHibProperties();
		Enumeration e = props.propertyNames();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			Property p = new Property( key, props.getProperty(key));
			hptm.addProperty(p);
		}
		
		
		return hptm;
	}
	
	//Properties _props;
	ConfigurationPanel _configPanel;
	String _hibPropFilePath;
	
} // end class
