package servlets.testsEditor;

import dao.TestDao;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Elizaveta on 22.05.2016.
 */
public class DeleteQuestion extends HttpServlet{
    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("yes")!=null){
            try {
                Connection con=(Connection) request.getSession(true).getAttribute("connection");
                if(con==null){
                    con =dataSource.getConnection();
                    request.getSession().setAttribute("connection", con);
                }
                TestDao.deleteQuestion(con, Integer.parseInt(request.getParameter("testId")),Integer.parseInt(request.getParameter("questionNumber")));
                request.getRequestDispatcher("/testsCreator/mytests.jsp").forward(request,response);
            } catch (SQLException e) {
                //логирование ошибка подключения
            }
        }
        else request.getRequestDispatcher("/index.jsp").forward(request,response);
    }
}
