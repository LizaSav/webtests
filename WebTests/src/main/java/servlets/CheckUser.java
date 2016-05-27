package servlets;

import model.Person;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/** Проверяет правильность введённого пользователем логина и пароля
 *
 */

public class CheckUser extends Dispatcher {

    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;

    private static final Logger log= Logger.getLogger(CheckUser.class);

    public String getServletInfo(){
        return "Registration servlet";
    }

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        model.Person user = null;
        try {
            Connection con=(Connection) request.getSession(true).getAttribute("connection");
            if(con==null){
                con =dataSource.getConnection();
                request.getSession().setAttribute("connection", con);
            }
            user = dao.PersonDao.getPersonByLogin(con,(request.getParameter("user")));
        } catch (SQLException e) {
            log.error("Can not close connection for check user password and login", e);
        }
        if (user == null){
            this.forward("/registration.jsp", request, response);
        } else {
            if (!user.getPassword().equals(request.getParameter("password"))){
                this.forward("/errorRegistration.jsp", request, response);
            } else {
                request.getSession(true).setAttribute("user", user);
                if (user.isPermission())request.getSession(true).setAttribute("creator","true");
                request.getSession(true).setAttribute("isLogged","true");
                log.info("User with id="+ ((Person)request.getSession(true).getAttribute("user")).getId()+"logged in");
                this.forward("/index.jsp", request, response);
            }
        }
    }

}