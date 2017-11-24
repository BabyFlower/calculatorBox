
package DBManager;

import oracle.sql.CLOB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;


/**
 * Created by Tina on 2017/10/29.
 */

public class EventDAO {
    public static void  detect_eventInsert( String bar_code,String equip_categ,String sch_id,String time,String chk
            ,String length,String height,String width,String picpath1,String picpath2,String picpath3,String picpath4,String detect_status,String equip_name
    ,String number,String factory,String location,String postcode,String phone_number,String arrival_date) throws SQLException{
        /*若bar_code存在，更新数据，
    bar_code不存在，则插入数据
        * */
        Boolean eventexist = eventSelect("DETECT_RECORD", "BAR_CODE", bar_code);
        if (eventexist) {
try {


    eventUpdate("DETECT_RECORD", "EQUIP_CATEG", equip_categ, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "SCHEME_ID", sch_id, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "TEST_TIME", time, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "CHK_CONC", chk, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "LENGTH", length, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "HEIGHT", height, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "WIDTH", width, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "PICPATH1", picpath1, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "PICPATH2", picpath2, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "PICPATH3", picpath3, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "PICPATH4", picpath4, "BAR_CODE", bar_code);
    eventUpdate("DETECT_RECORD", "DETECT_STATUS", detect_status, "BAR_CODE", bar_code);
    System.out.println("updatesuccess");
    eventUpdate("BARCODE_RELATIONSHIP","BINDED","1","BAR_CODE",bar_code);

}catch (Exception e){}
        } else {

            //获得数据库的连接对象



                Connection connection = DB.getConnection();
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

            //生成SQL代码
            StringBuilder sb = new StringBuilder();
            sb.append("insert into DETECT_RECORD(ID,TASK_ID,BAR_CODE,EQUIP_CATEG,SCHEME_ID,TEST_TIME,CHK_CONC,LENGTH,HEIGHT,WIDTH,PICPATH1,PICPATH2,PICPATH3,PICPATH4,DETECT_STATUS,EQUIP_NAME,NUMBER,FACTORY,LOCATION,POSTCODE,PHONE_NUMBER,ARRIVAL_DATE) values(SQ_COMON_TWO.NEXTVAL,'inserttest',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            try {
                preparedStatement = connection.prepareStatement(sb.toString());
                preparedStatement.setString(1, bar_code);
                preparedStatement.setString(2, equip_categ);
                preparedStatement.setString(3, sch_id);
                preparedStatement.setString(4, time);
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
        ResultSet resultSet = null;

        //生成SQL代码
        StringBuilder sb = new StringBuilder();
        sb.append("update "+table+" set "+col1+" = '"+value1+"' where "+col2+" = '"+value2+"'");
        try{
            preparedStatement = connection.prepareStatement(sb.toString());
            preparedStatement.executeUpdate();
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
            DB.closeConnection();
            DB.CloseAll(connection);
        }
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

