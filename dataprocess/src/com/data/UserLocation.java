package com.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserLocation {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String path = "C:\\Users\\Administrator\\Desktop\\dataProcess\\code\\all.txt";
		String userMovePath = "C:\\Users\\Administrator\\Desktop\\dataProcess\\code\\userLoc.txt";
		HashMap<String, ArrayList<String>> map = new HashMap<>();
		BufferedReader br = new BufferedReader(new FileReader(path));
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
		
		File saveFile = new File(userMovePath);
		if (!saveFile.exists()) {
			saveFile.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
		// StringBuffer da = new StringBuffer();

		for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();
			// System.out.println(key+": "+value);
			bw.append(key + "," + value + "\r\n");
		}
		bw.flush();
		bw.close();
		br.close();
		System.out.println("saveTxt " + userMovePath + " Done!");
	}
}
