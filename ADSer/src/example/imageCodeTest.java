package example;

import Main.xmlApp;
import org.apache.axis.utils.ByteArrayOutputStream;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Tina on 2018/4/18.
 */


public class imageCodeTest {
    public static void main(String[] args) {
        try {
            FileOutputStream fos = null;
            String srcPath1 = "C:\\Users\\Tina\\Desktop\\cat.jpg";
            FileInputStream fis = new FileInputStream(srcPath1);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[fis.available()];
            int count = 0;
            while ((count = fis.read(buffer)) >= 0) {
                baos.write(buffer, 0, count);

            }

            String picture = new BASE64Encoder().encodeBuffer(baos.toByteArray());
            System.out.println(picture);
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("Root");
            // root.addAttribute("version", "1.0");
            Element root2 = root.addElement("Root2");
            Element title = root2.addElement("picture1");
            title.setText(picture);

           String xxx= document.asXML();

            String toDir = "E:\\jlx_image";   //存储路径

            byte[] buffer1 = new BASE64Decoder().decodeBuffer(xxx);   //对android传过来的图片字符串进行解码

            File destDir = new File(toDir);
            if(!destDir.exists()) {
                destDir.mkdir();
            }
            fos = new FileOutputStream(new File(destDir,"1.jpg"));   //保存图片
            fos.write(buffer);
            fos.flush();
            fos.close();


        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
