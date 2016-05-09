package com.qait.tests;
import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.Reader;

 

import javax.script.Invocable;

import javax.script.ScriptEngine;

import javax.script.ScriptEngineManager;

import javax.script.ScriptException;
public class javascriptTest {
	public static void main(String[] args) throws ScriptException,FileNotFoundException, NoSuchMethodException {
		ScriptEngineManager engineManager = new ScriptEngineManager();
		ScriptEngine engine = engineManager.getEngineByName("ECMAScript");
		 File file = new File("./src/test/resources/testdata/Eval.js");
		 Reader reader = new FileReader(file);
		 engine.eval(reader);
		 System.out.println("Java Program Output");
		 Invocable invocable = (Invocable) engine;
		 //invocable.invokeFunction("firstFn", "./src/test/resources/testdata/Eval.js");
	}
	}

