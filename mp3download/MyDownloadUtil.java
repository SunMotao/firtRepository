package com.lanying.mp3download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MyDownloadUtil {

	/**
	 * ����ָ��URL������Ӧ��Դ
	 * 
	 * @param url
	 * @throws MalformedURLException 
	 */
	public static void download(File dst,String _url) throws MalformedURLException{
		if(dst == null || dst.isDirectory()){
			throw new IllegalArgumentException("��β��Ϸ�");
		}
		dst.getParentFile().mkdirs();
		URL url = new URL(_url);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			URLConnection conn = url.openConnection();
			long total = conn.getContentLength();
			System.err.println("�ļ��ܴ�С: "+total+" bytes");
			InputStream is = conn.getInputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(new FileOutputStream(dst));
			
			int data = -1;
			long hasRead = 0;// �Ѿ��������ֽ��������ڼ������������
			while((data = bis.read()) != -1){
				bos.write(data);
				hasRead ++;
				if(hasRead%(total/20)==0){
					System.out.print(">>");
				}
			}
			
			byte[] buf = new byte[1024];
			int len = 0;
			double r = 0.05;
			while((len = bis.read(buf)) != -1){
				bos.write(buf, 0, len);
				hasRead += len;
				if(hasRead> total*r){
					System.out.print(">>");
					r += 0.05;
				}
			}
			
			System.out.println("100%");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
