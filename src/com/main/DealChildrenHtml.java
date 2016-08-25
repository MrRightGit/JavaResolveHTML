package com.main;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DealChildrenHtml {

	/**
	 * ���ؿμ�����ÿ�δ�һ��video��Ƶ����ҳ����ʾ�Ŀμ�
	 * 
	 * @param videoNo
	 * videoNo�Ƿ�����ַvideo����������ַ���
	 * ����144������ʵ���Ƶ��ַΪhttp://www.imooc.com/video/144
	 * @param title
	 * title�ǿγ���
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
			 * Java��String.split��������Ĳ�����һ��RegularExpr����һ��������ʽ��
             * ��������ʽ�У���ű�ʾ�����ַ�����˼��������Ҫ��\��ת�塣
             * ��\\.��ʾ����\. ��.
			 */
			s=filePath.split("\\.");
			lastName=s[s.length-1];
			fileName=element.attr("title");
			
		//	System.out.println(filePath+":"+fileName+"."+lastName);
			//���μ��ĵ�ַ����Ϣ��ӵ�set������
			Main.set.add(filePath+"!"+fileName+"."+lastName);
			
//			downLoadFromUrl(filePath, fileName + "." + lastName,
//					"./download/" + className + "/�γ�����/");
			
			
		}
	}
}
