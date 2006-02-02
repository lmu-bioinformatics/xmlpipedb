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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import util.Log;

public class SampleJaxbImporter {

	private static JAXBContext jaxbContext;
	private static Unmarshaller unmarshaller;
	
	//Default constructor.
	public SampleJaxbImporter() {}
	
	public static void setXSD(File xsdFile) {
		//setup schema here
		//attach it to the unmarshaller
		
		try {
			jaxbContext = JAXBContext.newInstance(xsdFile.toString());
			unmarshaller = jaxbContext.createUnmarshaller();
			
			SchemaFactory schemaFactory = 
				SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(xsdFile);
			
			unmarshaller.setSchema(schema);
			
			//unmarshaller.setEventHandler(new MyValidationEventHandler());
			
		} catch (JAXBException e) { Log.error(e.toString());
		} catch (SAXException e) { Log.error(e.toString()); }
	}
	
	
	public static void loadFile(File xmlFile) {
	
        try {
			JAXBElement<?> poElement =
				(JAXBElement<?>)unmarshaller.unmarshal(new FileInputStream(xmlFile));
			
		} catch (FileNotFoundException e) { Log.error(e.toString());
		} catch (JAXBException e) { Log.error(e.toString()); }
             
	}

	
	
}
