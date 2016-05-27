package servlets;

import model.Person;
import org.apache.log4j.Logger;

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
    private static final Logger log= Logger.getLogger(LogOut.class);


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        if(request.getSession(true).getAttribute("isLogged")!=null) {
            Connection con = (Connection) request.getSession().getAttribute("connection");
            try {
                con.close();
                log.info("User with id="+ ((Person)request.getSession(true).getAttribute("user")).getId()+"logged out");
                request.getSession().invalidate();

            } catch (SQLException e) {
                log.error("Can not close connection after log out", e);
            }
        }

        this.forward("/main.jsp",request, response);

    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       doPost(request,response);
    }
    }

