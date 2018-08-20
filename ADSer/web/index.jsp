<%@ page import="java.net.URL" %>
<%@ page import="java.net.URLConnection" %>
<%@ page import="org.dom4j.DocumentHelper" %>
<%@ page import="java.io.DataOutputStream" %>
<%@ page import="java.io.DataInputStream" %>
<%@ page import="DBManager.EventDAO" %>
<%--
  Created by IntelliJ IDEA.
  User: Tina
  Date: 2017/10/29
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

%>
<html>
  <head>
    <title>数据库测试</title>
  </head>
  <body>
<div style="color:#2632ff;font-size: 24px">
  <%
    URL url = new java.net.URL("http://localhost:8080/ADSer/ServletHandle");
            /* URL url = new java.net.URL("http://localhost:8080/ServiceTest/MeeServlet");*/
    URLConnection con = url.openConnection();
    con.setUseCaches(false);
    con.setDoOutput(true);
    con.setDoInput(true);
    con.setReadTimeout(10000);
con.setConnectTimeout(10000);
try{
    org.dom4j.Document document = DocumentHelper.createDocument();
    org.dom4j.Element root = document.addElement("一级root");
    // root.addAttribute("version", "1.0");

    org.dom4j.Element ss = root.addElement("二级root");
    org.dom4j.Element title = ss.addElement("Method");
    title.setText("dbtest");

    String xml = document.asXML();

    StringBuilder sb = new StringBuilder();
    sb.append(xml);
                /*发送*/
    DataOutputStream dataOut = new DataOutputStream(con.getOutputStream());
    dataOut.writeUTF(sb.toString());
    dataOut.flush();
    dataOut.close();
try {
             /*获取服务器端返回信息*/
    DataInputStream in =new DataInputStream(con.getInputStream());
    // int str = in.readInt();
    String stringValue = in.readUTF();
    in.close();
    System.out.println(stringValue);

out.write(stringValue+"代表数据库连接成功");


}
catch (Exception e){
    out.write("11代表数据库连接失败");
}
}catch (Exception e
){}
finally{
out.close();
}

  %>
  </body>
  </div>
</html>

