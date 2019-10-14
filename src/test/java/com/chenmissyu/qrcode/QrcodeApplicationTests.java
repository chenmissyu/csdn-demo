package com.chenmissyu.qrcode;

import com.chenmissyu.qrcode.utils.ImageUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QrcodeApplicationTests {

    @Test
    public void contextLoads() {
        try{
            String link = "https://blog.csdn.net/chenmissyu";
            String savePath = "E:\\CSDN\\a-demo\\QRcode.png";
            String eurl = ImageUtils.createQRCode( link, savePath );
            String bigurl = "E:\\CSDN\\a-demo\\demo.jpg";           //主图片路径
            String ewmPath = "E:\\CSDN\\a-demo\\test.jpg";         //合成路径
            String databasePath = "/upload/ewm/test.jpg";  //数据库保存路径
            ImageUtils.CompoundImage( bigurl, eurl, "101", ewmPath, databasePath );
        }catch(Exception e){
        }
    }

}
