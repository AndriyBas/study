package com.oyster.core.controller.utils;

import com.oyster.core.controller.annotation.COMMAND;
import com.oyster.core.controller.annotation.CONTEXT;
import com.oyster.core.controller.annotation.PARAMETER;

public class ControllerAnnotationUtils {
	
	
	public static String getCommandKey(Class c){
		COMMAND t = (COMMAND) c.getAnnotation(COMMAND.class);
		return (t!=null) ? t.key() : null;
	}
	
	public static PARAMETER[] getParameterList(Class c){
		CONTEXT t = (CONTEXT) c.getAnnotation(CONTEXT.class);
		if (t == null) return null;
		return t.list();
	}
	
	public static boolean paramIsOptional(String paramKey, Class command){
		
		PARAMETER[] pl = getParameterList(command);
		for(PARAMETER p : pl){
			if(p.key().equals(paramKey)) return p.optional(); 
		}
		
		return true;
	}
	
	public static void main(String[] params){
		
		@COMMAND (key = "createUserAccount")
		@CONTEXT( list = {
					@PARAMETER(key = "email"	, type=String.class),
					@PARAMETER(key = "password"	, type=String.class),
					@PARAMETER(key = "phone"	, type=String.class, optional = true),
					@PARAMETER(key = "city"		, type=String.class, optional = true),
					@PARAMETER(key = "dept"		, type=String.class, optional = true)
				}
		)
		class TestAnnCMD extends Command{}
		
		
		System.out.println("COMMAND");
		System.out.println(getCommandKey(TestAnnCMD.class)+" : "+TestAnnCMD.class);
		System.out.println("PARAMETER List");
		
		PARAMETER[] pl = getParameterList(TestAnnCMD.class);
		for(PARAMETER p :pl ){
			System.out.println(p.key()+" : "+p.type()+" optional="+p.optional());
		}
	}
	
	
}
