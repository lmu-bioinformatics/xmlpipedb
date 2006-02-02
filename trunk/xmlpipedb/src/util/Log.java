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

package util;

public class Log {

	private static StringBuffer log = new StringBuffer();
	private static int errorCount = 0;
	private static int warningCount = 0;
	
	public static void write(String line) {
		log.append(line).append(System.getProperty("line.separator"));
	}
	
	public static void warn(String line) {
		log.append(line).append(System.getProperty("line.separator"));
	}
	
	public static void error(String line) {
		log.append(line).append(System.getProperty("line.separator"));
	}
	
}
