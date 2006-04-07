package edu.lmu.xmlpipedb.util.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.table.TableModel;

import edu.lmu.xmlpipedb.util.gui.ConfigurationPanel;
import edu.lmu.xmlpipedb.util.gui.HibernatePropertiesTableModel;
import edu.lmu.xmlpipedb.util.resources.AppResources;



/**
 * @author bob
 * 
 */
public class ConfigurationController {

	/**
	 * Creates an instance of the ConfigurationController and loads the current
	 * properties file at the file URL passed.
	 * @param currentPropsUrl
	 */
	public ConfigurationController(String currentPropsUrl) {
		// getHibernateConfigPanel(null);
		_currentHibProps = loadHibProperties(currentPropsUrl);
	}


	/**
	 * Loads the properties file and stores a copy in _firstHibPropLoad
	 * for later reversion, if necessary 
	 * 
	 * @param propertiesPath
	 * @return
	 */
	private Properties loadHibProperties(String propertiesPath) {
		Properties props = new Properties();

		try {
			FileInputStream fis = new FileInputStream(propertiesPath);
			props.load(fis);
			if (_firstHibPropLoad) {
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

	private void loadModelProperties(String url, String category, String type,
			HibernatePropertiesModel model) {
		Properties props = new Properties();
		boolean isSavedDialect = false;

		try {
			// open this properties file and read it in
			FileInputStream fis = new FileInputStream(url);
			props.load(fis);

			String savedDialect = _currentHibProps.getProperty("hibernate.dialect");
			String dialect = props.getProperty("hibernate.dialect");
			if( savedDialect != null && dialect != null && dialect.equals(savedDialect) )
				isSavedDialect = true;
			
			// exception for general properties
			if( category.equals("general") ) isSavedDialect = true;
			
			// walk through the list of properties
			Enumeration propsEnum = props.keys();
			while (propsEnum.hasMoreElements()) {
				String name = (String) propsEnum.nextElement();
				String value = (String) props.getProperty(name);
				
				HibernateProperty hp;
				
				if( isSavedDialect ){
					String altVal = _currentHibProps.getProperty(name);
					boolean isSaved = true;
					
					if( altVal == null ){
						altVal = value;
						isSaved = false;
					}
					
					hp = new HibernateProperty(category, type,
						name, altVal, isSaved);
				} else {
					hp = new HibernateProperty(category, type,
						name, value, false);
				}
				
				model.add(hp);
			}
		} catch (FileNotFoundException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Iterates through all folders and files under the URL supplied and creates
	 * a HibernatePropertiesModel.
	 * 
	 * @param folderUrl
	 * @return
	 */
	public HibernatePropertiesModel getConfigurationModel(String folderUrl) {
		HibernatePropertiesModel model = new HibernatePropertiesModel();

		File folders = new File(folderUrl);
		File[] folderList = folders.listFiles();
		// System.out.printf("can read %s, absolute path %s\n",
		// folders.canRead(), folders.getAbsolutePath() );

		for (int i = 0; i < folderList.length; i++) {
			if (folderList[i].isDirectory()) {
				String category = folderList[i].getName();
				
				// get an array of files in the folder
				File[] files = folderList[i]
						.listFiles(new PropertiesFileNameFilter());

				// iterate through the array of files, adding them to model
				for (int j = 0; j < files.length; j++) {
					String type = files[j].getName();
					loadModelProperties(files[j].getAbsolutePath(), category,
							type, model);

				}// end for
			}
		}
		return model;
	}

	/**
	 * @param propertiesPath
	 * @return
	 */
	private Properties loadProperties(String propertiesPath) {
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


	/**
	 * Stores the properties object passed to the properties file
	 * @param props
	 */
	private void storeHibProperties(Properties props) {
		// Properties props = new Properties();

		// for( int i = 0; i < mod.getRowCount(); i++){
		// props.setProperty((String)mod.getValueAt(i, 0),
		// (String)mod.getValueAt(i, 1));
		// }

		try {
			FileOutputStream fis = new FileOutputStream(AppResources
					.optionString("hibernate_properties_url"));
			props.store(fis, null);
			_currentHibProps = props;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// end storeHibProperties

	/**
	 * Merges the properties from the model passed with those in the current properties
	 * @param saveModel
	 */
	public void saveProperties(HibernatePropertiesModel saveModel) {
		//Properties props = _currentHibProps;
		Properties props = new Properties();
		Iterator namesIter = saveModel.getProperties();

		while( namesIter.hasNext() ){
			String propName = (String)namesIter.next();
			HibernateProperty hp = saveModel.getProperty(propName);
			if( hp.isSaved() )
				props.setProperty(hp.getName(), hp.getValue());
		}
		storeHibProperties(props);

	}

	/**
	 * @deprecated
	 * @return
	 */
	public TableModel loadOriginalHibProps() {
		return getHibProperties(loadHibProperties(AppResources
				.optionString("original_hibernate_properties_url")));
	}

	/**
	 * @deprecated
	 * @return
	 */
	public TableModel loadCurrentHibProps() {
		return getHibProperties(loadHibProperties(AppResources
				.optionString("hibernate_properties_url")));
	}

	/**
	 * @deprecated
	 * @return
	 */
	public TableModel loadRevertedHibProps() {
		return getHibProperties(_hibRevertProperties);
	}

	// public String loadHib_Conf() {
	// Properties hib_conf = new Properties();
	// String s = "";
	//		
	// try{
	//			
	// //FileInputStream fis = new FileInputStream(_hibPropFilePath);
	// FileInputStream fis = new FileInputStream(AppResources
	// .optionString("hibernate_conf_properties_url"));
	// hib_conf.load(fis);
	// Enumeration e = hib_conf.propertyNames();
	// while(e.hasMoreElements()){
	// String key = (String)e.nextElement();
	// s += "\n" + key + " \t " + hib_conf.getProperty(key);
	// }
	//		
	// } catch (FileNotFoundException e){
	// e.printStackTrace();
	//			
	// } catch (IOException e){
	//			
	// }
	// return s;
	// }

	/**
	 * @deprecated
	 * @param props
	 * @return
	 */
	private HibernatePropertiesTableModel getHibProperties(Properties props) {
		HibernatePropertiesTableModel hptm = new HibernatePropertiesTableModel();

		Enumeration e = props.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			Property p = new Property(key, props.getProperty(key));
			hptm.addProperty(p);
		}

		return hptm;
	}

	// #### DEFINE VARS ####

	// Properties _props;
	Properties _hibRevertProperties;

	boolean _firstHibPropLoad = true;

	Properties _currentHibProps;

	private ConfigurationPanel _callingPanel;

	String _currFile;

	String _currFolder;

} // end class
