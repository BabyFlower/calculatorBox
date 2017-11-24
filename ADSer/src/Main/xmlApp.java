package Main;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Tina on 2017/10/29.
 */
public class xmlApp {

    public static String xmlHandlemethod(Document doc , String method) {
        String methodname = null;
        Element foot ;
        try {
            //  SAXReader saxReader = new SAXReader();
            // Document doc = saxReader.read(in);
            Element root = doc.getRootElement();

            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                foot = (Element) i.next();
                methodname = foot.elementText(method);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  methodname;
    }
//***************************

    Document document = DocumentHelper.createDocument();
    Element root = document.addElement("Root");
    // root.addAttribute("version", "1.0");
    Element root2 = root.addElement("Root2");

public  void addE(String addE,String addText){
    Element title = root2.addElement(addE);
    title.setText(addText);

}
String result = "";
    /*
    * get lastnode name
    * */
    public String LastNode(Element e){

        //递归遍历当前节点所有的子节点
        List<Element> listElement=e.elements();//所有一级子节点的list
        result= listElement.get(listElement.size()-1).getName();

        return result;
    }
}
