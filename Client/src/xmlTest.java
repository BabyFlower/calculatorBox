/**
 * Created by Tina on 2017/10/30.
 */

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.*;
import org.jcp.xml.dsig.internal.dom.DOMBase64Transform;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class xmlTest {
    static  String result;
    public  static  void main(String args[]) throws DocumentException {
File f = new File("src\\test_detect.xml");
        SAXReader reader = new SAXReader();
        Document doc = reader.read(f);
        String xml = doc.asXML();
try{
    Document document=DocumentHelper.parseText(xml);//获取document对象,如果文档无节点，则会抛出Exception提前结束
    Element root=document.getRootElement();//获取根节点
    getNodes(root);//从根节点开始遍历所有节点
    System.out.println(result);
    }
    catch (Exception e){
    e.printStackTrace();
}
    }


    public static void DOM4Jcreate(File file)throws Exception {
        org.dom4j.Document document = DocumentHelper.createDocument();
        org.dom4j.Element root = document.addElement("一级root");
       // root.addAttribute("version", "1.0");

        org.dom4j.Element ss = root.addElement("二级root");
        org.dom4j.Element title = ss.addElement("title");
        title.setText("新闻国内");
        //...
        XMLWriter writer = new XMLWriter(new FileOutputStream(file), OutputFormat.createPrettyPrint());
        writer.setEscapeText(false);//字符是否转义,默认true
        writer.write(document);
        writer.close();
    }

    /**
     * 从指定节点开始,递归遍历所有子节点
     */
    public static void getNodes(Element node){


        //递归遍历当前节点所有的子节点
        List<Element> listElement=node.elements();//所有一级子节点的list
       result= listElement.get(listElement.size()-1).getName();
      /*  for(Element e:listElement){//遍历所有一级子节点
                result=e.getName();
        }*/
    }
}
