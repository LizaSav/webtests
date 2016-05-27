package servlets.testsEditor;

import dao.TestDao;
import org.apache.log4j.Logger;

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
 * Created by Elizaveta on 23.05.2016.
 */
public class DeleteTest extends HttpServlet {
    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;

    private static final Logger log= Logger.getLogger(DeleteTest.class);
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        if(request.getParameter("yes")!=null){
            try {
                Connection con=(Connection) request.getSession(true).getAttribute("connection");
                if(con==null){
                    con =dataSource.getConnection();
                    request.getSession().setAttribute("connection", con);
                }
                TestDao.deleteTest(con, Integer.parseInt(request.getParameter("testId")));
                log.info("Test with id="+request.getParameter("testId")+"has been deleted");
                request.getRequestDispatcher("/testsCreator/mytests.jsp").forward(request,response);
            } catch (SQLException e) {
                log.error("Can not delete test with id="+request.getParameter("testId"), e);
            }
        }
        else request.getRequestDispatcher("/index.jsp").forward(request,response);
    }
}
