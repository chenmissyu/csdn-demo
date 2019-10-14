package com.chenmissyu.qrcode.utils;

import com.swetake.util.Qrcode;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 二维码图像工具类
 */
public class ImageUtils {

    /**
     * 生成二维码
     * @param backLink  扫码回调链接
     * @param savePath  二维码保存路径
     * @return 返回保存路径（用于保存数据库）
     * @throws IOException
     */
    public static String createQRCode(String backLink,String savePath) throws IOException {
        //计算二维码图片的高宽比
        int v =6;
        int width = 67 + 12 * (v - 1);
        int height = 67 + 12 * (v - 1);

        Qrcode x = new Qrcode();
        x.setQrcodeErrorCorrect('L');
        x.setQrcodeEncodeMode('B');//注意版本信息 N代表数字 、A代表 a-z,A-Z、B代表 其他)
        x.setQrcodeVersion(v);

        byte[] d = backLink.getBytes("utf-8");//汉字转格式需要抛出异常

        //缓冲区
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        //绘图
        Graphics2D gs = bufferedImage.createGraphics();

        gs.setBackground(Color.WHITE);
        gs.setColor(Color.BLACK);
        gs.clearRect(0, 0, width, height);

        //偏移量
        int pixoff = 2;

        if (d.length > 0 && d.length < 120) {
            boolean[][] s = x.calQrcode(d);

            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (s[j][i]) {
                        gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                    }
                }
            }
        }
        gs.dispose();
        bufferedImage.flush();
        //设置图片格式，与输出的路径
        String url = savePath;
        ImageIO.write(bufferedImage, "png", new File(url));
        System.out.println("二维码生成完毕");
        return url;
    }



    /**
     * 图片合成
     * @param mainImgPath   主图片路径
     * @param qrcodePath    二维码路径
     * @param text          文字
     * @param compoundPath  图片合成保存路径
     * @param databasePath  数据库保存路径
     * @return  保存路径
     */
    public static String CompoundImage(String mainImgPath, String qrcodePath,String text,String compoundPath,String databasePath) {
        String ewmurl="";
        try {
            //1、获取主图片
            BufferedImage big = ImageIO.read(new File(mainImgPath));
            java.net.URL url = new URL("https://img-blog.csdn.net/20150906104118760");
            //2、拿到二维码
            BufferedImage erweima = ImageIO.read(new File(qrcodePath));
            int width = 709;
            int height = 472;
            Image image = big.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage bufferedImage2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            //3、开始绘图
            Graphics2D g = bufferedImage2.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.drawImage(erweima, 84, 44, 300, 300, null);
            Font font = new Font("微软雅黑", Font.BOLD, 38);
            g.setFont(font);
            g.setPaint(Color.BLACK);
            //4、设置位置
            int wordWidth = ImageUtils.getWordWidth(font, text);
            int i = width / 2;
            int i1 = (i - wordWidth) / 2;
            int numWidth=i+i1;
            g.drawString(text, numWidth-10, 310+28);
            g.dispose();

            ewmurl =compoundPath;
            ImageIO.write(bufferedImage2, "jpg", new File(ewmurl));
            System.out.println("图片生成完毕");
            ewmurl = databasePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ewmurl;
    }

    /**
     * 获取文字的px宽度
     * @param font      文字字体
     * @param content   文字内容
     * @return px宽度
     */
    public static int getWordWidth(Font font, String content) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int width = 0;
        for (int i = 0; i < content.length(); i++) {
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }

}
