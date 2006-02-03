package edu.lmu.xmlpipedb.xmlToObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.ErrorHandler;

import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.Outline;

public class MyPlugins extends Plugin {

	List<MyPlugins> pluginList = new ArrayList<MyPlugins>();
	
	public void addPlugin(String[] options) {
		MyPlugins mps = new MyPlugins();
		try {
			mps.parseArgument(new Options(), options, 0);
		} catch (BadCommandLineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pluginList.add(mps);
	}
	
	@Override
	public String getOptionName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean run(Outline arg0, Options arg1, ErrorHandler arg2) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Plugin[] getPlugins() {
		return pluginList.toArray(new MyPlugins[0]);
	}
	
}
