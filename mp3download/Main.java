package com.lanying.mp3download;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		do{
			// 请用户输入歌名
			Scanner input = new Scanner(System.in);
			System.out.println("请输入歌名");
			String name = input.nextLine();

			// 根据歌名拼接出URL
			String url = MyUtil.getURL(name);

			// 获取ShowAPI服务器上的数据
			String data = MyUtil.getData(url);

			// JSON格式的数据，需要解析
			List<Music> mList = MyUtil.parseJSON(data);

			// 遍历
			for (int i = 0; i < mList.size(); i++) {
				System.out.println("" + (i + 1) + mList.get(i));
			}

			// 获取用户想要下载的那首歌
			System.out.println("请输入您想下载的歌曲编号");
			int choice = MyUtil.inputNum(1, mList.size());
			Music m = mList.get(choice - 1);

			// 下载
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
			
			System.out.println("是否继续？y/n");
			if(input.next().equalsIgnoreCase("n")){
				break;
			}
		}while(true);
		System.out.println("Bye! O(∩_∩)O~");
	}

}
