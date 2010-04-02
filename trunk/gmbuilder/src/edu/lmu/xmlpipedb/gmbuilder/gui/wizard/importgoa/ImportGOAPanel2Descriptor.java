/********************************************************
 * Filename: ImportGOAPanel2Descriptor.java
 * Author: Joey J. Barrett
 * Adaptation: Don Murphy
 * Program: gmBuilder
 * Description: A subclass of WizardPanelDescriptor which
 * outlines provides the necessary requirements for
 * creating a wizard panel.
 *
 * Revision History:
 * 20100331: Initial Revision; based on ExportPanel5Descriptor.java.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.gui.wizard.importgoa;

import java.awt.Cursor;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.xml.sax.SAXException;

import com.nexes.wizard.WizardPanelDescriptor;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ExportToGenMAPP; /* Will probably not be used in this class; to be removed upon investiagtion */
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.ImportGOA;
import edu.lmu.xmlpipedb.util.exceptions.InvalidParameterException;


/**
 * @author Joey J. Barrett
 * Class: ImportGOAPanel2Descriptor
 */
public class ImportGOAPanel2Descriptor extends WizardPanelDescriptor {

    public static final String IDENTIFIER = "EXPORT_PANEL5";

    ImportGOAPanel2 panel2;

    /**
     * Constructor.
     */
    public ImportGOAPanel2Descriptor() {

        panel2 = new ImportGOAPanel2();
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel2);
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getNextPanelDescriptor()
     */
    public Object getNextPanelDescriptor() {
        return FINISH;
    }

    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#getBackPanelDescriptor()
     */
    public Object getBackPanelDescriptor() {
        return null;
    }


    /* (non-Javadoc)
     * @see com.nexes.wizard.WizardPanelDescriptor#aboutToDisplayPanel()
     */
    public void aboutToDisplayPanel() {

        ImportGOAPanel2.setProgress(0, "Preparing for GOA import...");


        getWizard().setNextFinishButtonEnabled(false);
        getWizard().setBackButtonEnabled(false);

    }

    /**
     * @see com.nexes.wizard.WizardPanelDescriptor#displayingPanel()
     */
    public void displayingPanel() {

        Thread t = new Thread() {

            public void run() {
                try {
                    getWizard().getDialog().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    ImportGOA.GOAimport();
                    ImportGOAPanel2.setProgress(100, "Import completed successfully.");
                    getWizard().setNextFinishButtonEnabled(true);
                } catch(HibernateException e) {
                    _Log.fatal("Import GOA problem", e);
                } catch(ClassNotFoundException e) {
                    _Log.fatal("Import GOA problem", e);
                } catch(SQLException e) {
                    _Log.fatal("Import GOA problem", e);
                } catch(SAXException e) {
                    _Log.fatal("Import GOA problem", e);
                } catch(IOException e) {
                    _Log.fatal("Import GOA problem", e);
                } catch(JAXBException e) {
                    _Log.fatal("Import GOA problem", e);
                } catch (InvalidParameterException e) {
                	_Log.fatal("Import GOA problem", e);
				} finally {
                    getWizard().getDialog().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        };

        t.start();
    }

    private static final Log _Log = LogFactory.getLog(ImportGOAPanel2Descriptor.class);
}
