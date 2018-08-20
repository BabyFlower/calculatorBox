
package DBManager;

import oracle.sql.CLOB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.text.*;
import java.text.ParseException;
import java.util.*;
import java.util.Date;


/**
 * Created by Tina on 2017/10/29.
 */

public class EventDAO {
    public static void  detect_eventInsert( String bar_code,String equip_categ,String sch_id,java.sql.Date time,String chk
            ,String length,String height,String width,String picpath1,String picpath2,String picpath3,String picpath4,String detect_status,String equip_name
    ,String number,String factory,String location,String postcode,String phone_number,String arrival_date,String task_id) throws SQLException{
        /*若bar_code存在，更新数据，
    bar_code不存在，则插入数据
        * */
        Boolean eventexist = eventSelect("DETECT_RECORD", "BAR_CODE", bar_code);
        if (eventexist) {
try {
    eventUpdate("DETECT_RECORD", "EQUIP_CATEG", equip_categ, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "SCHEME_ID", sch_id, "BAR_CODE", bar_code);
    eventUpdatetesttime("DETECT_RECORD", "TEST_TIME", time, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "CHK_CONC", chk, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "LENGTH", length, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "HEIGHT", height, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "WIDTH", width, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "PICPATH1", picpath1, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "PICPATH2", picpath2, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "PICPATH3", picpath3, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "PICPATH4", picpath4, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "DETECT_STATUS", detect_status, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "EQUIP_NAME", equip_name, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "FACTORYNUMBER", number, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "FACTORY", factory, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "LOCATION", location, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "POSTCODE", postcode, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "PHONE_NUMBER", phone_number, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "ARRIVAL_DATE", arrival_date, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "TASK_ID", task_id, "BAR_CODE", bar_code);
    System.out.println("updatesuccess");
  //  eventUpdate("BARCODE_RELATIONSHIP","BINDED","1","BAR_CODE",bar_code);

}catch (Exception e){}
        } else {

            //获得数据库的连接对象



                Connection connection = DB.getConnection();
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

            //生成SQL代码
            StringBuilder sb = new StringBuilder();
            sb.append("insert into DETECT_RECORD(ID,BAR_CODE,EQUIP_CATEG,SCHEME_ID,TEST_TIME,CHK_CONC,LENGTH,HEIGHT,WIDTH,PICPATH1,PICPATH2,PICPATH3,PICPATH4,DETECT_STATUS,EQUIP_NAME,FACTORYNUMBER,FACTORY,LOCATION,POSTCODE,PHONE_NUMBER,ARRIVAL_DATE,TASK_ID) values(SQ_COMON_TWO.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            try {




                preparedStatement = connection.prepareStatement(sb.toString());
                preparedStatement.setString(1, bar_code);
                preparedStatement.setString(2, equip_categ);
                preparedStatement.setString(3, sch_id);
                preparedStatement.setDate(4, time);//testtime
                preparedStatement.setString(5, chk);
                preparedStatement.setString(6, length);
                preparedStatement.setString(7, height);
                preparedStatement.setString(8, width);
                preparedStatement.setString(9, picpath1);
                preparedStatement.setString(10, picpath2);
                preparedStatement.setString(11, picpath3);
                preparedStatement.setString(12, picpath4);
                preparedStatement.setString(13, detect_status);
                preparedStatement.setString(14, equip_name);
                preparedStatement.setString(15, number);
                preparedStatement.setString(16, factory);
                preparedStatement.setString(17, location);
                preparedStatement.setString(18, postcode);
                preparedStatement.setString(19, phone_number);
                preparedStatement.setString(20, arrival_date);
                preparedStatement.setString(21, task_id);
/******add******/
                preparedStatement.executeUpdate();
                System.out.println("insertsuccess");
                eventUpdate("BARCODE_RELATIONSHIP","BINDED","1","BAR_CODE",bar_code);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if(preparedStatement!=null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                DB.closeConnection();
                DB.CloseAll(connection);
            }
        }
    }
/*
*
* */
//获取除test_time（date）之外的所有字段
    public  static String bindinfo(String barcode,String ziduan)throws SQLException{
        //获得数据库的连接对象
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //生成SQL代码
        StringBuilder sb = new StringBuilder();
        //sb.append("select TEST_RECORD from  DETECT_RECORD where  BAR_CODE="+" '"+bar_code+"'");
        sb.append("select * from  DETECT_RECORD where  BAR_CODE="+" '"+barcode+"'");
        /*sb.append("select * from  DETECT_RECORD where  BAR_CODE"+" '"+bar_code+"'");*/
        String xml = "none";
        try{
            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                try {
                    xml= resultSet.getString(ziduan);
                    System.out.println(xml+"bindinfo edao136");
                }catch (Exception e){e.printStackTrace();}
            }
            else {
                xml="none";
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            //释放资源
            if(resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            DB.closeConnection();
            DB.CloseAll(connection);
        }


        return xml;
    }

/*获取test_time（date）值
* */
//获取除test_time（date）之外的所有字段
public  static String bindinfodate(String barcode)throws SQLException{
    //获得数据库的连接对象
    Connection connection = DB.getConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    //生成SQL代码
    StringBuilder sb = new StringBuilder();
    //sb.append("select TEST_RECORD from  DETECT_RECORD where  BAR_CODE="+" '"+bar_code+"'");
    sb.append("select * from  DETECT_RECORD where  BAR_CODE="+" '"+barcode+"'");
        /*sb.append("select * from  DETECT_RECORD where  BAR_CODE"+" '"+bar_code+"'");*/
    String xml = "";
    try{
        preparedStatement = connection.prepareStatement(sb.toString());
        resultSet= preparedStatement.executeQuery();
        if(resultSet.next()){
            try {
                java.sql.Date date= resultSet.getDate("test_time");//取出sqldate型
                java.util.Date date1 = new java.util.Date(date.getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
              xml=formatter.format(date1);

                System.out.println(xml+"edao196 time");
            }catch (Exception e){e.printStackTrace();}
        }
        else {
            xml="none";
        }
    }
    catch (SQLException e){
        e.printStackTrace();
    }
    finally {
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //释放资源
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        DB.closeConnection();
        DB.CloseAll(connection);
    }


    return xml;
}

/**/
    public static void  detect_eventInsertbARCODERE( String bar_code,String tray_no) throws SQLException{

            Connection connection = DB.getConnection();
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
          //   int tray_noInt=0;
/*            try {
                tray_noInt=Integer.parseInt(tray_no);
            }
catch (Exception e){
                e.printStackTrace();
    System.out.println("Edao 113");
}*/
            //生成SQL代码
            StringBuilder sb = new StringBuilder();
            sb.append("insert into Barcode_relationship(TRAY_ID,BAR_CODE,TRAY_NO,BINDED,POS_NO) values(SQ_COMON_TWO.NEXTVAL,?,?,?,?)");
            //SQ_COMON_TWO.NEXTVAL
            try {
                preparedStatement = connection.prepareStatement(sb.toString());
                preparedStatement.setString(1, bar_code);
                preparedStatement.setString(2, tray_no);
                preparedStatement.setInt(3, 1);
                preparedStatement.setInt(4, 1);

/******add******/
                preparedStatement.executeUpdate();
                System.out.println(tray_no+"edao144");
                System.out.println("insertsuccess");

            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if(preparedStatement!=null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                DB.closeConnection();
                DB.CloseAll(connection);
            }
        }

/*
    * delete_eventDelete***************************
    * */

    public static void  delete_eventDelete(String table) throws SQLException{

        //获得数据库的连接对象
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //生成SQL代码
        StringBuilder sb = new StringBuilder();
        sb.append("delete * from "+table);


        try {
            preparedStatement = connection.prepareStatement(sb.toString());
            preparedStatement.executeUpdate();


/******add******/

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            DB.closeConnection();
            DB.CloseAll(connection);
        }
    }


/*
    * eventSelect根据某字段值查询某条记录是否存在（非具体列值）
    * */

    public static Boolean  eventSelect(String table,String col1,String value1) throws SQLException{
        //获得数据库的连接对象
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //生成SQL代码
        StringBuilder sb = new StringBuilder();
        sb.append("select * from "+table+" where "+col1+"= '"+value1+"'");
        boolean exit =false;
        try{
            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet= preparedStatement.executeQuery();
            int rowCount = 0;
            System.out.println("startexit");
            while(resultSet.next()) {
                rowCount++;
                System.out.println("rowcount");
            }
            if (rowCount>0){
                exit=true;
                System.out.println("exittrue");
            }else {
                System.out.println("exitfail");
            }
           // exit =preparedStatement.execute();
        }catch (SQLException e){
            System.out.println("exiterror");
            e.printStackTrace();
        }
        finally {

            //释放资源
            if(resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        DB.closeConnection();
        return  exit;
    }

/*
    * eventUpdate
    * */

    public static void  eventUpdate(String table,String col1,String value1,String col2,String value2) throws SQLException{
        //获得数据库的连接对象
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;


        //生成SQL代码
        StringBuilder sb = new StringBuilder();
        System.out.println("1111111");
        sb.append("update "+table+" set "+col1+" = '"+value1+"' where "+col2+" = '"+value2+"'");
        try{
            System.out.println("22222221");



            preparedStatement = connection.prepareStatement(sb.toString());
            preparedStatement.executeUpdate();
            System.out.println("update sss");
        }
        catch (SQLException e){
            System.out.println("update 222");
            e.printStackTrace();
        }
        finally {
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            DB.closeConnection();
            DB.CloseAll(connection);
        }
    }


    /*
    *
    * */
    public static void  eventUpdatetesttime(String table,String col1,java.sql.Date value1,String col2,String value2) throws SQLException{
        //获得数据库的连接对象
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;


        //生成SQL代码
        StringBuilder sb = new StringBuilder();
        System.out.println("1111111");
        sb.append("update "+table+" set "+col1+" =  to_date('"+value1+"','yyyy-mm-dd hh24:mi:ss')  where "+col2+" = '"+value2+"'");
        try{
            System.out.println("22222221");



            preparedStatement = connection.prepareStatement(sb.toString());
            preparedStatement.executeUpdate();
            System.out.println("update sss");
        }
        catch (SQLException e){
            System.out.println("update 222");
            e.printStackTrace();
        }
        finally {
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            DB.closeConnection();
            DB.CloseAll(connection);
        }
    }

    public static void  eventUpdateclob(String barcode,String clobtext) throws SQLException{
        //获得数据库的连接对象
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;


        //生成SQL代码
        StringBuilder sb = new StringBuilder();
        System.out.println("1111111");
        sb.append("update DETECT_RECORD set TEST_RECORD=? where BAR_CODE=?");
        try{
            CLOB clob   = oracle.sql.CLOB.createTemporary(connection, false,oracle.sql.CLOB.DURATION_SESSION);
            clob.setString(1L, clobtext);


            preparedStatement = connection.prepareStatement(sb.toString());
            preparedStatement.setClob(1, clob);
            preparedStatement.setString(2,barcode);
            preparedStatement.executeUpdate();
            System.out.println("update clobssss");
        }
        catch (SQLException e){
            System.out.println("update clob222");
            e.printStackTrace();
        }
        finally {
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            DB.closeConnection();
            DB.CloseAll(connection);
        }
    }
/*
*
* */
public static void  eventUpdateBarcodeRE(String barcode,String tray_no) throws SQLException{
    //获得数据库的连接对象
    Connection connection = DB.getConnection();
    PreparedStatement preparedStatement = null;


    //生成SQL代码
    StringBuilder sb = new StringBuilder();
    System.out.println("1111111");
   // sb.append("update "+table+" set "+col1+" = '"+value1+"' where "+col2+" = '"+value2+"'");
    sb.append("update Barcode_relationship  set bar_code='"+barcode+"'，binded=1,pos_no=1 where tray_no='"+tray_no+"'");
    try{

        preparedStatement = connection.prepareStatement(sb.toString());
        preparedStatement.executeUpdate();
        System.out.println("update sss");
    }
    catch (SQLException e){
        System.out.println("update 222");
        e.printStackTrace();
    }
    finally {
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        DB.closeConnection();
        DB.CloseAll(connection);
    }
}

/*
*
* */
public  static String taskidget()throws SQLException{
    //获得数据库的连接对象
    Connection connection = DB.getConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    //生成SQL代码
    StringBuilder sb = new StringBuilder();
    //sb.append("select TEST_RECORD from  DETECT_RECORD where  BAR_CODE="+" '"+bar_code+"'");
    sb.append("select task_id from d_task_config d where d.status_code='1'");
        /*sb.append("select * from  DETECT_RECORD where  BAR_CODE"+" '"+bar_code+"'");*/
    String xml = "";
    try{
        preparedStatement = connection.prepareStatement(sb.toString());
        resultSet= preparedStatement.executeQuery();
        if(resultSet.next()){
                       /* xml = resultSet.toString();*/
            System.out.println("taskidget");
            try {
            xml= resultSet.getString("TASK_ID");
                System.out.println(xml);
            }catch (Exception e){e.printStackTrace();}
        }
        else {
            xml="notask_id";
        }
    }
    catch (SQLException e){
        e.printStackTrace();
    }
    finally {
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //释放资源
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        DB.closeConnection();
        DB.CloseAll(connection);
    }


    return xml;
}


    //*******************************************获取xml
    public  static String xmlGetDAO(String bar_code)throws SQLException{
        //获得数据库的连接对象
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //生成SQL代码
        StringBuilder sb = new StringBuilder();
        sb.append("select TEST_RECORD from  DETECT_RECORD where  BAR_CODE="+" '"+bar_code+"'");
        /*sb.append("select * from  DETECT_RECORD where  BAR_CODE"+" '"+bar_code+"'");*/
        String xml = "";
        try{
            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                       /* xml = resultSet.toString();*/
                System.out.println("hello");
                try {
                    Clob clob= (Clob) resultSet.getClob("TEST_RECORD");

                    xml = ClobToString(clob);
                    System.out.println(xml);
                }catch (Exception e){e.printStackTrace();}

            }
            else {
                xml="none";
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            //释放资源
            if(resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
DB.closeConnection();
            DB.CloseAll(connection);
        }


return xml;
}
/*
* scmhm_id
* */
//*******************************************获取xml
public  static String shmidDAO(String bar_code)throws SQLException{
    //获得数据库的连接对象
    Connection connection = DB.getConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    //生成SQL代码
    StringBuilder sb = new StringBuilder();
    sb.append("select SCHEME_ID from  DETECT_RECORD where  BAR_CODE="+" '"+bar_code+"'");
        /*sb.append("select * from  DETECT_RECORD where  BAR_CODE"+" '"+bar_code+"'");*/
    String xml = "";
    try{
        preparedStatement = connection.prepareStatement(sb.toString());
        resultSet= preparedStatement.executeQuery();
        if(resultSet.next()){
                       /* xml = resultSet.toString();*/
            System.out.println("hello");
            try {
                xml =  resultSet.getString("SCHEME_ID");

                System.out.println(xml);
            }catch (Exception e){e.printStackTrace();}

        }
        else {
            xml="none";
        }
    }
    catch (SQLException e){
        e.printStackTrace();
    }
    finally {
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //释放资源
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        DB.closeConnection();
        DB.CloseAll(connection);
    }


    return xml;
}
/*
* 绑定时获取的smid****************************
* */
public  static Map bangdingshmidDAO(String equip_categ)throws SQLException{
    //获得数据库的连接对象
    Connection connection = DB.getConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    int ct = Integer.parseInt(equip_categ);
    //生成SQL代码
    StringBuilder sb = new StringBuilder();
   // sb.append("select  from  DETECT_RECORD where  BAR_CODE="+" '"+bar_code+"'");
    sb.append("select d.id from detect_scheme_inf  d where d.scheme_type ='"+ct+"'");
        /*sb.append("select * from  DETECT_RECORD where  BAR_CODE"+" '"+bar_code+"'");*/
    int  xml =0000;
  Map<Integer,Integer> map=new HashMap<>();
    int i=0;
    try{
        preparedStatement = connection.prepareStatement(sb.toString());
        resultSet= preparedStatement.executeQuery();
        if(resultSet.next()){
                       /* xml = resultSet.toString();*/
            System.out.println("hello");
            try {

                xml =  resultSet.getInt("id");
                System.out.println(xml+"edao631*****");
                map.put(i,xml );
              //  System.out.println(xml);
                i++;
            }catch (Exception e){e.printStackTrace();}

        }
        else {
            xml=0000;
        }
    }
    catch (SQLException e){
        e.printStackTrace();
    }
    finally {
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //释放资源
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        DB.closeConnection();
        DB.CloseAll(connection);
    }


    return map;
}
    //*******************************************获取大类
    public  static String search(String bar_code)throws SQLException{
        //获得数据库的连接对象
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //生成SQL代码
        StringBuilder sb = new StringBuilder();
        sb.append("select * from  DETECT_RECORD where  BAR_CODE="+" '"+bar_code+"'");
        /*sb.append("select * from  DETECT_RECORD where  BAR_CODE"+" '"+bar_code+"'");*/
        String xml = "";
        try{
            preparedStatement = connection.prepareStatement(sb.toString());
            resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                try {
                    xml=  resultSet.getString("EQUIP_CATEG");
                    System.out.println(xml);
                }catch (Exception e){e.printStackTrace();}
            }
            else {
                xml="none";
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            //释放资源
            if(resultSet!=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            DB.closeConnection();
            DB.CloseAll(connection);
        }


        return xml;
    }
////获取content，根据equip_categ和testitemname可以唯一确定
public  static String contentGetDAO(String equip_categ,String item)throws SQLException{
    //获得数据库的连接对象
    Connection connection = DB.getConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    //生成SQL代码
    StringBuilder sb = new StringBuilder();
    sb.append("select content from  device_item  where  equip_categ="+" '"+equip_categ+"' and testitemname ="+" '"+item+"'");
        /*sb.append("select * from  DETECT_RECORD where  BAR_CODE"+" '"+bar_code+"'");*/
    String xml = "";
    try{
        preparedStatement = connection.prepareStatement(sb.toString());
        resultSet= preparedStatement.executeQuery();
        if(resultSet.next()){
                       /* xml = resultSet.toString();*/
            System.out.println("hello");
            try {

                xml=resultSet.getString("content");
                System.out.println(xml);
            }catch (Exception e){e.printStackTrace();}

        }
        else {
            xml="none";
        }
    }
    catch (SQLException e){
        e.printStackTrace();
    }
    finally {
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //释放资源
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        DB.closeConnection();
        DB.CloseAll(connection);
    }


    return xml;
}




    private static String ClobToString(Clob clob) throws SQLException, IOException {
                     String reString = "";
                   if(clob==null) return "none";

                     Reader is = clob.getCharacterStream();// 得到流
                     BufferedReader br = new BufferedReader(is);
                    String s = br.readLine();
                     StringBuffer sb = new StringBuffer();
                     // 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
                     while (s != null) {
                            sb.append(s);
                             s = br.readLine();
                        }
                     reString = sb.toString();if(br!=null){br.close();}if(is!=null){is.close();}
                     return reString;
                }
}

