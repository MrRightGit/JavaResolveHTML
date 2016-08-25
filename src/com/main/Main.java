package com.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.down.DownLoadVideo;
import com.down.DownloadCourse;

public class Main {

	// �μ����ϣ������ظ�
	public static Set<String> set = new HashSet<>();
	public static List<Long> bps = new ArrayList<>();

	public static void main(String[] args) {
		// Jsoup��Document����

		Document doc = null;

		String title;
		String savePath;
		String[] videoNos;
		String videoName;

		int classNo = 376;
		try {
			// ��ȡJsoup����
			Connection con = Jsoup.connect("http://www.imooc.com/learn/" + classNo);
			// ��ȡDocument����
			doc = con.get();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// continue;
		}
		// ��ȡ��ǩΪh2��Ԫ�أ�����ȡHTML����,����������γ̵�����
		title = doc.getElementsByTag("h2").html();
		// �����ļ��зǷ��ַ������д���ο�������ʽ
		title = title.replaceAll("[\\\\/:\\*\\?\"<>\\|]", "#");
		title = classNo + "_" + title;
		// ��ַ��д������Ŀ¼��\\,��Ŀ¼��/
		savePath = "F:\\imooc/Android/" + title + "/";
		File file = new File(savePath);
		System.out.println(title);
		// ����÷���û�ҵ����ҵ�����ǲ��ұ�ǩa�к���video�ı�ǩԪ��
		Elements videos = doc.select(".video a");
		// ѡ�����
		int videoDef = 1;

		for (Element element : videos) {
			videoNos = element.attr("href").split("/");
			if (videoNos[0].equals("")) {
				// System.out.println(videoNos[2]);
				DealChildrenHtml.doGetFile(videoNos[2]);
				// ��ȡԪ�ص��ı�
				videoName = element.text();
				videoName = videoName.substring(0, videoName.length() - 4).trim();
				videoName = videoName.replace(" ", "").replace("(", "").replace(")", "").replace(":", "").replace("-",
						"_");

				// ��ȡ��Ƶ���ص�ַ
				DownLoadVideo.getVideoURL(videoNos[2], videoDef, videoName, title);

			}
		}
		DownloadCourse.getCourseURLAndName(set, title);
		System.out.println("������ϣ�");
		getBps(title);

	}

	/**
	 * �������ٶȵ���
	 * 
	 * @param title
	 */
	public static void getBps(String title) {
		long sum = 0;
		int i = 0;
		for (; i < bps.size(); i++) {
			sum += bps.get(i);
		}
		String str = title + "������ϣ�" + "������������Ϊ��" + sum / i + "b/ms";
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
		String strData=sdf.format(date);
		System.out.println(str);

		File file2 = new File("F:\\imooc/bps.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("F:\\imooc/bps.txt", true);
			// \r\nʱ����ļ��Ļ��в���
			fileWriter.write(str +"��"+strData+"��"+"��1024*1024��"+"\r\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
