package DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Created by Tina on 2017/10/29.*/


public class UserDAO {

/*
     * 查询给定用户名的用户的详细信息
     *
     * @param userName 给定的用户名
     * @return 查询到的封装了详细信息的User对象
     */

    public static User queryUser(String userName) throws SQLException{
        //获得数据库的连接对象
        Connection connection = DB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //生成SQL代码
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM USER WHERE USERNAME=?");

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, userName);

            resultSet = preparedStatement.executeQuery();
            User user = new User();


            if (resultSet.next()) {
                //存在use*************
                user.setUserName(resultSet.getString("USER"));
                user.setPassword(resultSet.getString("PASSWORD"));
                return user;
            } else {
                //否则返回null**************************
                return null;
            }
        } catch (SQLException ex) {
            // Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return null;
        } finally {
            DB.closeConnection();
            DB.CloseAll(connection);
        }
    }
}
