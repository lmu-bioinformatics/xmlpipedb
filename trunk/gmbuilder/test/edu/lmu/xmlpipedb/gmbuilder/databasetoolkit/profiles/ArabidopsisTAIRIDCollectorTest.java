/*
  ============================================
  openPACS  - An Open Source PACS Framework
  ============================================

  Copyright (C) 2007 UCLA Medical Imaging Informatics.

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

  Email: opensource@mii.ucla.edu

  Mail: UCLA Medical Imaging Informatics
  924 Westwood Bl., Suite 420
  Westwood, CA 90024

  Web: http://www.mii.ucla.edu/openPACS/
*/
// Created by dondi, Jul 13, 2007.
package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Standalone unit test for just the TAIR ID collection routines.
 * 
 * @author   dondi
 */
public class ArabidopsisTAIRIDCollectorTest {

    private ArabidopsisTAIRIDCollector _collector;
    private Connection _c;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        Class.forName("org.postgresql.Driver");
        _c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/arabidopsis07", "xmlpipedb", null);
        _collector = new ArabidopsisTAIRIDCollector();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        _c.close();
        _collector = null;
    }

    /**
     * Test method for {@link edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.ArabidopsisTAIRIDCollector#collectTAIRIDs(java.sql.Connection)}.
     */
    @Test
    public void testCollectTAIRIDs() {
        _collector.collectTAIRIDs(_c, false);
    }

}
