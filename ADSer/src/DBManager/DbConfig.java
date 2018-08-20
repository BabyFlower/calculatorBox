package DBManager;

/**
 * Created by Tina on 2017/11/22.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
/**
 * 数据库访问配置文件各参数的获取
  *
   *
   */
public class DbConfig {
    //数据库及server配置文件路径
    private static final String ACTIONPATH = "DBconfig.properties";
    private static DbConfig instance=null;
    private String db_username=null;
    private String db_password=null;
    private String db_name=null;
    private String ip=null;
    private DbConfig(){}
    public String getDb_username() {
        return db_username;
    }
    public String getDb_password() {
        return db_password;
    }
    public String getDb_name() {
        return db_name;
    }
    public String getIp() {
        return ip;
    }
    public static DbConfig getInstance(){if(instance==null){instance= new DbConfig().getNewDbConfig();}
        return instance;}
    /*
    * 获取配置信息**************important
    * */
    private DbConfig getNewDbConfig(){
        DbConfig dc=new DbConfig();
        Properties prop = new Properties();
        String path=null;
        FileInputStream fis=null;
        try {
            path = DbConfig.class.getClassLoader().getResource("").toURI().getPath();//src***
            //path = DbConfig.class.getResource("").toURI().getPath();//DBManager*****
            System.out.println(path+"here");
          fis = new FileInputStream(new File(path + ACTIONPATH));
            prop.load(fis);
            dc.db_username=prop.getProperty("db_username");
            dc.db_password=prop.getProperty("db_password");
            dc.db_name=prop.getProperty("db_name");
            dc.ip=prop.getProperty("ip");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


        return dc;
    }
}
