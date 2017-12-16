package com.lanying.mp3download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyUtil {
	private static Scanner input = new Scanner(System.in);
	private static final int APPID = 52113;
	private static final String SECRET = "6db1321af20046dbaf65ac4fb76ec884";

	/**
	 * �����û�����ĸ���ƴ����Ӧ��URL
	 * @param songname
	 * @return
	 */
	public static String getURL(String songname) {
		// ���������ģ���Ҫ�Ѹ���תΪUTF8��ʽ
		try {
			songname = URLEncoder.encode(songname, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "https://route.showapi.com/213-1?" 
				+ "keyword="+songname+"&page=1" 
				+ "&showapi_appid="+APPID
				+ "&showapi_test_draft=false" 
				+ "&showapi_timestamp="+getCurrentTime()
				+ "&showapi_sign="+SECRET;
	}

	/**
	 * ��ȡ��ǰ��ʱ�䣬ת����ShowAPI��վҪ���ʱ���ʽ
	 * @return
	 */
	private static String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	/**
	 * ����URL���ʵ���JSON��ʽ������
	 * @param _url
	 * @return
	 */
	public static String getData(String _url){
		try {
			URL url = new URL(_url);
			URLConnection conn = url.openConnection();
			// �����ϵı���Ĭ����UTF8��ʽ�ģ�����ת��������ע����ӵڶ�����������ʾ��UTF8��ʽ�������
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			return sb.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			// �ͷ���Դ
		}
		
		return null;
	}

	// ����data�ĳ��ȣ���̬����������ٸ�\t
	public static String printTab(String data){
		if(data.length()<8){
			return getTab(2);
		}else if(data.length()>8 && data.length()<16){
			return getTab(1);
		}else{
			return "";
		}
	}
	
	private static String getTab(int level){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < level; i ++){
			sb.append("\t");
		}
		return sb.toString();
	}
	
	
	public static List<Music> parseJSON(String data){
		List<Music> mList = new ArrayList<Music>();
		
		JSONObject jsonObj = new JSONObject(data);
		
		JSONObject jsonObjBody = jsonObj.getJSONObject("showapi_res_body");
		JSONObject jsonObjPageBean = jsonObjBody.getJSONObject("pagebean");
		JSONArray jsonArr = jsonObjPageBean.getJSONArray("contentlist");
		for (int i = 0; i < jsonArr.length(); i ++) {
			JSONObject jsonMusic = jsonArr.getJSONObject(i);
			String m4a = jsonMusic.getString("m4a");
			String songname = jsonMusic.getString("songname");
			String singername = jsonMusic.getString("singername");
			// ���������������еĿո��滻Ϊ�»���
			songname = songname.replace(" ", "_");
			singername = singername.replace(" ", "_");
			mList.add(new Music(songname,singername,m4a));
		}
		
		return mList;
	}
	
	/**
	 * ��ȡ�û�����һ��ָ����Χ�ڵ�������������ѡ��
	 * @param min
	 * @param max
	 * @return
	 */
	public static int inputNum(int min,int max){
		do{
			try {
				int res = input.nextInt();
				if(res >= min && res <= max){
					return res;
				}else{
					System.err.println("���ݷ�Χ���Ϸ�������������");
				}
			} catch (InputMismatchException e) {
				System.err.println("�������Ͳ��Ϸ������������룡");
				input.nextLine();
			}
		}while(true);
	}
	
}
