package com.main;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DealChildrenHtml {

	/**
	 * 下载课件，即每次打开一个video视频的网页中显示的课件
	 * 
	 * @param videoNo
	 * videoNo是访问网址video后面的数字字符串
	 * 例：144，则访问的视频地址为http://www.imooc.com/video/144
	 * @param title
	 * title是课程名
	 */
	public static void doGetFile(String videoNo) {
		// TODO Auto-generated method stub
		Document document=null;
		String filePath;
		String[] s;
		String lastName;
		String fileName;
	//	System.out.println("start"+videoNo);
		try {
			document=Jsoup.connect("http://www.imooc.com/video/"+videoNo).timeout(10*1000).get();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements efilePaths=document.select(".coursedownload a");
	//	System.out.println("---------------------------------------");
		for (Element element : efilePaths) {
			filePath=element.attr("href");
			/*
			 * Java中String.split方法传入的参数是一个RegularExpr，是一个正则表达式。
             * 在正则表达式中，点号表示所有字符的意思，所以需要用\来转义。
             * 则\\.表示的是\. 即.
			 */
			s=filePath.split("\\.");
			lastName=s[s.length-1];
			fileName=element.attr("title");
			
		//	System.out.println(filePath+":"+fileName+"."+lastName);
			//将课件的地址等信息添加到set集合中
			Main.set.add(filePath+"!"+fileName+"."+lastName);
			
//			downLoadFromUrl(filePath, fileName + "." + lastName,
//					"./download/" + className + "/课程资料/");
			
			
		}
	}
}
