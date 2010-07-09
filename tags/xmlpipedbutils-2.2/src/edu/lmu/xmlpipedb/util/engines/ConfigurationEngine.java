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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Configuration;

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
     * properties file. A ConfigurationEngine created using this method stores
     * and loads hibernate properties in a pre-defined location, which can be
     * found in the options.properties file. The internal datastore, used to
     * load the gui config is also read from a location defined in the
     * options.properties.
     * 
     * @throws CouldNotLoadPropertiesException
     */
    public ConfigurationEngine() throws CouldNotLoadPropertiesException {
        // init does the work
        init(AppResources.optionString("hibernateProperties"), AppResources.optionString("hibernate_properties_default_url"));
    }

    /**
     * Creates an instance of the ConfigurationEngine and loads the current
     * properties file at the file URL passed.
     * 
     * @param hibernateFileUrl
     *            - URL of the custom hiberante.properties file where property
     *            settings will be stored and loaded.
     * @param defaultPropsUrl
     *            - URL of the default properties and data structure
     * @throws CouldNotLoadPropertiesException
     */
    public ConfigurationEngine(String hibernateFileUrl, String defaultPropsUrl) throws CouldNotLoadPropertiesException {
        // init does the work
        init(hibernateFileUrl, defaultPropsUrl);
    }

    /**
     * Stores the passed params and loads the currently stored properties.
     * 
     * @param hibernateFileUrl
     * @param defaultPropsUrl
     * @throws CouldNotLoadPropertiesException
     */
    private void init(String hibernateFileUrl, String defaultPropsUrl) throws CouldNotLoadPropertiesException {
        // store the URLs in class scope vars
        _hibernatePropertiesUrl = hibernateFileUrl;
        _defaultPropertiesUrl = defaultPropsUrl;
        try {
            // load the custom hibernate properties
            loadHibProperties();
        } catch(CouldNotLoadPropertiesException e) {
            // if the properties could not be loaded, throw an exception
            throw e;
        }
    }

    /**
     * Returns a hibernate Configuration object with the currently stored
     * hibernate properties in it, if the properties object is not empty. If it
     * is empty, a NoHibernatePropertiesException will be thrown. It does NOT
     * load anything else, for example, no hibernate mapping file(s) or jar(s)
     * are loaded. These must be loaded by the caller.
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
        // remove custom properties (non-hibernate)
        currProps.remove(SAVED_TYPE_NAME);
        currProps.remove(SAVED_CATEGORY_NAME);
        // create the to-be-delivered properties object
        Properties configProps = new Properties();

        Enumeration<Object> propsEnum = currProps.keys();

        while (propsEnum.hasMoreElements()) {
            // get the key and value
            // the key will have extra info that is stored to be able to
            // identify the category and type of the property
            String key = (String)propsEnum.nextElement();
            String value = currProps.getProperty(key);
            // create a HibernateProperty from the key and value,
            // because it will parse the category, type and name
            // automatically.
            HibernateProperty hp = new HibernateProperty(key, value, false);
            // hp.getName() is only the hibernate name, with no extra info
            configProps.setProperty(hp.getName(), value);
        }

        // call the setProperties method and pass in the properties object
        // this loads the hibernate properties into the Config object
        // Other things, like loading the mapping jar file(s) must be done
        // by the caller.
        config.setProperties(configProps);

        return config;
    }

    /**
     * Loads the properties file and stores a copy in _hibRevertProperties for
     * possible reversion later. If the URL cannot be found, an attempt is made
     * to create a file in that location. If this fails, an exception is thrown.
     * 
     * @return void
     * @throws CouldNotLoadPropertiesException
     */
    private void loadHibProperties() throws CouldNotLoadPropertiesException {
        Properties props = new Properties();

        try {
            // create a file object with the URL
            File f = new File(_hibernatePropertiesUrl);
            // if the file at that location does not exist, try to create it
            // if creating it fails, throw an error
            if (f.exists()) {
                // at this point, we know the URL is good, so create a file stream
                FileInputStream fis = new FileInputStream(_hibernatePropertiesUrl);
                // load the properties from the file
                props.load(fis);
            }
            // if it is the first time loading, store a copy for reverting to
            // later.
            if (_firstHibPropLoad) {
                _hibRevertProperties = new Properties();
                _hibRevertProperties = props;
                _firstHibPropLoad = false;
            }
        } catch(FileNotFoundException e) {
            _Log.error(e);
            throw new CouldNotLoadPropertiesException(e.toString());
        } catch(IOException e) {
            _Log.error(e);
            throw new CouldNotLoadPropertiesException(e.toString());
        }

        // if we have properties, save them in a class var
        _currentHibProps = props;
    }

    /**
     * Iterates through all properties the URL supplied and creates a
     * HibernatePropertiesModel. Saved property values are stored in the model
     * in place of default values.
     * 
     * @return HibernatePropertiesModel
     * @throws FileNotFoundException
     */
    public HibernatePropertiesModel getConfigurationModel() throws FileNotFoundException {
        HibernatePropertiesModel model = new HibernatePropertiesModel();
        // used to determine if this is saved
        _defaultProperties = new Properties();

        try {
            // try to find the file in the jar file first
            InputStream iStream = getClass().getResourceAsStream(_defaultPropertiesUrl);
            if (iStream != null) {
                // iStream will be null if the file was not found
                _defaultProperties.load(iStream);
            } else {
                // since iStream WAS null, we'll try to find the properties
                // as a file in the file system
                File f = new File(_defaultPropertiesUrl);
                if (!f.exists()) {
                    throw new FileNotFoundException(AppResources.messageString("exception.filenotfound.default") + _defaultPropertiesUrl);
                }
                FileInputStream fis = new FileInputStream(_defaultPropertiesUrl);
                _defaultProperties.load(fis);
            }
            // at this point, one way or another, _defaultProperties has
            // properties loaded into it.
        } catch(IOException e) {
            _Log.error(e);
        }

        // extract the category and type that were saved
        // these can be used to set the view in the GUI to where the
        // user left off.
        model.setCurrentCategory(_currentHibProps.getProperty(SAVED_CATEGORY_NAME));
        model.setCurrentType(_currentHibProps.getProperty(SAVED_TYPE_NAME));
        // these props don't follow the hibernate model and must be removed or
        // they will cause trouble!!!
        _currentHibProps.remove(SAVED_TYPE_NAME);
        _currentHibProps.remove(SAVED_CATEGORY_NAME);

        Enumeration<Object> propsEnum = _defaultProperties.keys();

        while (propsEnum.hasMoreElements()) {
            String key = (String)propsEnum.nextElement();
            String value = _defaultProperties.getProperty(key);

            // figure out what value to put in model
            // if the key matches a key in the properties file we read in
            // then use the value from that file and mark it as saved
            // otherwise, use the default value.
            HibernateProperty hp = (_currentHibProps.containsKey(key)) ?
                new HibernateProperty(key, _currentHibProps.getProperty(key), true) :
                new HibernateProperty(key, value, false);

            // add the HP object to the model
            model.add(hp);
        }
        // Done!
        // now we have a model with all the default properties and any saved
        // values.
        return model;
    }

    /**
     * Stores the properties object passed to the properties file
     * 
     * @param props
     */
    private void storeHibProperties(Properties props) {
        try {
            // create the output stream
            FileOutputStream fis = new FileOutputStream(_hibernatePropertiesUrl);
            // store the props in the file
            props.store(fis, null);
            // set the current props to be the ones just stored (they are now
            // current)
            _currentHibProps = props;
        } catch(FileNotFoundException e) {
            _Log.error(e);
        } catch(IOException e) {
            _Log.error(e);
        }
    }

    /**
     * Merges the properties from the model passed with those in the current
     * properties
     * 
     * @param saveModel
     */
    public void saveProperties(HibernatePropertiesModel saveModel) {
        // Properties props = _currentHibProps;
        Properties props = new Properties();
        Iterator<String> namesIter = saveModel.getProperties();
        while (namesIter.hasNext()) {
            // get the property name
            String propName = (String)namesIter.next();
            // logging += propName + "\n";
            // get the HibernateProperties object out of the model,
            // based on the property name
            HibernateProperty hp = saveModel.getProperty(propName);
            // check if the property is supposed to be saved
            // this should have been done by the caller, but just in case
            // we check it here
            if (hp.isSaved()) {
                // store the property by "storage name" (category|type|name)
                // and value
                props.setProperty(hp.getStorageName(), hp.getValue());
            }
        }

        // add these two "static" properties to the Properties obj.
        props.setProperty(SAVED_TYPE_NAME, saveModel.getCurrentType());
        props.setProperty(SAVED_CATEGORY_NAME, saveModel.getCurrentCategory());

        // store the properties
        storeHibProperties(props);
    }

    /**
     * The ConfigurationEngine log.
     */
    private static final Log _Log = LogFactory.getLog(ConfigurationEngine.class);
    
    boolean _firstHibPropLoad = true;
    Properties _hibRevertProperties;

    Properties _currentHibProps = null;
    Properties _defaultProperties;

    String _currFile;
    String _currFolder;

    private String _hibernatePropertiesUrl;
    private String _defaultPropertiesUrl;
    private static String SAVED_TYPE_NAME = "xmlpipedb.type";
    private static String SAVED_CATEGORY_NAME = "xmlpipedb.category";

}
