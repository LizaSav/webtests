import dao.PersonDao;
import model.Person;

import java.sql.*;

/**
 * Created by Elizaveta on 20.05.2016.
 */
public class PersonDaoT {
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

            Person per = PersonDao.getPersonByLogin(con, "qwerty");
            System.out.println(per.getId());
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
