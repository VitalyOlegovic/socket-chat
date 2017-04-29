package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * 
 * @author Ezio Sperduto
 *
 */
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

	public static void stampa(Object o){
		System.out.println(o);
	}
	
	public static void stampa(byte o){
		System.out.println(o);
	}
	
	public static void stampa(short o){
		System.out.println(o);
	}
	
	public static void stampa(int o){
		System.out.println(o);
	}
	
	public static void stampa(long o){
		System.out.println(o);
	}
	
	public static void stampa(char o){
		System.out.println(o);
	}
	
	public static void stampa(boolean o){
		System.out.println(o);
	}
	
	public static void stampa(float o){
		System.out.println(o);
	}
	
	public static void stampa(double o){
		System.out.println(o);
	}

	public static void spedisciRiga(OutputStream os,String str){
		PrintWriter out = new PrintWriter(os, true);
		out.println(str);
	}
}
