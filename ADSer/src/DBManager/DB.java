package DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Tina on 2017/10/29.
 */
public class DB {

    // 定义线程本地变量，每个线程访问它都会获得不同的对象
    // 使用ThreadLocal使一个连接绑定到一个线程上
    private static ThreadLocal<Connection> currentConnection = new ThreadLocal<Connection>();

    private static String username=null;            //用户名
    private static String password=null;            //密码
    private static String dbName=null;                //数据库名称
    private static String ip=null;                        //数据库服务器IP地址

    private static String resourceName=null;                                        //为null时不使用连接池， jdbc/mysql或jdbc/oracle或jdbc/derby
    private static String databaseType = "oracle";
    private static void initParams(){
        username=DbConfig.getInstance().getDb_username();
        password=DbConfig.getInstance().getDb_password();
        dbName=DbConfig.getInstance().getDb_name();
        ip=DbConfig.getInstance().getIp();
    }
//connection方法*****************
public static Connection getConnection() throws SQLException {
    Connection conn = currentConnection.get();
    if (conn == null) {
        if(null==resourceName){
          if("oracle".equals(databaseType.toLowerCase())){
                conn = getOracleConnection();
            }else{
                System.out.println("在 JdbcConnection.java 中数据库类型没有设置");
                throw new SQLException("数据库类型未设置");
            }
        }
        else{
        }
        currentConnection.set(conn);
    }
    return conn;
}
    /**
     63      * 关闭Oracle数据库连接
     64      * @throws SQLException
     65 */
    public static void closeConnection() throws SQLException{
        Connection conn = currentConnection.get();
        if(!(conn==null)) {
            conn.close();}
            currentConnection.set(null);

    }

    //获得Oracle数据库连接
    private static Connection getOracleConnection(){
       initParams();
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();    //加载驱动
            //    conn= DriverManager.getConnection("jdbc:oracle:thin:@"+"localhost"+":1521:"+"orcl","admin","admin");
             conn= DriverManager.getConnection("jdbc:oracle:thin:@"+ip+":1521:"+dbName,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Oracle驱动没找到");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //connection方法*****************
   /* public static Connection dbcon (){
        Connection con = null;
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "admin";
        String password = "admin";
        try{
          Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            con = DriverManager.getConnection(
                    url,user, password);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(con);
        return con;
    }*/
    //**********************close******************************************//
    public static void CloseAll(Connection con){
        try{
            if (con !=null){
                con.close();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}