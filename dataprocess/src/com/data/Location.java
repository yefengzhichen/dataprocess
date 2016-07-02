package com.data;


/**
 * 保存用于界面展示的用户实时位置
 * @author yefengzhichen
 * 2016年7月2日
 */
public class Location {
	private int x;
	private int y;

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Location:x=" + x + ",y=" + y;
	}
}
