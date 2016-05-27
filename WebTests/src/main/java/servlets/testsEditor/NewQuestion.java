package servlets.testsEditor;

import dao.TestDao;
import model.Question;
import model.Test;
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
import java.util.HashMap;

/** Добавление вопроса в редактируемый тест
 *
 */
public class NewQuestion extends HttpServlet {
    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;
    private static final Logger log= Logger.getLogger(NewQuestion.class);
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        if (request.getParameter("addQuestion")!=null) {
            int size = Integer.parseInt(request.getParameter("currentNumberOfAnswers"));
            Question q;
            String question = request.getParameter("question");
            String[] answers = new String[size];
            String[] preCorrectAnswers = request.getParameterValues("checkbox");
            if (preCorrectAnswers==null) request.getRequestDispatcher("/testsCreator/addQuestion").forward(request,response);
            int[] correctAnswers = new int[preCorrectAnswers.length];
            for (int i = 0; i < correctAnswers.length; i++) {
                correctAnswers[i] = Integer.parseInt(preCorrectAnswers[i]);
            }
            for (int i = 0; i < size; i++) {
                answers[i] = request.getParameter("answer" + i);
            }
            q = new Question(question, answers, correctAnswers);
            ((HashMap<String, Test>) request.getSession().getAttribute("editTestMap")).remove(Integer.parseInt(request.getParameter("testId")));
            try {
                Connection con=(Connection) request.getSession(true).getAttribute("connection");
                if(con==null){
                    con =dataSource.getConnection();
                    request.getSession().setAttribute("connection", con);
                }
                TestDao.addQuestion(con, Integer.parseInt(request.getParameter("testId")), q);
                log.info("New question has been added in test with id="+request.getParameter("testId"));
                request.getRequestDispatcher("/testsCreator/mytests.jsp").forward(request,response);
            } catch (SQLException e) {
                log.error("Can not add new question in test with id="+request.getParameter("testId"), e);
            }
        }
        else   request.getRequestDispatcher("/index.jsp").forward(request,response);

    }
}