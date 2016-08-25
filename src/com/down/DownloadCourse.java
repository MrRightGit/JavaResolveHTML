package com.down;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

import com.main.Main;



public class DownloadCourse {

	/**
	 * 通过课件的Set获取课件的下载地址和课件名
	 * @param set
	 */
	public static void getCourseURLAndName(Set<String> set,String title){
//		System.out.println("getCourseUrlAndName");
		for (String string : set) {
			//在传给Set的string时，以！为界将网址和filename分开了
			String[] path=string.split("!");
			String url=path[0];
			String fileName=path[1];
//			System.out.println(url+"  "+fileName);
			
			downloadByURLAndName2(url, fileName,title);

		}
	}
	/**
	 * 用的是字符流操作，竟然打不开
	 * @param url
	 * @param fileName
	 * @param title
	 */
	public static void downloadByURLAndName(String url,String fileName,String title){
		System.out.println("downloadByURLAndName");
		try {
			URL httpUrl=new URL(url);
			HttpURLConnection con=(HttpURLConnection)httpUrl.openConnection();
			
			InputStreamReader ins=new InputStreamReader(con.getInputStream(),"utf-8");
			BufferedReader br=new BufferedReader(ins);
			
			new File("F:\\imooc/Android/"+title+"/").mkdir();
			File file=new File("F:\\imooc/Android/"+title+"/"+fileName);
			FileWriter fileWriter=new FileWriter(file);
			
			String str="";
			while((str=br.readLine())!=null){
				fileWriter.write(str);
				fileWriter.flush();
			}
			fileWriter.close();
			br.close();
			ins.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 用字节流处理,竟然可以
	 * @param url
	 * @param fileName
	 * @param title
	 */
	public static void downloadByURLAndName2(String url,String fileName,String title){
	//	System.out.println("downloadByURLAndName");
		
		long startTime;
		long bytes;
		startTime=System.currentTimeMillis();
		bytes=0;
		try {
			URL httpUrl=new URL(url);
			HttpURLConnection con=(HttpURLConnection)httpUrl.openConnection();
			
			InputStream in=con.getInputStream();
			
			new File("F:\\imooc/Android/"+title+"/").mkdir();
			File file=new File("F:\\imooc/Android/"+title+"/"+fileName);
			FileOutputStream fos=new FileOutputStream(file);
			
			byte[] b=new byte[1024*1024];
			int len;
			while((len=in.read(b))!=-1){
				bytes+=len;
				fos.write(b, 0, len);
			}
			fos.close();
			in.close();
			System.out.println(fileName+"下载完成");
			long endTime=System.currentTimeMillis();
			long bp=(bytes/((endTime-startTime)));
			Main.bps.add(bp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
