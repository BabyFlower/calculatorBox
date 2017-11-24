package example;

import DBManager.DbConfig;
import DBManager.EventDAO;
import DBManager.User;
import DBManager.UserDAO;
import Main.xmlApp;
import com.sun.org.apache.xalan.internal.utils.XMLSecurityPropertyManager;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Tina on 2017/10/29.
 */
public class HelloWorld extends javax.servlet.http.HttpServlet {


  public static void main(String args[]) {
    String path = null, path2=null;
    FileInputStream fis = null;
    String ACTIONPATH = "DBconfig.properties";
    Properties prop = new Properties();
    try {
      //  path = DbConfig.class.getResource("").toURI().getPath();
      path2 = DbConfig.class.getResource("").toURI().getPath();
      System.out.println(path2);
      fis = new FileInputStream(new File(path2 + ACTIONPATH));
      prop.load(fis);

      //  System.out.println(path);


    } catch (Exception e) {
      System.out.println("fail");
      e.printStackTrace();
    }
  }
}


