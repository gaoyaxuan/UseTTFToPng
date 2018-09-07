package com.vlife.png.getfont;

import java.awt.Color;  
import java.awt.Font;  
import java.awt.FontMetrics;  
import java.awt.Graphics;  
import java.awt.image.BufferedImage;  
import java.io.File;

import javax.imageio.ImageIO;  
  
public class GetFontPng {  
    public static void main(String[] args) throws Exception { 
    	
    	//需要出现在图片中的汉字
   
    	String Picturestr=System.getProperty("picstr");
    	//ttf的绝对路径
    	String fontUrl=System.getProperty("ttf");
    	String picPath=System.getProperty("png");
    	int strLength=Picturestr.length();
    	if(strLength==0){
    		strLength=1;
    	}
    	int width =60*10+150;
    	int height= ((strLength%10==0 ? strLength/10 : strLength/10+1)*80 )+30;
    	File file =new File(fontUrl);
    	Font font =Font.createFont( Font.TRUETYPE_FONT,file );
    	Font font2=font.deriveFont(Font.TRUETYPE_FONT,60);
        createImage(Picturestr, font2, new File(picPath), width,height);  
    }  
  
    // 根据str,font的样式以及输出文件目录  
    public static void createImage(String str, Font font, File outFile,  
            Integer width, Integer height) throws Exception {  
        // 创建图片  
        BufferedImage image = new BufferedImage(width, height,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics g = image.getGraphics();  
        g.setClip(0, 0, width, height);  
        g.setColor(Color.white);  
        g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景  
        g.setColor(Color.black);// 在换成黑色  
        g.setFont(font);// 设置画笔字体  
        /** 用于获得垂直居中y */  
//        Rectangle clip = g.getClipBounds();  
        FontMetrics fm = g.getFontMetrics(font);  
        int ascent = fm.getAscent();  
        int descent = fm.getDescent();  
        //取y的中心位置
//        int y = (clip.height - (ascent + descent)) / 2 + ascent;  
        //画图的坐标
        int y = 0 ;  
        int x = 50;
        StringBuffer sb = new StringBuffer();
        int end=1;
        int begin=0;
        for (int i = 0; i < str.length(); i++) {
        	if(i!=0){
        		end=i;
        	}
        	if(end>str.length()){
        		end=str.length();
        	}
        	
        	String st=str.substring(begin, end);
        	if(fm.stringWidth(st)+120>width){
        		
        		sb.append(st+"&&") ;
        		begin=i;
        		
        	}else if (i+1 == str.length()){
        		sb.append(st) ;
        		break;
        	}
        	else{
        		end=end+1;
        	}
        	
        	
		}
        //没个是一行文字
        String str2[]=sb.toString().trim().split("&&");
        
        for(String s:str2){
        	//y增加 换行
        	y=y+ascent+descent+20;
        
            g.drawString(s, x, y);// 画出字符串  
        }
        g.dispose();  
        ImageIO.write(image, "png", outFile);// 输出png图片  
    }  
}  
