/**
 * 
 */
package edu.lmu.xmlpipedb.util.utilities;

/**
 * This class provides a series of static methods that will serve as helpers utilizing the BeanUtils class.
 * @author Babak
 *
 */
public class PipeDBBeanUtils {
	
	public static boolean isPrimitive( Object o ){
		Class oClass = o.getClass();
		return(
			oClass.equals( int.class 	) ||
			oClass.equals( float.class 	) ||
			oClass.equals( double.class ) ||
			oClass.equals( boolean.class) ||
			oClass.equals( byte.class 	) ||
			oClass.equals( float.class 	) ||
			oClass.equals( float.class 	) ||
			oClass.equals( short.class 	) ||
			oClass.equals( long.class 	) ||
			oClass.equals( char.class 	)		
		);
	}
	
	public static boolean isObject( Object o ){
		return false;
	}
	
	public static boolean isCollection( Object o ){
		return false;
	}

}
