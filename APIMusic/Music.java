package com.sxt.APIMusic;

public class Music {
	private String songName;
	private String singerName;
	private String m4a;// 下载地址

	public Music() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Music(String songName, String singerName, String m4a) {
		super();
		this.songName = songName;
		this.singerName = singerName;
		this.m4a = m4a;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getSingerName() {
		return singerName;
	}

	public void setSingerName(String singerName) {
		this.singerName = singerName;
	}

	public String getM4a() {
		return m4a;
	}

	public void setM4a(String m4a) {
		this.m4a = m4a;
	}

	@Override
	public String toString() {
		return "\t歌曲名: " + songName + "\n\t歌手名:" + singerName;
	}

	
	
}
