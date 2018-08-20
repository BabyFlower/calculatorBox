package example;

import DBManager.EventDAO;
import DBManager.User;
import DBManager.UserDAO;
import Main.xmlApp;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Tina on 2017/10/29.
 */
public class HelloWorld extends javax.servlet.http.HttpServlet {

  protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
    DataInputStream in = new DataInputStream(request.getInputStream());
    try {

      String xml = in.readUTF();
//SAXReader reader = new SAXReader();
      Document doc = DocumentHelper.parseText(xml);
      String method = xmlApp.xmlHandlemethod(doc,"Method");
      DataOutputStream out = new DataOutputStream(response.getOutputStream());
/* ******************调用method******/

      switch (method){
        case "test":
          out.writeUTF(test());
          out.flush();
          out.close();
          break;
        case "Login":
          String user = xmlApp.xmlHandlemethod(doc,"username");
          String password = xmlApp.xmlHandlemethod(doc,"password");
          out.writeUTF(Login(user,password));
          out.flush();
          out.close();
          break;
        case  "Insert":
          String str1 = xmlApp.xmlHandlemethod(doc,"para1");
          String str2 = xmlApp.xmlHandlemethod(doc,"para2");
          String str3 = xmlApp.xmlHandlemethod(doc,"para3");
          String str4 = xmlApp.xmlHandlemethod(doc,"para4");
          String str5 = xmlApp.xmlHandlemethod(doc,"para5");
          String str6 = xmlApp.xmlHandlemethod(doc,"para6");
          String str7 = xmlApp.xmlHandlemethod(doc,"para7");
          String str8 = xmlApp.xmlHandlemethod(doc,"para8");
          String str9 = xmlApp.xmlHandlemethod(doc,"para9");
          String str10 = xmlApp.xmlHandlemethod(doc,"para10");
          String str11 = xmlApp.xmlHandlemethod(doc,"para11");
          String str12 = xmlApp.xmlHandlemethod(doc,"para12");
          String str13 = xmlApp.xmlHandlemethod(doc,"para13");
        //  EventDAO.detect_eventInsert(str1,str2,str3,str4,str5,str6,str7,str8,str9,str10,str11,str12,str13);
          break;

/******存xml数据记录*/
        case "test_record":
        /*调method方法
        */

          out.flush();
          out.close();
          break;
        default:
          out.flush();
          out.close();
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
    return "Scuess";
  }
/*
***************Login***************************
*/

  private   String Login (String user,String password)
  {
    String result = null;
    try {
      //密码验证结果
      Boolean verifyResult = verifyLogin(user, password);
      if (verifyResult) {
        result =  "Scuess";
      } else {
        result = "Fail";
      }
    }
    catch (Exception e){
      e.printStackTrace();
    }
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
