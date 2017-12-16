package com.lanying.mp3download;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		do{
			// ���û��������
			Scanner input = new Scanner(System.in);
			System.out.println("���������");
			String name = input.nextLine();

			// ���ݸ���ƴ�ӳ�URL
			String url = MyUtil.getURL(name);

			// ��ȡShowAPI�������ϵ�����
			String data = MyUtil.getData(url);

			// JSON��ʽ�����ݣ���Ҫ����
			List<Music> mList = MyUtil.parseJSON(data);

			// ����
			for (int i = 0; i < mList.size(); i++) {
				System.out.println("" + (i + 1) + mList.get(i));
			}

			// ��ȡ�û���Ҫ���ص����׸�
			System.out.println("�������������صĸ������");
			int choice = MyUtil.inputNum(1, mList.size());
			Music m = mList.get(choice - 1);

			// ����
			File mp3 = new File("download/" + m.getSongName() + "_" + m.getSingerName() + ".mp3");
			try {
				MyDownloadUtil.download(mp3,m.getM4a());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			try {
				Runtime.getRuntime().exec("C:\\Program Files (x86)\\Windows Media Player\\wmplayer.exe "+mp3.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("�Ƿ������y/n");
			if(input.next().equalsIgnoreCase("n")){
				break;
			}
		}while(true);
		System.out.println("Bye! O(��_��)O~");
	}

}
