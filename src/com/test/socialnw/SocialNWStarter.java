package com.test.socialnw;

import java.io.Console;

public class SocialNWStarter {
	
	private static String quit="";
	
	  public static final void main(String... aArgs){
		  while(!quit.equalsIgnoreCase("q")) {
		    Console console = System.console();
		    String message = console.readLine("");
		    quit=message.trim();
		    
		    String [] contents = message.split("\\s+");
		    if(contents.length==1) {
		    	System.out.println("reading");
		    }else if(contents[1].equals("->")) {
		    	System.out.println("posting");
		    } else if(contents[1].equals("follows")) {
		    	System.out.println("following");
		    } else if(contents[1].equals("wall")) {
		    	System.out.println("wall");
		    }
		    
		  }
	  }
		  

}
