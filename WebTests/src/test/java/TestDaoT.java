import dao.TestDao;
import model.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Created by Elizaveta on 21.05.2016.
 */
public class TestDaoT {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/webtests";
    private static final String user = "root";
    private static final String password = "root";

    // JDBC variables for opening and managing connection
    private static Connection con;

    public static void main(String args[]) throws SQLException {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            Test test = TestDao.getTestById(con,"1");
            System.out.println(test.getTitle());
            System.out.println(LocalDateTime.now());
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            con.close();
        }
    }
}
