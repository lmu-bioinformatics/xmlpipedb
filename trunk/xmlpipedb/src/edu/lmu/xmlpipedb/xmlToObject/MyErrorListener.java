package edu.lmu.xmlpipedb.xmlToObject;

import org.xml.sax.SAXParseException;

import com.sun.tools.xjc.api.ErrorListener;

public class MyErrorListener implements ErrorListener {

	public void error(SAXParseException arg0) {
		System.out.println(arg0);
		
	}

	public void fatalError(SAXParseException arg0) {
		System.out.println("1" + arg0);
		
	}

	public void warning(SAXParseException arg0) {
		System.out.println("2" + arg0);
		
	}

	public void info(SAXParseException arg0) {
		System.out.println("3" +arg0);
		
	}

}
