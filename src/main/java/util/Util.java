package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public static final int PORTA_SERVER = 4999;
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	public static String adesso(){
		return "["+sdf.format(new Date())+"]";
	}
	
	public static String pref(String chi){
		for(int i=chi.length();i<=10;i++)
			chi+="-";
		
		return "["+chi+"]"+adesso();
	}

	public static void stampa(String s){
		System.out.println(s);
	}

}
