package com.course.selection.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RandomValidateCode {
	private Random random = new Random();
	private String randString = "2345678abcdefghijkmnprstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";// 随机产生的字符串

	private int width = 80;// 图片宽
	private int height = 22;// 图片高
	private int lineSize = 10;// 干扰线数量
	private int strLength = 3;// 随机产生字符数量

	/*
	 * 获得字体
	 */
	private Font getFont() {
		return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
	}

	/*
	 * 获得颜色
	 */
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/*
	 * 绘制字符串
	 */
	private void drowString(Graphics g, String s, int i) {
		g.setFont(getFont());
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(s, (width - 5) / strLength * i + 5, 17);
	}

	/*
	 * 绘制干扰线
	 */
	private void drowLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(30);
		int yl = random.nextInt(30);
		g.drawLine(x, y, x + xl, y + yl);
	}

	/**
	 * 生成随机图片
	 */
	public void getRandcode(HttpServletRequest request, HttpServletResponse response, String key) {
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		g.setColor(getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			drowLine(g);
		}
		// 绘制随机字符
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strLength; i++) {
			int beginIndex = random.nextInt(randString.length());
			String s = randString.substring(beginIndex, beginIndex + 1);
			sb.append(s);
			drowString(g, s, i);
		}
		// 将随机生成的验证码放入session中
		request.getSession().setAttribute(key, sb.toString().toLowerCase());
		g.dispose();
		try {
			ByteArrayOutputStream tmp = new ByteArrayOutputStream();
			ImageIO.write(image, "png", tmp);
			tmp.close();
			Integer contentLength = tmp.size();
			response.setHeader("content-length", contentLength + "");
			response.getOutputStream().write(tmp.toByteArray());// 将内存中的图片通过流动形式输出到客户端
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
