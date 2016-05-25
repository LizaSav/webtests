package listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Elizaveta on 23.05.2016.
 */
public class connectionClose implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        if(se.getSession().getAttribute("connection")!=null){
            try {
                ((Connection)se.getSession().getAttribute("connection")).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
