package com.data;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import static com.data.SwingConsole.*;


/**
 * 主函数，执行流程
 * @author yefengzhichen
 * 2016年7月2日
 */
public class userPath extends JFrame {

	private static final int MAX = 10000;
	// Base,interval:distance between last location and current location
	// base + rand(interval)
	private static final int BASE = 360;
	private static final int RADIUS = 30;
	private static Random random = new Random(10);

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	JPanel textPanel = new JPanel();
	private JLabel userLabel = new JLabel("显用户个数");
	private JLabel locCountLabel = new JLabel("用户位置最大数量");
	private JLabel userIndexLabel = new JLabel("指定用户索引");
	private JTextField userText = new JTextField(10);
	private JTextField locCountText = new JTextField(10);
	private JTextField userIndexText = new JTextField(20);
	private int randomThrow = 0;

	private static Map<String, Location> mapLocation = new HashMap<>();
	private static HashMap<String, ArrayList<String>> map = new HashMap<>();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		userPath path = new userPath();
		path.dataPath();
	}
	
	public userPath() {
		int userCount = 4;
		int locCount = 20;
		String userIndex = "1,2,3,4";
		int[] userIndexArray = { 1, 2, 3, 4 };
		PathGui pathGui = new PathGui(map, mapLocation, userCount, locCount, userIndexArray);
		add(pathGui);
		userText.setText("" + userCount);
		userText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String user = ((JTextField) e.getSource()).getText();
				int count = Integer.valueOf(user);
				String temp = "";
				for (int i = 0; i < count; i++) {
					temp += i;
				}
				userIndexText.setText(temp);
				pathGui.setUserCount(count);
			}
		});
		locCountText.setText("" + locCount);
		locCountText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String loc = ((JTextField) e.getSource()).getText();
				int count = Integer.valueOf(loc);
				pathGui.setLocCount(count);
			}
		});
		userIndexText.setText(userIndex);
		userIndexText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String user = ((JTextField) e.getSource()).getText();
				String[] strArray = user.split(",");
				int[] index = new int[strArray.length];
				for (int i = 0; i < strArray.length; i++) {
					index[i] = Integer.valueOf(strArray[i]);
				}
				pathGui.setUserIndex(index);
			}
		});
		textPanel.setLayout(new FlowLayout());
		textPanel.add(userLabel);
		textPanel.add(userText);
		textPanel.add(locCountLabel);
		textPanel.add(locCountText);
		textPanel.add(userIndexLabel);
		textPanel.add(userIndexText);

		add(BorderLayout.SOUTH, textPanel);
	}
	
	public void dataPath() throws IOException{
		Dimension screensize = tk.getScreenSize();
		int width = screensize.width - 100;
		int height = screensize.height - 50;
		// String path =
		// "C:\\Users\\Administrator\\Desktop\\dataProcess\\code\\all.txt";
		// src\\all.txt
		// String path = "all.txt";
//		BufferedReader br = new BufferedReader(new FileReader(path));
		InputStream is=this.getClass().getResourceAsStream("/all.txt"); 
		
        BufferedReader br=new BufferedReader(new InputStreamReader(is));  
		
		String lineTxt = null;
		int recordNum = 0;
		lineTxt = br.readLine();
		String userID = null;
		String baseStation = null;
		while ((lineTxt = br.readLine()) != null) {
			++recordNum;
			String[] content = lineTxt.split(",");
			userID = content[0];
			baseStation = content[6];
			if (map.containsKey(userID)) {
				ArrayList<String> value = map.get(userID);
				value.add(baseStation);
				map.put(userID, value);
			} else {
				ArrayList<String> value = new ArrayList<>();
				value.add(baseStation);
				map.put(userID, value);
			}
		}
		System.out.println("清洗后的记录为：" + recordNum);
		int userNum = map.size();
		System.out.println("用户数量为：" + userNum);
		int count = 0;
		for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();
			Location lastLoc = null;
			for (String str : value) {
				Location location = mapLocation.get(str);
				if (location == null) {
					location = getRandLocation(str, lastLoc, width, height);
					mapLocation.put(str, location);
				}
				lastLoc = location;
			}
		}
		
		System.out.println("getRandLocation Done! randomThrow = " + randomThrow);
		// traverseMap(mapLocation);

		run(new userPath(), width, height);
		br.close();
	}
	
	public void traverseMap(Map<String, Location> map) {
		for (Map.Entry<String, Location> entry : map.entrySet()) {
			System.out.println(
					"key:" + entry.getKey() + ",value:" + entry.getValue().getX() + "," + entry.getValue().getY());
		}
	}

	public Location getRandLocation(String str, Location lastLoc, int width, int height) {
		width = width;
		height = height;
		
		int x = 0;
		int y = 0;
		if (lastLoc == null) {
			x = width/3 + random.nextInt(width/3);
			y = height/3 + random.nextInt(height/3);
		} else {
			// System.out.println("theta=" + theta);
			double theta = Math.PI * random.nextInt(BASE) * 2 / BASE;
			x = (int) (lastLoc.getX() + RADIUS * Math.sin(theta));
			y = (int) (lastLoc.getY() + RADIUS * Math.cos(theta));
			while (x <= 0 || y <= 0 || x > width-100 || y > height-50) {
//				System.out.println("x=" + x + ",y=" + y);
				randomThrow++;
				theta = Math.PI * random.nextInt(BASE) * 2 / BASE;
				x = (int) (lastLoc.getX() + RADIUS * Math.sin(theta));
				y = (int) (lastLoc.getY() + RADIUS * Math.cos(theta));
			}
		}
		Location resu = new Location(x, y);
		// System.out.println("x=" + x + ",y=" + y);
		return resu;
	}

}
