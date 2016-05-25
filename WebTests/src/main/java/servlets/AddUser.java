package servlets;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AddUser extends Dispatcher {
    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;

    public String getServletInfo(){
        return "Add user servlet";
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("save")!=null){
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("user");
            String password = request.getParameter("password");
            String address = request.getParameter("address");

            model.Person newPerson = null;
            try {
                Connection con=(Connection) request.getSession(true).getAttribute("connection");
                if(con==null){
                    con =dataSource.getConnection();
                    request.getSession().setAttribute("connection", con);
                }
                newPerson = dao.PersonDao.addPerson(con,firstName, lastName, email,password,address);
            } catch (SQLException e) {
                //логирование ошибка подключения
            }
            if (newPerson!=null) {
                request.getSession().setAttribute("user", newPerson);
                this.forward("/successRegistration.jsp", request, response);
            } else {
                this.forward("/errorRegistration.jsp", request, response);
            }
        } else if (request.getParameter("cancel")!=null){
            this.forward("/login.jsp", request, response);
        }
    }
}