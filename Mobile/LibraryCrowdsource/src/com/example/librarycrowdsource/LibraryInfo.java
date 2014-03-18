package com.example.librarycrowdsource;

public class LibraryInfo {
	private String noise = "";
	private String crowd = "";
	
	public LibraryInfo(String noise, String crowd){
		this.noise = noise;
		this.crowd = crowd;
	}
	
	public String getNoise() {
		return noise;
	}
	public void setNoise(String noise) {
		this.noise = noise;
	}
	public String getCrowd() {
		return crowd;
	}
	public void setCrowd(String crowd) {
		this.crowd = crowd;
	}
}

