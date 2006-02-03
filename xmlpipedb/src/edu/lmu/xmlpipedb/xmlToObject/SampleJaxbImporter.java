/*
  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public
  License along with this library; if not, write to the Free Software
  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

  Web: http://sourceforge.net/projects/xmlpipedb
*/
// Created by joeyjbarrett, Feb 2, 2006.

package edu.lmu.xmlpipedb.xmlToObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import util.Log;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import com.sun.tools.xjc.outline.Outline;

public class SampleJaxbImporter {

	private static JAXBContext jaxbContext;
	private static Unmarshaller unmarshaller;
	
	

	
	
	//Default constructor.
	public SampleJaxbImporter() {}
	
	public static void setXSD(File xsdFile) {
		
		SchemaCompiler sc = XJC.createSchemaCompiler();
		sc.setDefaultPackageName("try");
		try {
			
			
			
			InputSource inputSource = new InputSource(new FileInputStream(xsdFile));
			inputSource.setSystemId(xsdFile.toString());
			inputSource.setEncoding("UTF-8");
			sc.parseSchema(inputSource);
			System.out.println("Success!");
			
			S2JJAXBModel jaxbModel = sc.bind();

			MyPlugins myPlugin = new MyPlugins();
			
			Options o1 = new Options();
			o1.targetDir = new File("/u/blue/jjbarret/Desktop/try");
			
			String[] s = new String[1];
			s[0] = "-d /u/blue/jjbarret/Desktop/try";
			
			myPlugin.addPlugin(s);
			
			s[0] = "-p generated";
			
			myPlugin.addPlugin(s);

			
			JCodeModel jCodeModel = jaxbModel.generateCode(null, new MyErrorListener());
			
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
//		//setup schema here
//		//attach it to the unmarshaller
//		
//		try {
//			jaxbContext = JAXBContext.newInstance(xsdFile.toString());
//			unmarshaller = jaxbContext.createUnmarshaller();
//			
//			SchemaFactory schemaFactory = 
//				SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
//			Schema schema = schemaFactory.newSchema(xsdFile);
//			
//			unmarshaller.setSchema(schema);
//			
//			unmarshaller.setEventHandler(new MyValidationEventHandler());
//			
//		} catch (JAXBException e) { Log.error(e.toString());
//		} catch (SAXException e) { Log.error(e.toString()); }
	}
	
	
	public static void loadFile(File xmlFile) {
	
        try {
			JAXBElement<?> poElement =
				(JAXBElement<?>)unmarshaller.unmarshal(new FileInputStream(xmlFile));
			
		} catch (FileNotFoundException e) { Log.error(e.toString());
		} catch (JAXBException e) { Log.error(e.toString()); }
             
	}

	
	
}
