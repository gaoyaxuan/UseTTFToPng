package com.vlife.png.getfont;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class GetFontPng {
	public static void main(String[] args) throws Exception {

		// 需要出现在图片中的汉字
		// String Picturestr = "科\ne";
		// // ttf的绝对路径
		// String fontUrl = "D:\\HanYaShaoNian.ttf";
		// String picPath = "D:\\bb.png";
		String Picturestr = System.getProperty("picstr");
		// ttf的绝对路径
		String fontUrl = System.getProperty("ttf");
		String picPath = System.getProperty("png");

		int strLength = Picturestr.length();
		if (strLength == 0) {
			strLength = 1;
		}
		// 去除\r
		Picturestr = Picturestr.replaceAll("\\\\n", "\n");
		Picturestr = Picturestr.replaceAll("\\\\r", "");
		String replaceBlankStr = replaceBlank(Picturestr).trim();
		String[] eachLineStr = replaceBlankStr.split("\n");

		File file = new File(fontUrl);
		Font font = Font.createFont(Font.TRUETYPE_FONT, file);
		Font font2 = font.deriveFont(Font.TRUETYPE_FONT, 60);
		int width = getPngWidthAndHeight(eachLineStr, font2)[0];
		int height = getPngWidthAndHeight(eachLineStr, font2)[1];
		createImage(eachLineStr, font2, new File(picPath), width, height);
	}

	// 根据str,font的样式以及输出文件目录
	public static void createImage(String str[], Font font, File outFile, Integer width, Integer height)
			throws Exception {
		// 创建图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setClip(0, 0, width, height);
		g.setColor(Color.white);
		// 先用黑色填充整张图片,也就是背景
		g.fillRect(0, 0, width, height);
		// 在换成黑色
		g.setColor(Color.black);
		// 设置画笔字体
		g.setFont(font);
		/** 用于获得垂直居中y */
		// Rectangle clip = g.getClipBounds();
		FontMetrics fm = g.getFontMetrics(font);
		int ascent = fm.getAscent();
		int descent = fm.getDescent();
		// 取y的中心位置
		// int y = (clip.height - (ascent + descent)) / 2 + ascent;
		// 画图的坐标
		int y = 0;
		for (String tempStr : str) {
			// y增加 换行
			y = y + ascent + descent + 20;
			int stringWidth = fm.stringWidth(tempStr);
			int startX = (width - stringWidth) / 2;
			g.drawString(tempStr, startX, y);// 画出字符串
		}
		g.dispose();
		ImageIO.write(image, "png", outFile);// 输出png图片
	}

	/**
	 * 计算宽高
	 * 
	 * @param str
	 * @param font
	 * @return wh[0] 宽 wh[1] 高
	 */
	public static int[] getPngWidthAndHeight(String eachLineStr[], Font font) {
		int[] wh = new int[] { 0, 0 };
		// 最大宽度的字符串的宽度
		int width = 0;
		// 总高度
		int height = 0;
		FontMetrics fm = new JLabel().getFontMetrics(font);
		for (String temp : eachLineStr) {
			int stringWidth = fm.stringWidth(temp);
			int height2 = fm.getHeight() + 25;
			height = height + height2;
			if (stringWidth > width) {
				width = stringWidth;
			}
		}
		wh[0] = width + 150;
		wh[1] = height;
		return wh;
	}

	/**
	 * 去除所有的空格 回车换行
	 *
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\r");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

}
