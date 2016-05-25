package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Elizaveta on 20.05.2016.
 */
public class LogOut extends Dispatcher {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getSession(true).getAttribute("isLogged")!=null) {
            Connection con = (Connection) request.getSession().getAttribute("connection");
            try {
                con.close();
                request.getSession().invalidate();
            } catch (SQLException e) {
                // логирование
                e.printStackTrace();
            }
        }

        this.forward("/index.jsp",request, response);

    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       doPost(request,response);
    }
    }

