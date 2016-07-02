package com.data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.GeneralPath;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import javax.swing.JPanel;

public class PathGui extends JPanel
{
  Toolkit tk = Toolkit.getDefaultToolkit();
  Dimension screensize = this.tk.getScreenSize();
  private int width = this.screensize.width - 300;
  private int height = this.screensize.height - 300;
  private static final Color[] colors = { Color.BLACK, Color.BLUE, Color.GRAY, Color.GREEN, Color.RED };
  private static final int COLORTYPE = 5;
  private static final int MAX = 10000;
  private static Random random = new Random(47L);
  private int userCount = 4;
  private int locCount = 20;
  private int[] userIndex = new int[100];
  private boolean inputFlag = false;
  Map<String, ArrayList<String>> map = new HashMap();
  Map<String, Location> mapLocation = new HashMap();

  public PathGui(Map<String, ArrayList<String>> map, Map<String, Location> mapLocation, int userCount, int locCount, int[] userIndex)
  {
    this.map = map;
    this.mapLocation = mapLocation;
    this.userCount = userCount;
    this.locCount = locCount;
    for (int i = 0; i < userIndex.length; i++)
      this.userIndex[i] = userIndex[i];
  }

  public void setUserCount(int userCount)
  {
    this.userCount = userCount;

    for (int i = 0; i < userCount; i++) {
      this.userIndex[i] = (i + 1);
    }
    repaint();
  }

  public void setLocCount(int locCount) {
    this.locCount = locCount;
    repaint();
  }

  public void setUserIndex(int[] userIndex)
  {
    for (int i = 0; i < userIndex.length; i++) {
      this.userIndex[i] = (userIndex[i] + 1);
    }

    this.userCount = userIndex.length;
    repaint();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    int count = 0;
    int index = 0;
    for (Map.Entry entry : this.map.entrySet()) {
      index++;
      if (index == this.userIndex[count]) {
        String userID = (String)entry.getKey();
        ArrayList concretePath = (ArrayList)entry.getValue();
        int pathLen = concretePath.size();
        Location[] path = new Location[pathLen];
        for (int i = 0; i < pathLen; i++) {
          String key = (String)concretePath.get(i);
          Location loc = (Location)this.mapLocation.get(key);
          loc.setX(loc.getX());
          loc.setY(loc.getY());
          path[i] = loc;
        }
        g.setColor(colors[random.nextInt(5)]);

        if (pathLen >= this.userCount) {
          for (int i = 1; i < pathLen; i++) {
            int x1 = path[(i - 1)].getX();
            int y1 = path[(i - 1)].getY();
            int x2 = path[i].getX();
            int y2 = path[i].getY();

            Graphics2D g2 = (Graphics2D)g;
            drawAL(x1, y1, x2, y2, g2);
            if (i > this.locCount) {
              break;
            }
          }
        }
        System.out.println("userID " + userID + " : The path is shown!");
        count++;
        if (count >= this.userCount)
          break;
      }
    }
  }

  public void drawAL(int sx, int sy, int ex, int ey, Graphics2D g2)
  {
    double H = 10.0D;
    double L = 4.0D;
    int x3 = 0;
    int y3 = 0;
    int x4 = 0;
    int y4 = 0;
    double awrad = Math.atan(L / H);
    double arraow_len = Math.sqrt(L * L + H * H);
    double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
    double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
    double x_3 = ex - arrXY_1[0];
    double y_3 = ey - arrXY_1[1];
    double x_4 = ex - arrXY_2[0];
    double y_4 = ey - arrXY_2[1];

    Double X3 = new Double(x_3);
    x3 = X3.intValue();
    Double Y3 = new Double(y_3);
    y3 = Y3.intValue();
    Double X4 = new Double(x_4);
    x4 = X4.intValue();
    Double Y4 = new Double(y_4);
    y4 = Y4.intValue();

    g2.drawLine(sx, sy, ex, ey);

    GeneralPath triangle = new GeneralPath();
    triangle.moveTo(ex, ey);
    triangle.lineTo(x3, y3);
    triangle.lineTo(x4, y4);
    triangle.closePath();

    g2.fill(triangle);
  }

  public double[] rotateVec(int px, int py, double ang, boolean isChLen, double newLen)
  {
    double[] mathstr = new double[2];

    double vx = px * Math.cos(ang) - py * Math.sin(ang);
    double vy = px * Math.sin(ang) + py * Math.cos(ang);
    if (isChLen) {
      double d = Math.sqrt(vx * vx + vy * vy);
      vx = vx / d * newLen;
      vy = vy / d * newLen;
      mathstr[0] = vx;
      mathstr[1] = vy;
    }
    return mathstr;
  }
}