package Main;


import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import DBManager.*;
import com.sun.deploy.Environment;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.dom4j.*;
import org.dom4j.io.*;
import sun.misc.BASE64Decoder;
import static DBManager.EventDAO.*;
/*
 * Created by Tina on 2017/10/29.*/


public class ServletHandle extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("connect success");


        try {
    DataInputStream in = new DataInputStream(request.getInputStream());
String xml = in.readUTF();
    System.out.println(xml);
//SAXReader reader = new SAXReader();
 //   StringBuilder recordxml= new StringBuilder(xml);
Document doc = DocumentHelper.parseText(xml);
String method = xmlApp.xmlHandlemethod(doc,"Method");
DataOutputStream out = new DataOutputStream(response.getOutputStream());
/*
* 声明4个图片路径
* */


/* ******************调用method******/
switch (method){
    case "test":
//Connection connection = DB.dbcon();
        System.out.println("test");
try {

    out.writeUTF("11");
    out.flush();
    out.close();
}catch (Exception e){
    e.getStackTrace();
    System.out.println("testhere");
}
        break;
/*
* 相同条码，向android传值
* */
    case "bind":

//Connection connection = DB.dbcon();
        try {
    String bar_code = xmlApp.xmlHandlemethod(doc,"bar_code");
        Boolean eventexist = eventSelect("DETECT_RECORD", "BAR_CODE", bar_code);
        if(eventexist){//已经在数据库绑定过，查询各绑定信息发给android
            String EQUIP_CATEG=bindinfo(bar_code,"EQUIP_CATEG");
            String SCHEME_ID=bindinfo(bar_code,"SCHEME_ID");
            String LENGTH=bindinfo(bar_code,"LENGTH");
            String HEIGHT=bindinfo(bar_code,"HEIGHT");
            String WIDTH=bindinfo(bar_code,"WIDTH");
            String PICPATH1=bindinfo(bar_code,"PICPATH1");
            String PICPATH2=bindinfo(bar_code,"PICPATH2");
            String PICPATH3=bindinfo(bar_code,"PICPATH3");
            String PICPATH4=bindinfo(bar_code,"PICPATH4");
            String FACTORY=bindinfo(bar_code,"FACTORY");
            String LOCATION=bindinfo(bar_code,"LOCATION");
            String POSTCODE=bindinfo(bar_code,"POSTCODE");
            String PHONE_NUMBER=bindinfo(bar_code,"PHONE_NUMBER");
            String ARRIVAL_DATE=bindinfo(bar_code,"ARRIVAL_DATE");

            String TEST_TIME = bindinfodate(bar_code);

            EQUIP_CATEG=xmlApp.nulltoblank(EQUIP_CATEG);
            SCHEME_ID=xmlApp.nulltoblank(SCHEME_ID);
            LENGTH=xmlApp.nulltoblank(LENGTH);
            HEIGHT=xmlApp.nulltoblank(HEIGHT);
            WIDTH=xmlApp.nulltoblank(WIDTH);
            PICPATH1=xmlApp.nulltoblank(PICPATH1);
            PICPATH2=xmlApp.nulltoblank(PICPATH2);
            PICPATH3=xmlApp.nulltoblank(PICPATH3);
            PICPATH4=xmlApp.nulltoblank(PICPATH4);
            FACTORY=xmlApp.nulltoblank(FACTORY);
            LOCATION=xmlApp.nulltoblank(LOCATION);
            POSTCODE=xmlApp.nulltoblank(POSTCODE);
            PHONE_NUMBER=xmlApp.nulltoblank(PHONE_NUMBER);
            ARRIVAL_DATE=xmlApp.nulltoblank(ARRIVAL_DATE);
            TEST_TIME=xmlApp.nulltoblank(TEST_TIME);


            xmlApp xmlApp22222 = new xmlApp();
            //构造xml文件
            xmlApp22222.addE("EQUIP_CATEG",EQUIP_CATEG);
            xmlApp22222.addE("SCHEME_ID",SCHEME_ID);
            xmlApp22222.addE("LENGTH",LENGTH);
            xmlApp22222.addE("HEIGHT",HEIGHT);
            xmlApp22222.addE("WIDTH",WIDTH);
            xmlApp22222.addE("PICPATH1",PICPATH1);
            xmlApp22222.addE("PICPATH2",PICPATH2);
            xmlApp22222.addE("PICPATH3",PICPATH3);
            xmlApp22222.addE("PICPATH4",PICPATH4);
            xmlApp22222.addE("FACTORY",FACTORY);
            xmlApp22222.addE("LOCATION",LOCATION);
            xmlApp22222.addE("POSTCODE",POSTCODE);
            xmlApp22222.addE("PHONE_NUMBER",PHONE_NUMBER);
            xmlApp22222.addE("ARRIVAL_DATE",ARRIVAL_DATE);
            xmlApp22222.addE("TEST_TIME",TEST_TIME);

            String failxml = xmlApp22222.document.asXML();
            out.writeUTF(failxml);
            out.flush();
            out.close();

        }else {//没绑定
            out.writeUTF("nobind");//////////////////
            out.flush();
            out.close();

        }

        }
        catch (Exception e){e.printStackTrace();}
        break;
///////////////////////////////////从smid数据库获得smid列表，然后发给app
    case "id":
        try {


String dalei = xmlApp.xmlHandlemethod(doc,"equip_categ");//获取大类
Map map= EventDAO.bangdingshmidDAO(dalei);
String xmlID="";
for(int i=0;i<map.size();i++){
    xmlID=xmlID+map.get(i)+";";

}
            System.out.println(xmlID+"sh71");
            out.writeUTF(xmlID);
            out.flush();
            out.close();
        }
catch (Exception e){e.printStackTrace();}
        break;
///////////////////////////////////////////////
    case "pptest":
//Connection connection = DB.dbcon();
        System.out.println("pptest");
        Boolean eventexist2=true;
        String method22 = xmlApp.xmlHandlemethod(doc,"lastname");
        try {
         eventexist2 = eventSelect("DETECT_RECORD", "BAR_CODE", method22);}catch (Exception e){e.printStackTrace();}
        if(eventexist2){
        out.writeUTF("success");
        out.flush();
        out.close();}
        else {
            out.writeUTF("fail");
            out.flush();
            out.close();
        }
        break;
    case "ddtest":
//Connection connection = DB.dbcon();
        System.out.println("sh+48+ddtest");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
try{

    Connection connection = DB.getConnection();
        //生成SQL代码
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE Persons\n" +
                "(\n" +
                "Id_P int,\n" +
                "LastName varchar(255),\n" +
                "FirstName varchar(255),\n" +
                "Address varchar(255),\n" +
                "City varchar(255)\n" +
                ")");
    preparedStatement = connection.prepareStatement(sb.toString());
    preparedStatement.executeUpdate();}
        catch (Exception e){
    e.printStackTrace();
            System.out.println("sh 71");
        }
        out.writeUTF("person");
        out.flush();
        out.close();
        break;
        /*
        *
        * */
    case "dbtest":
//Connection connection = DB.dbcon();
       try{
    Connection connection = DB.getConnection();
if(!connection.isClosed())
{out.writeUTF("22");
        out.flush();
        out.close();}
        }
        catch (Exception e){e.printStackTrace();
            System.out.println("e.servlethandle 59");}
        break;
    case "Login":
        try {
        System.out.println("Login");
        String user = xmlApp.xmlHandlemethod(doc,"username");
        String password = xmlApp.xmlHandlemethod(doc,"password");
       String result= Login(user,password);
            xmlApp xmlApp222 = new xmlApp();
            xmlApp222.addE("Result",result);
            String failxml = xmlApp222.document.asXML();
            out.writeUTF(failxml);
            out.flush();
            out.close();
        }
        catch (Exception e){e.printStackTrace();}
        break;

    case  "Insert":
        try{
        System.out.println("insert");
            String str22=EventDAO.taskidget();//获取taskid
            if(str22.equals("notask_id")){
                ///////////////////无task_id
                out.writeUTF("taskfail");
                out.flush();
                out.close();
            }
            else {
                //有tsakid
            String str1 = xmlApp.xmlHandlemethod(doc,"bar_code");
            String str2 = xmlApp.xmlHandlemethod(doc,"equip_categ");//设备类型
            String str3 = xmlApp.xmlHandlemethod(doc,"scheme_id");
/////////////////////////////////////////date
            String str4 = xmlApp.xmlHandlemethod(doc,"test_time");
            System.out.println(str4+"sh142");
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date testtime2 = format1.parse(str4);
            System.out.println(testtime2+"time测试sh145"+testtime2.getTime());
            java.sql.Date sqltesttime=new java.sql.Date(testtime2.getTime());
            System.out.println(sqltesttime+"sh148");
/////////
            String str5 = xmlApp.xmlHandlemethod(doc,"chk_conc");
            String str6 = xmlApp.xmlHandlemethod(doc,"length");
            String str7 = xmlApp.xmlHandlemethod(doc,"height");
            String str8 = xmlApp.xmlHandlemethod(doc,"width");
//////////////////////unuseful
            String str9 = xmlApp.xmlHandlemethod(doc,"picpath1");
            String str10 = xmlApp.xmlHandlemethod(doc,"picpath2");
            String str11 = xmlApp.xmlHandlemethod(doc,"picpath3");
            String str12 = xmlApp.xmlHandlemethod(doc,"picpath4");
///////////////////
            String str13 = xmlApp.xmlHandlemethod(doc,"DETECT_STATUS");
            String str14 = xmlApp.xmlHandlemethod(doc,"equip_name");
            String str15 = xmlApp.xmlHandlemethod(doc,"number");
            String str16 = xmlApp.xmlHandlemethod(doc,"factory");
            String str17 = xmlApp.xmlHandlemethod(doc,"location");
            String str18 = xmlApp.xmlHandlemethod(doc,"postcode");
            String str19 = xmlApp.xmlHandlemethod(doc,"phone_number");
            String str20 = xmlApp.xmlHandlemethod(doc,"arrival_date");
            String str21 = xmlApp.xmlHandlemethod(doc,"tuopancode");
           // String str22 = xmlApp.xmlHandlemethod(doc,"task_id");



              EventDAO.detect_eventInsert(str1, str2, str3, sqltesttime, str5, str6, str7, str8, xmlApp.picpath1, xmlApp.picpath2, xmlApp.picpath3, xmlApp.picpath4, str13, str14, str15, str16, str17, str18, str19, str20, str22);
/*更新对应equip_categ的equip_name
* */System.out.println("insert success sh 264");
              String equip_name = "";
              switch (str2) {
                  case "1":equip_name = "单相单表位金属";break;
                  case "2":equip_name = "单相单表位非金属";break;
                  case "3":equip_name = "单相多表位金属";break;
                  case "4":equip_name = "单相多表位非金属";break;
                  case "5":equip_name = "三相单表位金属";break;
                  case "6":equip_name = "三相单表位非金属";break;
                  case "7":equip_name = "三相多表位金属";break;
                  case "8":equip_name = "三相多表位非金属";break;
                  default:break;}

              EventDAO.eventUpdate("detect_record", "equip_name", equip_name, "BAR_CODE", str1);
              System.out.println("update equipname success sh276");
              if (str21.equals("")) {
                  System.out.println("tray_no=none");
              } else {
                  Boolean eventexist22 = eventSelect("BARCODE_RELATIONSHIP", "tray_no", str21);
                  if (eventexist22) {
                      System.out.println(str21 + "sh183");
                      EventDAO.eventUpdateBarcodeRE(str1, str21);
                  } else {
                      System.out.println(str21 + "sh186");
                      EventDAO.detect_eventInsertbARCODERE(str1, str21);
                  }
              }
              out.writeUTF("tasksuccess");
              out.flush();
              out.close();
          }
        }
        catch (Exception e){e.printStackTrace();}
            break;
/******存xml数据记录*/
   /* case "test_record":

        System.out.println("test_record");
        out.writeUTF("test_record");
        out.flush();
        out.close();
        String bar_code = xmlApp.xmlHandlemethod(doc,"bar_code");
        Boolean eventexist = eventSelect("DETECT_RECORD", "BAR_CODE", bar_code);
            if (eventexist){
            eventUpdate("DETECT_RECORD", "TEST_RECORD", xml, "BAR_CODE", bar_code);
            System.out.println("update testrecord success");
        }
        else{
            Connection connection = DB.dbcon();
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            StringBuilder test = new StringBuilder();
                System.out.println("unexistandinsert");
            test.append("insert into DETECT_RECORD(ID,TASK_ID,BAR_CODE,TEST_RECORD) values(SQ_COMON_TWO.NEXTVAL,'TESTRECORD',?,?)");
            try {
                preparedStatement = connection.prepareStatement(test.toString());
                preparedStatement.setString(1, bar_code);
                preparedStatement.setString(2, "'"+xml+"'");

                preparedStatement.executeUpdate();

                System.out.println("inserttestrecordsuccess");

            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if(preparedStatement!=null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                DB.CloseAll(connection);
            }
        }
        break;*/
    /******存xml数据记录*/
    case "RecordSave":
try {
  //  String location = xmlApp.xmlHandlemethod(doc, "Location");
    String bar_code = xmlApp.xmlHandlemethod(doc, "bar_code");
    String clob_result = xmlApp.xmlHandlemethod(doc, "clob_result");//取出实验结果
    String equip_categ = search(bar_code);//大类*************************
    String item = xmlApp.xmlHandlemethod(doc, "item");
    String content = contentGetDAO(equip_categ, item);
    Boolean eventexist = eventSelect("DETECT_RECORD", "BAR_CODE", bar_code);
    String itemxml = xmlApp.xmlhead + content + xmlApp.xmlfoot;//拼接xml字符串,现在是result为空的格式
    String itemxmlfinal = xmlApp.content_set(itemxml, clob_result);//赋值result

    if (eventexist) {
        String oldxml = xmlGetDAO(bar_code);//detect_record表里的clob

        if (oldxml.equals("none")) {
            ///////detect_record内无数据****
            try {

                eventUpdateclob(bar_code, itemxmlfinal);//无数据就将itemxmlfinal整个赋值进去
                out.writeUTF("sh266");
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //里面有值 ，           neco
            Document doc2 = DocumentHelper.parseText(oldxml);//将之前的clob数据转换为xml格式
            Element root = doc2.getRootElement();//root
            List<Element> list = root.elements("test_item");//得到test_item节点的集合
            String flag="";
            for(Element test_item:list) {
                // Element test_item=root.element("test_item");//test_item
                String type = test_item.attributeValue("type");//获取子节点属性的值
                String name = test_item.attributeValue("name");//获取子节点属性的值
                //   System.out.println("属性：" + type + name);
                if(name.equals(item ))
            {
                Element item_result = test_item.element("item_result");
                flag=item_result.getText();
                item_result.setText(clob_result);
                //  System.out.println(item_result.getText()+"******************************");
            }
        }
if(flag.equals("")){String[] oldhead=new String[10];String[] newfoot = new String[10];
//该实验没做过，所以里面没有result，因此将整个itemxmlfinal直接拼接进去
    oldhead=oldxml.split("</root>");       newfoot=itemxmlfinal.split("<root>");
    System.out.println(oldhead+"sh404 save+++++++++++++++++++++++++++++++++++++++++++++++");
    System.out.println(newfoot+"sh405 save+++++++++++++++++++++++++++++++++++++++++++++++");
    String final_content_xml = oldhead[0]+"\n"+newfoot[1];
    eventUpdateclob(bar_code,final_content_xml);
    out.writeUTF("success");
    out.flush();
    out.close();
}
else {//该实验做过，直接修改值

    String final_content_xml = doc2.asXML();//修改在sh 287
    eventUpdateclob(bar_code,final_content_xml);
    out.writeUTF("success");
    out.flush();
    out.close();
}

        }
    }
    else {
        out.writeUTF("no binded");
        out.flush();
        out.close();
    }
}
    catch(Exception e){e.printStackTrace();}
    break;

    /*
     *
     * **查询是否存在xml数据记录*/
    case "RecordQuery"://修改
try{
  //  String location = xmlApp.xmlHandlemethod(doc, "Location");
    String bar_code = xmlApp.xmlHandlemethod(doc, "bar_code");
    String item = xmlApp.xmlHandlemethod(doc, "item");////item名
    System.out.println(bar_code+"sh 328");
    Boolean eventexist = eventSelect("DETECT_RECORD", "BAR_CODE", bar_code);
    String finaxml = "";
    if(eventexist){
        String oldxml = xmlGetDAO(bar_code);
        if (oldxml.equals("none")) {
            try {
                //无test_record记录*************
                xmlApp xmlApp2 = new xmlApp();
                xmlApp2.addE("Result","fail");
                String failxml = xmlApp2.document.asXML();
                out.writeUTF(failxml);
                System.out.println("first");
                out.flush();
                out.close();
            } catch (Exception e) {
e.printStackTrace();
            }
        }else {
            System.out.println("sh test_record exit 346");
            //存在test_record记录*************，那么就直接把值取出来，用get方法
            Document document = DocumentHelper.parseText(oldxml);//将clob内数据转换为xml
            Element root = document.getRootElement();
            List<Element> list = root.elements("test_item");
            String flag="";
//取出结果值
            for(Element test_item:list) {
                // Element test_item=root.element("test_item");//test_item
                String type = test_item.attributeValue("type");//获取子节点属性的值
                String name = test_item.attributeValue("name");//获取子节点属性的值
                System.out.println(item+"  "+name+"sh451**************");
                if(name.equals(item ))
                {
                    Element item_result = test_item.element("item_result");
                    flag=item_result.getText();//result
                    System.out.println(flag+"sh457");
                }
            }
            if (flag.equals("")) {
             //无记录***********************没做过实验

                xmlApp xx = new xmlApp();
                xx.addE("Result","fail");
                String failxml = xx.document.asXML();
                out.writeUTF(failxml);
                out.flush();
                out.close();
            }
            else {
                //做过实验*********************************************************************
                xmlApp xx = new xmlApp();  xx.addE("Result",flag);
                String failxml = xx.document.asXML();
                out.writeUTF(failxml);
                out.flush();
                out.close();
            }
        }

    }else {
        out.writeUTF("sh 304 noexit");
        out.flush();
        out.close();

    }
    System.out.println(finaxml+"sh+331");
}
catch (Exception e){e.printStackTrace();}
break;

/*
* */

    case "picture1":
//Connection connection = DB.dbcon();
try {
    String picture = xmlApp.xmlHandlemethod(doc, "picture1");
    String barcode = xmlApp.xmlHandlemethod(doc, "bar_code");
    String task_id=EventDAO.taskidget();
    if(task_id.equals("notask_id")){
        //无task
        out.writeUTF("");
        out.flush();
        out.close();
    }
        else {//有task_id
        FileOutputStream fos = null;
        //获取日期
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;
        System.out.println(year + "年" + month + "月");

        String toDir = "D:\\jlx_image\\"+year + "年" + month + "月"+"\\";   //存储路径

        byte[] buffer = new BASE64Decoder().decodeBuffer(picture);   //对android传过来的图片字符串进行解码

        File destDir = new File(toDir);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        fos = new FileOutputStream(new File(destDir, barcode + "_" + task_id + "_1.jpg"));   //保存图片
        xmlApp.picpath1 = "";
        xmlApp.picpath1 = toDir+ barcode + "_" + task_id + "_1.jpg";
        fos.write(buffer);
        fos.flush();
        fos.close();
        System.out.println("picture554****");
        out.writeUTF("success");
        out.flush();
        out.close();
        System.out.println("picture558*****");
    }
}catch (Exception e){e.printStackTrace();}


        break;
    case "picture2":
//Connection connection = DB.dbcon();
        try {
            String picture = xmlApp.xmlHandlemethod(doc, "picture2");
            String barcode = xmlApp.xmlHandlemethod(doc, "bar_code");
            String task_id=EventDAO.taskidget();
            if(task_id.equals("notask_id")){
                //无task
                out.writeUTF("");
                out.flush();
                out.close();
            }
            else {
                FileOutputStream fos = null;
                //获取日期
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH )+1;
                System.out.println(year + "年" + month + "月");

                String toDir = "D:\\jlx_image\\"+year + "年" + month + "月"+"\\";   //存储路径

                byte[] buffer = new BASE64Decoder().decodeBuffer(picture);   //对android传过来的图片字符串进行解码
                File destDir = new File(toDir);
                if (!destDir.exists()) {
                    destDir.mkdir();
                }
                fos = new FileOutputStream(new File(destDir, barcode + "_" + task_id + "_2.jpg"));   //保存图片
                xmlApp.picpath2 = "";
                xmlApp.picpath2 = toDir + barcode + "_" + task_id + "_2.jpg";
                fos.write(buffer);
                fos.flush();
                fos.close();
                out.writeUTF("success");
                out.flush();
                out.close();

            }
        }catch (Exception e){e.printStackTrace();}
        break;
    case "picture3":
//Connection connection = DB.dbcon();
        try {
            String picture = xmlApp.xmlHandlemethod(doc, "picture3");
            String barcode = xmlApp.xmlHandlemethod(doc, "bar_code");
            String task_id=EventDAO.taskidget();
            if(task_id.equals("notask_id")){
                //无task
                out.writeUTF("");
                out.flush();
                out.close();
            }
            else {
            FileOutputStream fos = null;
                //获取日期
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH )+1;
                System.out.println(year + "年" + month + "月");

                String toDir = "D:\\jlx_image\\"+year + "年" + month + "月"+"\\";   //存储路径

                byte[] buffer = new BASE64Decoder().decodeBuffer(picture);   //对android传过来的图片字符串进行解码
            File destDir = new File(toDir);
            if(!destDir.exists()) {
                destDir.mkdir();
            }
            fos = new FileOutputStream(new File(destDir,barcode+"_"+task_id+"_3.jpg"));   //保存图片
            xmlApp.picpath3="";
            xmlApp.picpath3=toDir+barcode+"_"+task_id+"_3.jpg";
            fos.write(buffer);
            fos.flush();
            fos.close();
                out.writeUTF("success");
                out.flush();
                out.close();
            }
        }catch (Exception e){e.printStackTrace();}
        break;
    case "picture4":
//Connection connection = DB.dbcon();
        try {
            String picture = xmlApp.xmlHandlemethod(doc, "picture4");//picture编码字符串
            String barcode = xmlApp.xmlHandlemethod(doc, "bar_code");
            String task_id=EventDAO.taskidget();
            if(task_id.equals("notask_id")){
                //无task
                out.writeUTF("");
                out.flush();
                out.close();
            }
            else {
            FileOutputStream fos = null;
                //获取日期
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH )+1;
                System.out.println(year + "年" + month + "月");

                String toDir = "D:\\jlx_image\\"+year + "年" + month + "月"+"\\";   //存储路径

                byte[] buffer = new BASE64Decoder().decodeBuffer(picture);   //对android传过来的图片字符串进行解码
            File destDir = new File(toDir);
            if(!destDir.exists()) {
                destDir.mkdir();
            }
            fos = new FileOutputStream(new File(destDir,barcode+"_"+task_id+"_4.jpg"));   //保存图片
            xmlApp.picpath4="";
            xmlApp.picpath4=toDir+barcode+"_"+task_id+"_4.jpg";
            fos.write(buffer);
            fos.flush();
            fos.close();
            //
                out.writeUTF("success");
                out.flush();
                out.close();
            }
        }catch (Exception e){e.printStackTrace();}
        break;
/*
*
* */
    case "Search":
        String leixing = "";
        String finaxml3="";
        try {
        String barcode = xmlApp.xmlHandlemethod(doc,"bar_code");
        String ss = EventDAO.search(barcode);//大类名称****************
switch (ss) {
    case "1"://单相单表位金属
        leixing = "A";
        break;
    case "2"://单相单表位非金属
        leixing = "B";
        break;
    case "3"://单相多表位金属
        leixing = "C";
        break;
    case "4"://单相多表位非金属
        leixing = "D";
        break;
    case "5"://三相单表位金属
        leixing = "E";
        break;
    case "6"://三相单表位非金属
        leixing = "F";
        break;
    case "7"://三相多表位金属
        leixing = "G";
        break;
    case "8"://三相多表位非金属
        leixing = "H";
        break;
        default:
            break;
}
        }
        catch (Exception e){e.printStackTrace();}
        try {
            //发送AB.....
            xmlApp xmlzeze2 = new xmlApp();
            xmlzeze2.addE("equip_categ",leixing );
            finaxml3 = xmlzeze2.document.asXML();
            out.writeUTF(finaxml3);
            out.flush();
            out.close();
        }catch (Exception e){}
        break;
        /*
        * */

    case "update":
        try{
            String bar_code = xmlApp.xmlHandlemethod(doc, "bar_code");
            EventDAO.eventUpdate("DETECT_RECORD","DETECT_STATUS","1","BAR_CODE", bar_code);
            System.out.println("update state(detect_status)success");
            out.writeUTF("update state(detect_status)success");
            out.flush();
            out.close();
        }catch (Exception e){
            System.out.println("sh542");
        }
        break;
/*
* *******************************************************************************************************************
* */
    case "Equery"://查询当前项目和下一个项目
        String finaxml2 = "";
        try{
            String bar_code = xmlApp.xmlHandlemethod(doc, "bar_code");
            Boolean eventexist = eventSelect("DETECT_RECORD", "BAR_CODE", bar_code);
            String shmid = shmidDAO(bar_code);
            if(eventexist){
            String oldxml = xmlGetDAO(bar_code);
            if(oldxml.equals("none")){
                //clob数据不存在
                out.writeUTF("noclob");
                out.flush();
                out.close();}
                else {
                //clob存在
                Document doc2 = DocumentHelper.parseText(oldxml);
                Map<Integer ,String> map1 = new HashMap<Integer,String >();
                int i =0;
                Element root = doc2.getRootElement();
                List<Element> list = root.elements("test_item");
                for(Element test_item:list){
                    String type = test_item.attributeValue("type");//获取子节点属性的值
                    String name = test_item.attributeValue("name");//获取子节点属性的值
                   map1.put(i,name) ;
                   ++i;
                }
             //   System.out.println(map1.get(i)+"i  ++++sh572");
                System.out.println(map1.get(i-1)+"i-1 ++++sh572");
String current_item = map1.get(i-1);
                out.writeUTF(shmid+";"+current_item);//直接发送当前项目
                out.flush();
                out.close();
            }
            }
            else {
                out.writeUTF("nobind");
                out.flush();
                out.close();
            }

        }catch (Exception e){}


break;
    default:
        break;
}
}
catch (DocumentException e){
    e.printStackTrace();

}

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
doPost(request,response);
    }
    /* 调用method
    *
    */
private String test(){
        return "Success";
}
/*
***************Login***************************
*/
private   String Login (String user,String password)
{String result = null;
    try {
        //密码验证结果
        Boolean verifyResult = verifyLogin(user, password);
        if (verifyResult) {
            result =  "Scuess";
        } else {
            result = "Fail";
        }
    }
    catch (Exception e){e.printStackTrace();}
    return result;
}
/*
*
*
     * 验证用户名密码是否正确
     *
     *
     */
    private Boolean verifyLogin(String userName, String password)throws SQLException {
        User user = UserDAO.queryUser(userName);

        //账户密码验证
        return null != user && password.equals(user.getPassword());
    }
}
