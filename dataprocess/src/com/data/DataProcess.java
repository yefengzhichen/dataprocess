package com.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * �����ƶ���Ϊ���д��룬����ǰ�ڴ���������ļ���������¼��ȡ�������ݲ����л��ܡ�
 * @author yefengzhichen
 * 2016��7��2��
 */
public class DataProcess {

	private static StringBuffer data = new StringBuffer();

	public static void main(String[] args) {
		//����·��Ϊ�ļ���
		String filePath = "C:\\Users\\Administrator\\Desktop\\dataProcess\\code\\data";
		//���Ϊһ��txt�ļ�
		String saveDath = "C:\\Users\\Administrator\\Desktop\\dataProcess\\code\\all.txt";
		readDir(filePath, saveDath);
	}

	public static void readDir(String path, String savePath) {
		try {
			File dir = new File(path);
			File[] tempList = dir.listFiles();
			System.out.println("��Ŀ¼�¶��������" + tempList.length);

			File saveFile = new File(savePath);
			if (!saveFile.exists()) {
				saveFile.createNewFile();
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter(savePath));
			StringBuffer da = new StringBuffer();
			da.append("userID,website,startTime,endTime,ip,port,baseStation");
			da.append("\r\n");
			// String da = new String();
			// da += "userID,website,startTime,endTime,ip,port,baseStation";
			// da += ("\r\n");

			bw.write(da.toString());

			for (int i = 0; i < tempList.length; i++) {
				StringBuffer str = new StringBuffer();
				readTxtFile(tempList[i], str);
				// System.out.println(str);
				bw.append(str);
				System.out.println("saveFile " + tempList[i] + " done!");
			}
			bw.flush();
			bw.close();
			System.out.println("saveTxt Done!");
		} catch (Exception e) {
			System.out.println("�Ҳ���ָ�����ļ�");
			e.printStackTrace();
		}

	}

	public static void readTxtFile(File file, StringBuffer str) {

		try {

			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));// ���ǵ������ʽ
				BufferedReader br = new BufferedReader(read);
				String lineTxt = null;

				String userID = null;
				String website = null;
				String start = null;
				String end = null;
				String ip = null;
				String port = null;
				String baseStation = null;
				int len = 0;
				while ((lineTxt = br.readLine()) != null) {
					String[] content = lineTxt.split(",");
					len = content.length;
					if (len < 18) {
						continue;
					}
					userID = content[0];
					website = content[3];
					start = content[len - 8];
					end = content[len - 7];
					ip = content[len - 5];
					port = content[len - 4];
					baseStation = content[len - 1];

					str.append(userID + ",");
					str.append(website + ",");
					str.append(start + ",");
					str.append(end + ",");
					str.append(ip + ",");
					str.append(port + ",");
					str.append(baseStation + "\r\n");

				}
				read.close();
			}
		} catch (Exception e) {
			System.out.println("�Ҳ���ָ�����ļ�");
			e.printStackTrace();
		}
	}

}
