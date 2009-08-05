/*
 * Created on May 21, 2005
 *
 */
package edu.lmu.xmlpipedb.gmbuilder.resource.properties;


import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author J. Nicholas
 *
 * Class providing static methods to access resources in files.
 * Resources can be text labels or options.
 * 
 * Resource files must be updated / customized for each project.
 */
public class AppResources {
	// Below is some generic plumbing, which allows the application
	// to use configurable options and messages
	
	/**
	 * @param key
	 * @return
	 * 
	 * Returns the string associated with the given key in the
     * resource bundle, lazily creating the bundle if necessary.
	 */
	public static String messageString(String key) {
        String message = "";
    	if (_messageBundle == null) {
            _messageBundle = ResourceBundle.getBundle(_messageBundlePrefix);
        }
        try{
        	message = _messageBundle.getString(key);
        } catch( MissingResourceException e){
        	_Log.error("Oops. The key, " + key + " does not exist.");
        	return message;
        }
        return message;
    }
	
    /**
     * @param key
     * @return
     * 
     * Returns the string associated with the given key in the
     * resource bundle, lazily creating the bundle if necessary.
     */
    public static String optionString(String key) {
        String option = "";
    	if (_optionsBundle == null) {
        	_optionsBundle = ResourceBundle.getBundle(_optionsBundlePrefix);
        }
        try{
        	option = _optionsBundle.getString(key);
        } catch( MissingResourceException e ){
        	_Log.error("Oops. The key, " + key + " does not exist.");
        	return option;
        }
        return option;
    }

    // ### DEFINE VARS ###
    // options
    private static ResourceBundle _optionsBundle;
    private static String _optionsBundlePrefix = "edu.lmu.xmlpipedb.gmbuilder.resource.properties.gmbuilder";

    
    // label texts
   private static ResourceBundle _messageBundle;
   private static String _messageBundlePrefix = 
   	optionString("opt_langfilepackage") + "." +		
   	optionString("opt_language") + 
	"_messages";
   
   /**
    * The resource class log.
    */
   private static final Log _Log = LogFactory.getLog(AppResources.class);
}
