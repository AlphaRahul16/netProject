package com.qait.tests;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry; 

//
//@Retention(RetentionPolicy.RUNTIME) 
//@Target(ElementType.METHOD)  
// @interface MySampleAnn {
//	 
//    String name();
//    String desc();
//}


public class Test123{
	public static void main(String args[]){
		
			Map<String,List<String>> credentials = new HashMap<String,List<String>>();
            List<String> arList=new ArrayList<String>();
            List<String> arList1=new ArrayList<String>();

int i=0;
            arList.add("Abrams");
            arList.add("2346003");
            arList1.add("Aleman");
            arList1.add("2339139");
//            arList.add("Aleman");
//            arList.add("2339139");
    		credentials.put("user0",arList);
			credentials.put("user1",arList1);
//			for(Entry<String, String> e:credentials.entrySet()){
//				System.out.println(e.getKey()+e.getValue());
//			}
//			System.out.println(credentials.get("user"+i).get(i));
//			System.out.println(credentials.get("user"+i).get(i+1));
//			i++;
//			System.out.println(credentials.get("user"+i).get(i+1));
//			System.out.println(credentials.get("user"+i).get(i+2));

			System.out.println(credentials.get("user"+i).get(0));
			System.out.println(credentials.get("user"+i).get(1));
i++;
System.out.println(credentials.get("user"+i).get(0));
System.out.println(credentials.get("user"+i).get(1));
	}	
}
