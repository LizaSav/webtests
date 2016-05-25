package dao;

import java.sql.*;

/**
 * Created by Elizaveta on 21.05.2016.
 */
public class TestResultDao {
    public static void addResult(Connection con, int personId, int testId, String answers, String testDate, int mark){
        try (Statement st = con.createStatement()) {
            st.executeUpdate("DELETE FROM test_result WHERE student_id=" + personId+" AND test_id="+testId);
            st.executeUpdate("INSERT INTO test_result (student_id, test_id,answers, test_date, mark) VALUES (" + personId + ","+testId+", '" + answers +
                    "', '" + testDate + "', " + mark+ ")");
        } catch (SQLException e) {
            e.printStackTrace();
            //логирование ошибка подключения
        }
    }
    public static boolean isPassed(Connection con, int studentId, int testId){
        try (ResultSet rs = con.createStatement().executeQuery("SELECT  latest_update, test_date  FROM test_result, test WHERE student_id=" + studentId+" AND test.id="+testId)) {
            if (rs.next()) {
                Timestamp update=rs.getTimestamp("latest_update");
                Timestamp testDate=rs.getTimestamp("test_date");
                return (update.before(testDate))? true:false;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
}
