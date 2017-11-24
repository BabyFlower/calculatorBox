package Main;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import DBManager.*;
import org.dom4j.*;
import org.dom4j.io.*;
import static DBManager.EventDAO.eventSelect;
import static DBManager.EventDAO.eventUpdate;
import static DBManager.EventDAO.xmlGetDAO;
/*
 * Created by Tina on 2017/10/29.*/


public class ServletHandle extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("connect success");
DataInputStream in = new DataInputStream(request.getInputStream());
try {

String xml = in.readUTF();
    System.out.println(xml);
//SAXReader reader = new SAXReader();
 //   StringBuilder recordxml= new StringBuilder(xml);
Document doc = DocumentHelper.parseText(xml);
String method = xmlApp.xmlHandlemethod(doc,"Method");
DataOutputStream out = new DataOutputStream(response.getOutputStream());
/* ******************调用method******/
switch (method){
    case "test":
        System.out.println("test");
        out.writeUTF("11");
        out.flush();
        out.close();
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
            String str1 = xmlApp.xmlHandlemethod(doc,"bar_code");
            String str2 = xmlApp.xmlHandlemethod(doc,"equip_categ");
            String str3 = xmlApp.xmlHandlemethod(doc,"scheme_id");
            String str4 = xmlApp.xmlHandlemethod(doc,"test_time");
            String str5 = xmlApp.xmlHandlemethod(doc,"chk_conc");
            String str6 = xmlApp.xmlHandlemethod(doc,"length");
            String str7 = xmlApp.xmlHandlemethod(doc,"height");
            String str8 = xmlApp.xmlHandlemethod(doc,"width");
            String str9 = xmlApp.xmlHandlemethod(doc,"picpath1");
            String str10 = xmlApp.xmlHandlemethod(doc,"picpath2");
            String str11 = xmlApp.xmlHandlemethod(doc,"picpath3");
            String str12 = xmlApp.xmlHandlemethod(doc,"picpath4");
            String str13 = xmlApp.xmlHandlemethod(doc,"DETECT_STATUS");
            String str14 = xmlApp.xmlHandlemethod(doc,"equip_name");
            String str15 = xmlApp.xmlHandlemethod(doc,"number");
            String str16 = xmlApp.xmlHandlemethod(doc,"factory");
            String str17 = xmlApp.xmlHandlemethod(doc,"location");
            String str18 = xmlApp.xmlHandlemethod(doc,"postcode");
            String str19 = xmlApp.xmlHandlemethod(doc,"phone_number");
            String str20 = xmlApp.xmlHandlemethod(doc,"arrival_date");
      //  System.out.println(str1+str2+str3+str4+str5+str6+str7+str8+str9+str10+str11+str12+str13);
        out.writeUTF("success");
        out.flush();
        out.close();
        EventDAO.detect_eventInsert(str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13,str14,str15,str16,str17,str18,str19,str20);}
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
    String location = xmlApp.xmlHandlemethod(doc, "Location");
    String bar_code = xmlApp.xmlHandlemethod(doc, "bar_code");
    Boolean eventexist = eventSelect("DETECT_RECORD", "BAR_CODE", bar_code);
   if(eventexist) {
       String oldxml = xmlGetDAO(bar_code);
       if (oldxml.equals("none")) {
           try {
               eventUpdate("DETECT_RECORD", "BAR_CODE", bar_code, "TEST_RECORD", xml);
           } catch (Exception e) {e.printStackTrace();}
       } else {
           String[] ss = new String[10];
           String[] ss2 = new String[10];
           String[] ss3 = new String[10];
           String[] ss4 = new String[10];
           String oldxml1 = "";
           String oldxml2 = "";
           String finalxml = "";
           try {
               ss = oldxml.split("<" + location + ">");
               oldxml1 = ss[0];
               oldxml2 = ss[1];
           } catch (Exception e) {
               oldxml2 = "none";
           }

           if (oldxml2.equals("none")) {
//无记录
               ss3 = oldxml.split("</Root>");
               ss4 = xml.split("<Root>");
               finalxml = ss3[0] + ss4[1];
               eventUpdate("DETECT_RECORD", "TEST_RECORD", finalxml,"BAR_CODE" , bar_code);
           } else {
//存在记录，分割=2
               ss2 = oldxml2.split("</" + location + ">");
               String[] aa = new String[10];
               aa = xml.split("<Root>");
               String[] aa2 = new String[10];
               aa2 = aa[1].split("</Root>");

               finalxml = oldxml1 + aa2[0] + ss2[1];
               eventUpdate("DETECT_RECORD", "TEST_RECORD", finalxml,"BAR_CODE" , bar_code);
           }
           out.writeUTF("me");
           out.flush();
           out.close();
       }
   }
    }
    catch(Exception e){e.printStackTrace();}
    break;

    /*
     *
     * **查询是否存在xml数据记录*/
    case "RecordQuery":
try{
    String location = xmlApp.xmlHandlemethod(doc, "Location");
    String bar_code = xmlApp.xmlHandlemethod(doc, "bar_code");
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
            String[] ss = new String[10];
            String[] ss2 = new String[10];
            String oldxml1 = "";
            String oldxml2 = "";

            try {
                ss = oldxml.split("<" + location + ">");
                oldxml1 = ss[0];
                oldxml2 = ss[1];
            } catch (Exception e) {
                oldxml2 = "none";
            }
            if (oldxml2.equals("none")) {
             //无记录***********************

                xmlApp xx = new xmlApp();
                xx.addE("Result","fail");
                String failxml = xx.document.asXML();
                out.writeUTF(failxml);
                System.out.println("second");
                out.flush();
                out.close();
            }
            else {
                ss2 = oldxml2.split("</" + location + ">");
finaxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +"<Root>"+"<" + location + ">"+ss2[0]+"</" + location + ">"+"</Root>";
                out.writeUTF(finaxml);
                out.flush();
                out.close();
            }
        }

    }
}
catch (Exception e){e.printStackTrace();}
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
    case "单相单表位金属-过盈":
        leixing = "A";
        break;
    case "单相单表位非金属-过盈":
        leixing = "B";
        break;
    case "单相多表位金属-过盈":
        leixing = "C";
        break;
    case "单相多表位非金属-过盈":
        leixing = "D";
        break;
    case "三相单表位金属-过盈 ":
        leixing = "E";
        break;
    case "三相单表位非金属-过盈":
        leixing = "F";
        break;
    case "三相多表位金属-过盈":
        leixing = "G";
        break;
    case "三相多表位非金属-过盈":
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
* *******************************************************************************************************************
* */
    case "Equery":
        String finaxml2 = "";
        try{
            String bar_code = xmlApp.xmlHandlemethod(doc, "bar_code");
            Boolean eventexist = eventSelect("DETECT_RECORD", "BAR_CODE", bar_code);
            if(eventexist){
            String oldxml = xmlGetDAO(bar_code);
            if(oldxml.equals("none")){
                out.writeUTF("");
                out.flush();
                out.close();}
                else {
                Document doc2 = DocumentHelper.parseText(oldxml);
                Element root = doc2.getRootElement();

                xmlApp xmlzeze = new xmlApp();
                String lastname = xmlzeze.LastNode(root);//get lastname

                xmlzeze.addE("Location", lastname);
                finaxml2 = xmlzeze.document.asXML();
                out.writeUTF(finaxml2);
                out.flush();
                out.close();
            }
            }
            else {
                out.writeUTF("");
                out.flush();
                out.close();
            }

        }catch (Exception e){}


break;
    default:
        break;
}
}
catch (DocumentException e){e.printStackTrace();}
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
