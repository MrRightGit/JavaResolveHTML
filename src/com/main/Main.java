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

	// 课件集合，避免重复
	public static Set<String> set = new HashSet<>();
	public static List<Long> bps = new ArrayList<>();

	public static void main(String[] args) {
		// Jsoup的Document对象

		Document doc = null;

		String title;
		String savePath;
		String[] videoNos;
		String videoName;

		int classNo = 376;
		try {
			// 获取Jsoup连接
			Connection con = Jsoup.connect("http://www.imooc.com/learn/" + classNo);
			// 获取Document对象
			doc = con.get();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// continue;
		}
		// 获取标签为h2的元素，并获取HTML代码,这个是整个课程的名称
		title = doc.getElementsByTag("h2").html();
		// 过滤文件夹非法字符，这个写法参考正则表达式
		title = title.replaceAll("[\\\\/:\\*\\?\"<>\\|]", "#");
		title = classNo + "_" + title;
		// 地址的写法，根目录用\\,子目录用/
		savePath = "F:\\imooc/Android/" + title + "/";
		File file = new File(savePath);
		System.out.println(title);
		// 这个用法还没找到，我的理解是查找标签a中含有video的标签元素
		Elements videos = doc.select(".video a");
		// 选择高清
		int videoDef = 1;

		for (Element element : videos) {
			videoNos = element.attr("href").split("/");
			if (videoNos[0].equals("")) {
				// System.out.println(videoNos[2]);
				DealChildrenHtml.doGetFile(videoNos[2]);
				// 获取元素的文本
				videoName = element.text();
				videoName = videoName.substring(0, videoName.length() - 4).trim();
				videoName = videoName.replace(" ", "").replace("(", "").replace(")", "").replace(":", "").replace("-",
						"_");

				// 获取视频下载地址
				DownLoadVideo.getVideoURL(videoNos[2], videoDef, videoName, title);

			}
		}
		DownloadCourse.getCourseURLAndName(set, title);
		System.out.println("下载完毕！");
		getBps(title);

	}

	/**
	 * 将下载速度导入
	 * 
	 * @param title
	 */
	public static void getBps(String title) {
		long sum = 0;
		int i = 0;
		for (; i < bps.size(); i++) {
			sum += bps.get(i);
		}
		String str = title + "下载完毕，" + "本次下载网速为：" + sum / i + "b/ms";
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		String strData=sdf.format(date);
		System.out.println(str);

		File file2 = new File("F:\\imooc/bps.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("F:\\imooc/bps.txt", true);
			// \r\n时针对文件的换行操作
			fileWriter.write(str +"【"+strData+"】"+"【1024*1024】"+"\r\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
