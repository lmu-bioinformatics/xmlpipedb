package app;

import gui.ConfigurationPanel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConfigurationController {

	public ConfigurationController(String hibernatePropertiesFilePath) {
		_hibPropFilePath = hibernatePropertiesFilePath;
	}

	
	public String loadHibProperties(){
		_props = new Properties();
		String s = "";
		
		try {
			FileInputStream fis = new FileInputStream(_hibPropFilePath);
			_props.load(fis);
			Enumeration e = _props.propertyNames();
			while(e.hasMoreElements()){
				String key = (String)e.nextElement();
				s += "\n" + key + " \t " + _props.getProperty(key);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return s;
	}
	
	public String loadHib_Conf() {
//		ResourceBundle rb = ResourceBundle.getBundle("hib_conf");
		Properties hib_conf = new Properties();
		String s = "";
		
		try{
			
			FileInputStream fis = new FileInputStream("lib/hibernate/hib_conf.properties");
			//FileInputStream fis = new FileInputStream("src/resources/hibernate.properties");
			hib_conf.load(fis);
			Enumeration e = hib_conf.propertyNames();
			while(e.hasMoreElements()){
				String key = (String)e.nextElement();
				if( key != null )
				s += "\n" + key + " \t " ;
				try{
					s += _props.getProperty(key);
				}catch( NullPointerException ex ){
					ex.printStackTrace();
				}
			}
		
		} catch (FileNotFoundException e){
			e.printStackTrace();
			
		} catch (IOException e){
			
		}
		return s;
	}



	Properties _props;
	ConfigurationPanel _configPanel;
	String _hibPropFilePath;
	
} // end class
