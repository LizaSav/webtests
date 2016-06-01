package servlets;

import helperClasses.DataChecking;
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

/** Проверка правильность заполнения формы регистрации и добавление пользователя в базу данных
 *
 */
public class AddUser extends Dispatcher {
    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;


    private static final Logger log= Logger.getLogger(AddUser.class);

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        if (request.getParameter("save")!=null) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("user");
            String password = request.getParameter("password");
            String address = request.getParameter("address");
            if (!(DataChecking.checkOneWord(firstName) && DataChecking.checkOneWord(lastName) && DataChecking.checkOneWord(email) && DataChecking.checkPassword(password))) {
                request.getRequestDispatcher("/errorRegistrationDataInvalid.jsp").forward(request, response);
                log.info("Failed to register new user");
            } else {
                model.Person newPerson = null;
                try {
                    Connection con = (Connection) request.getSession(true).getAttribute("connection");
                    if (con == null) {
                        con = dataSource.getConnection();
                        request.getSession().setAttribute("connection", con);
                    }
                    newPerson = dao.PersonDao.addPerson(con, firstName, lastName, email, password, address);
                } catch (SQLException e) {
                    log.error("Can not create connection for add person", e);
                }
                if (newPerson != null) {
                    request.getSession().setAttribute("user", newPerson);
                    log.info("User with id=" + ((Person) request.getSession(true).getAttribute("user")).getId() + "has been successfully registered");
                    this.forward("/successRegistration.jsp", request, response);
                } else {
                    this.forward("/errorRegistration.jsp", request, response);
                }
            }
            }else if (request.getParameter("cancel") != null) {
                this.forward("/login.jsp", request, response);
            }

    }
}