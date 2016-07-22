package com.qait.tests;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.*; 


@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)  
 @interface MySampleAnn {
	 
    String name();
    String desc();
}


public class Test123 {

	@MySampleAnn(desc = "desc123", name = "name123")
	public void sayHello(){
		
	}

	

	
	
	
	
}
