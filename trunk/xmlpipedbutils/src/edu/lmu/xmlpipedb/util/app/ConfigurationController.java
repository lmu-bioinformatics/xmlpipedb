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

/**
 * @author bob
 * 
 */
public class ConfigurationController {

	/**
	 * @param currentPropsUrl
	 */
	public ConfigurationController(String currentPropsUrl) {
		// getHibernateConfigPanel(null);
		_currentHibProps = loadHibProperties(currentPropsUrl);
	}

	/**
	 * @param currentPropsUrl
	 * @param cp
	 */
	public ConfigurationController(String currentPropsUrl, ConfigurationPanel cp) {
		// getHibernateConfigPanel(null);
		_currentHibProps = loadHibProperties(currentPropsUrl);
		_callingPanel = cp;
	}

	/**
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

		try {
			FileInputStream fis = new FileInputStream(url);
			props.load(fis);

			Enumeration e = props.keys();
			while (e.hasMoreElements()) {
				String name = (String) e.nextElement();
				String value = (String) props.getProperty(name);
				HibernateProperty hp = new HibernateProperty(category, type,
						name, value);
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
				// System.out.printf("folderName = %s, absolute path %s\n",
				// folders.getName(), folders.getParent() );
				File[] files = folderList[i]
						.listFiles(new PropertiesFileNameFilter());

				// ArrayList fileList = new ArrayList();

				for (int j = 0; j < files.length; j++) {
					String type = files[j].getName();
					loadModelProperties(files[j].getAbsolutePath(), category,
							type, model);

					//System.out.printf("filename = %s, in folder %s\n", files[j]
					//		.getName(), files[j].getParent());
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
	 * @param props
	 */
	public void storeHibProperties(Properties props) {
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
	 * @param config
	 */
	public void saveProperties(JPanel config) {
		Properties props = _currentHibProps;
		Component[] box = config.getComponents();
		Box bx = (Box) box[0];

		// Component[] comps = box[0].();
		for (int i = 0; i < bx.getComponentCount(); i++) {
			Box b = (Box) bx.getComponent(i);
			boolean cb = ((JCheckBox) b.getComponent(0)).isSelected();
			String label = ((JLabel) b.getComponent(1)).getText();
			if (cb) {
				String value = ((JTextField) b.getComponent(2)).getText();
				props.setProperty(label, value);
			} else {
				props.remove(label);
			}
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
