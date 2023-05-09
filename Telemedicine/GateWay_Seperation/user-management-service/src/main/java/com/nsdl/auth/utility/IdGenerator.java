package com.nsdl.auth.utility;

/**
 * @author sudip banerjee
 *
 */

public class IdGenerator {
	
		
	/**
	 * @return roleId.
	 */
	
	public static int createRoleId(){
	    return (int)(Math.random()*10000);
	    
	}
	
	
	/**
	 * @return functionId.
	 */
	
	public static int createFunctionId(){
	    return (int)(Math.random()*10000);
	}
	
}
