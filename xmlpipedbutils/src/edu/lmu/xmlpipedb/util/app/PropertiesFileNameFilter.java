package edu.lmu.xmlpipedb.util.app;

import java.io.File;
import java.io.FilenameFilter;

public class PropertiesFileNameFilter implements FilenameFilter {

	public boolean accept(File arg0, String arg1) {
		if(arg1.lastIndexOf(".") > -1)
			if( arg1.substring(arg1.lastIndexOf(".")).equalsIgnoreCase(".properties"))
				return true;
		
		return false;
	}

}
