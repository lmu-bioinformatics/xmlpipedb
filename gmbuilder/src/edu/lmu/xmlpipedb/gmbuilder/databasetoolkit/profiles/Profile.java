/********************************************************
 * Filename: Profile.java
 * Author: Joey J. Barrett
 * Program: gmBuilder
 * Description: An instance of a subclass of Profile
 * is an object with a name and a description.
 *     
 * Revision History:
 * 20060620: Initial Revision.
 * *****************************************************/

package edu.lmu.xmlpipedb.gmbuilder.databasetoolkit.profiles;

/**
 * @author Joey J. Barrett
 * Class: Profile
 */
public abstract class Profile {

	private final String name;
	private final String description;

	
	/**
	 * This constructor sets the name and description
	 * of a profile.
	 * @param name
	 * @param description
	 */
	public Profile(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Gets the name of the profile.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the description of the profile.
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name;
	}
}
