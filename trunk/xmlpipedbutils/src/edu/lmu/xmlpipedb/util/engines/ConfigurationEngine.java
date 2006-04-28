package edu.lmu.xmlpipedb.util.engines;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

import org.hibernate.cfg.Configuration;

import sun.print.resources.serviceui;

import edu.lmu.xmlpipedb.util.exceptions.CouldNotLoadPropertiesException;
import edu.lmu.xmlpipedb.util.exceptions.NoHibernatePropertiesException;
import edu.lmu.xmlpipedb.util.resources.AppResources;

/**
 * @author bob
 * 
 */
public class ConfigurationEngine {

    /**
     * Creates an instance of the ConfigurationEngine and loads the current
     * properties file at the file URL passed.
     * 
     * @param hibernateFileUrl
     * @throws CouldNotLoadPropertiesException
     */
    public ConfigurationEngine() throws CouldNotLoadPropertiesException {
        // AppResources.optionString("hibernateProperties"),
        // AppResources.optionString("hibernate_properties_default_url")

        _hibernatePropertiesUrl = AppResources.optionString("hibernateProperties");
        _defaultPropertiesUrl = AppResources.optionString("hibernate_properties_default_url");
        try {
            loadHibProperties();
        } catch(CouldNotLoadPropertiesException e) {
            throw e;
        }
    }

    /**
     * Creates an instance of the ConfigurationEngine and loads the current
     * properties file at the file URL passed.
     * 
     * @param hibernateFileUrl
     * @throws CouldNotLoadPropertiesException
     */
    public ConfigurationEngine(String hibernateFileUrl, String defaultPropsUrl) throws CouldNotLoadPropertiesException {
        _hibernatePropertiesUrl = hibernateFileUrl;
        _defaultPropertiesUrl = defaultPropsUrl;
        try {
            loadHibProperties();
        } catch(CouldNotLoadPropertiesException e) {
            throw e;
        }
    }

    /**
     * Returns a hibernate Configuration object, if the properties object is not
     * empty. If it is empty, a NoHibernatePropertiesException will be thrown.
     * 
     * @return Configuration - an org.hibernate.cfg.Configuration ojbect is
     *         populated with the currently configured properties and returned.
     * @throws NoHibernatePropertiesException
     */
    public Configuration getHibernateConfiguration() throws NoHibernatePropertiesException {
        Configuration config = new Configuration();

        if (_currentHibProps.isEmpty()) {
            throw new NoHibernatePropertiesException(AppResources.messageString("exception.nohibprops"));
        }
        // get a copy of the currentHibProps
        Properties currProps = _currentHibProps;
        // remove "local" properties
        currProps.remove(SAVED_TYPE_NAME);
        currProps.remove(SAVED_CATEGORY_NAME);
        // create the to-be-delivered properties object
        Properties configProps = new Properties();
        
        Enumeration propsEnum = currProps.keys();
        
        while (propsEnum.hasMoreElements()) {
            String key = (String)propsEnum.nextElement();
            String value = currProps.getProperty(key);
            HibernateProperty hp = new HibernateProperty( key, value, false );
            configProps.setProperty(hp.getName(), value);
        }
        
        config.setProperties(configProps);

        return config;
    }

    /**
     * Loads the properties file and stores a copy in _firstHibPropLoad for
     * later reversion, if necessary
     * 
     * @param propertiesPath
     * @return
     */
    private void loadHibProperties() throws CouldNotLoadPropertiesException {
        Properties props = new Properties();

        try {
            File f = new File(_hibernatePropertiesUrl);
            if (!f.exists()) {
                if (!f.createNewFile())
                    throw new FileNotFoundException("File " + _hibernatePropertiesUrl + " did not exist. Attempt to create the file failed. Please check the settings for this file.");
            }
            FileInputStream fis = new FileInputStream(_hibernatePropertiesUrl);
            props.load(fis);
            if (_firstHibPropLoad) {
                _hibRevertProperties = new Properties();
                _hibRevertProperties = props;
                _firstHibPropLoad = false;
            }
        } catch(FileNotFoundException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
            throw new CouldNotLoadPropertiesException(e.toString());
        } catch(IOException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
            throw new CouldNotLoadPropertiesException(e.toString());
        }

        if (props != null)
            _currentHibProps = props;
        else
            throw new CouldNotLoadPropertiesException("Properties object was null. No hibernate properties loaded.");
    }

    /**
     * Iterates through all folders and files under the URL supplied and creates
     * a HibernatePropertiesModel.
     * 
     * @param folderUrl
     * @return
     */
    public HibernatePropertiesModel getConfigurationModel() throws FileNotFoundException {
        HibernatePropertiesModel model = new HibernatePropertiesModel();
        // used to determine if this is saved
        _defaultProperties = new Properties();

        try {
            InputStream iStream = getClass().getResourceAsStream(_defaultPropertiesUrl);
            if (iStream != null) { // iStream will be null if the file was not
                                    // found
                _defaultProperties.load(iStream);
            } else {
                File f = new File(_defaultPropertiesUrl);
                if (!f.exists()) {
                    throw new FileNotFoundException("The defaultHibernate.properties file was not found at " + _defaultPropertiesUrl);
                }
                FileInputStream fis = new FileInputStream(_defaultPropertiesUrl);
                _defaultProperties.load(fis);
            }
        } catch(IOException e) {
            System.out.print(e.getMessage());
            e.printStackTrace();
        }

        model.setCurrentCategory(_currentHibProps.getProperty(SAVED_CATEGORY_NAME));
        model.setCurrentType(_currentHibProps.getProperty(SAVED_TYPE_NAME));
        _currentHibProps.remove(SAVED_TYPE_NAME);
        _currentHibProps.remove(SAVED_CATEGORY_NAME);

        Enumeration propsEnum = _defaultProperties.keys();

        while (propsEnum.hasMoreElements()) {
            String key = (String)propsEnum.nextElement();
            String value = _defaultProperties.getProperty(key);
			
            HibernateProperty hp;
            // figure out what value to put in model
            // if the key matches a key in the properties file we read in
            // then use the value from that file and mark it as saved
            // otherwise, use the default value.
            if (_currentHibProps.containsKey(key)) {
                hp = new HibernateProperty(key, _currentHibProps.getProperty(key), true);
            } else {
                hp = new HibernateProperty(key, value, false);
            }

            model.add(hp);

        }
        return model;
    }

    /**
     * Stores the properties object passed to the properties file
     * 
     * @param props
     */
    private void storeHibProperties(Properties props) {
        try {
            FileOutputStream fis = new FileOutputStream(_hibernatePropertiesUrl);
            props.store(fis, null);
            _currentHibProps = props;
        } catch(FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }// end storeHibProperties

    /**
     * Merges the properties from the model passed with those in the current
     * properties
     * 
     * @param saveModel
     */
    public void saveProperties(HibernatePropertiesModel saveModel) {
        // Properties props = _currentHibProps;
        Properties props = new Properties();
        Iterator namesIter = saveModel.getProperties();
        String logging = "";

        while (namesIter.hasNext()) {
            String propName = (String)namesIter.next();
            logging += propName + "\n";
            HibernateProperty hp = saveModel.getProperty(propName);
            if (hp.isSaved())
                props.setProperty(hp.getStorageName(), hp.getValue());
        }
        System.out.print(logging);

        // add these two "static" properties to the Properties obj.
        props.setProperty(SAVED_TYPE_NAME, saveModel.getCurrentType());
        props.setProperty(SAVED_CATEGORY_NAME, saveModel.getCurrentCategory());

        storeHibProperties(props);
        _currentHibProps = props;
    }

    /**
     * 
     * @param category
     * @param type
     */
    public void saveSettings(String category, String type) {
        if (!category.equalsIgnoreCase("general")) {
            _currentHibProps.setProperty(SAVED_TYPE_NAME, type);
            _currentHibProps.setProperty(SAVED_CATEGORY_NAME, category);
            FileOutputStream fis;
            try {
                fis = new FileOutputStream(_defaultPropertiesUrl);
                _defaultProperties.store(fis, null);

            } catch(FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch(IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    // #### DEFINE VARS ####

    /**
     * Iterates through all folders and files under the URL supplied and creates
     * a HibernatePropertiesModel.
     * 
     * @param folderUrl
     * @return
     */
    /*	public HibernatePropertiesModel getConfigurationModel(String folderUrl) {
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
     }*/

    // Properties _props;
    Properties _hibRevertProperties;
    boolean _firstHibPropLoad = true;

    Properties _currentHibProps = null;
    Properties _defaultProperties;

    String _currFile;
    String _currFolder;

    private String _hibernatePropertiesUrl;
    private String _defaultPropertiesUrl;
    public static String SAVED_TYPE_NAME = "xmlpipedb.type";
    public static String SAVED_CATEGORY_NAME = "xmlpipedb.category";

} // end class
