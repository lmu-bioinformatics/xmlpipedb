/********************************************************
 * Filename: DatabaseInspector.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: Sets up the database profiles and 
 * examines which are available given a connection
 * to the database.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.DatabaseProfile;
import edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles.UniProtDatabaseProfile;


/**
 * @author Joey J. Barrett
 * Class: DatabaseInspector
 * @author Jeffrey Nicholas
 */
public class DatabaseInspector {

	private static final DatabaseProfile[] databaseProfiles;
	private static List<DatabaseProfile> profilesInUse = new ArrayList<DatabaseProfile>();
	
	static {
		//Add all available database profiles.  To add a new
		//database, create a profile by extending <code>Profile<code>
		//and providing required definitions.	
		databaseProfiles = new DatabaseProfile[] {new UniProtDatabaseProfile()};
	}
	
	/**
	 * Using the given connection the database is inspected.  Any
	 * profile that matches the requirements it defines are made
	 * available in the returned DatabaseProfile[].
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public static DatabaseProfile[] init(Connection connection) throws SQLException {
		
		//Recognize all valid profiles for the given 
		//database connection.
		for(DatabaseProfile dbProfile : databaseProfiles) {	
			if(dbProfile.isAvailable(connection)) {
				dbProfile.checkRequirements(connection);
				profilesInUse.add(dbProfile);
			}		
		}
		
		return profilesInUse.toArray(new DatabaseProfile[0]);
	}
}
