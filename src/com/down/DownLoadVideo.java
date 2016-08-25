package com.down;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DownLoadVideo {

	/**
	 * Json处理获取Video的URL
	 * 
	 * @param videoNo
	 * @param videoDef
	 * @param videoName
	 */
	public static void getVideoURL(String videoNo,int videoDef,String videoName,String title){
		Document jsonDoc = null;
		String jsonData;
		JSONObject jsonObject;
		JSONArray mpath;
		
		try {
			//获取Json格式的网页Document
			jsonDoc=Jsoup.connect("http://www.imooc.com/course/ajaxmediainfo/?mid="
					+ videoNo + "&mode=flash").timeout(10000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		//这个Document就是个Json代码
		jsonData=jsonDoc.text();
		//通过Json字符串创建Json对象
		jsonObject=new JSONObject(jsonData);
		mpath=jsonObject.getJSONObject("data").getJSONObject("result").getJSONArray("mpath");
		String downloadPath=mpath.getString(videoDef).toString().trim();
		downloadVideoByURL(downloadPath,videoName,title);
	}

	private static void downloadVideoByURL(String downloadPath, String videoName,String title) {
		// TODO Auto-generated method stub
		DownloadCourse.downloadByURLAndName2(downloadPath, videoName+".mp4", title);
	}
}
