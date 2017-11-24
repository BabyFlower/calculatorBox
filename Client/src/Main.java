
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.*;
public class Main {

    public static void main(String[] args) throws Exception{
      //  System.out.println("Hello World!");
        /*初始化*/
               URL url = new java.net.URL("http://localhost:8080/ADSer/ServletHandle");
            /* URL url = new java.net.URL("http://localhost:8080/ServiceTest/MeeServlet");*/
                URLConnection con = url.openConnection();

           //    con.setRequestProperty("ContentType","text/html;charset=utf-8");
                con.setUseCaches(false);
                con.setDoOutput(true);
                con.setDoInput(true);
     //   con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        File f = new File("src\\s.xml");
        SAXReader reader = new SAXReader();
        Document doc = reader.read(f);
        String xml = doc.asXML();
        System.out.println(xml.length());
    StringBuilder sb = new StringBuilder();
    sb.append(xml);
                /*发送*/
                DataOutputStream dataOut = new DataOutputStream(con.getOutputStream());
                dataOut.writeUTF(sb.toString());
                dataOut.flush();
                dataOut.close();


                /*获取服务器端返回信息*/
                DataInputStream in =new DataInputStream(con.getInputStream());
            // int str = in.readInt();
               String stringValue = in.readUTF();
                in.close();
                System.out.println(stringValue);
    }
}
