package com.zslin.test;

import com.zslin.bus.tools.PictureTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * Created by zsl on 2019/4/15.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("zsl")
public class PictureTest {

    @Autowired
    private PictureTools pictureTools;

    @Test
    public void test02() {
        String name = "奎香乡异地搬迁户图片/仙马村易地搬迁相片/中沟组/张富勇/430709722420928027.jpg";
        System.out.println("fileName:"+pictureTools.getFileName(name));
        System.out.println("fileNameNoSuffix::"+pictureTools.getFileNameNoSuffix(name));
        System.out.println("isPicture::"+pictureTools.isPicture(name));
    }

    @Test
    public void test01() {
        File f = new File("D:/temp/kxx.zip");
        try {
            pictureTools.readFile("xm", 6, f); //6表示奎香乡
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
