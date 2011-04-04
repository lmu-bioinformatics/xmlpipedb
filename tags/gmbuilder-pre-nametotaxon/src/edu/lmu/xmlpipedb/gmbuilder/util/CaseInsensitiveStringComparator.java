// Created by xmlpipedb, Jul 3, 2006.
package edu.lmu.xmlpipedb.gmbuilder.util;

import java.util.Comparator;

/**
 * Simple utility class for comparing strings disregarding case.
 * 
 * @author xmlpipedb
 * @version $Revision$ $Date$
 */
public class CaseInsensitiveStringComparator implements Comparator<String> {
    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(String s1, String s2) {
        return s1.toUpperCase().compareTo(s2.toUpperCase());
    }
}
