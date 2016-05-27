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

/** Изеняет вопрос в тесте и сохраняет изменения в базу
 *
 */
public class NewEditQuestion extends HttpServlet {
    private static final Logger log= Logger.getLogger(NewEditQuestion.class);
        @Resource(name = "jdbc/ProdDB")
        private DataSource dataSource;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        if (request.getParameter("editQuestion")!=null) {
            HashMap<Integer,Test> map = ((HashMap<Integer,Test>)request.getSession().getAttribute("editTestMap"));
            Test test = map.get(Integer.parseInt(request.getParameter("testId")));
            int size = Integer.parseInt(request.getParameter("currentNumberOfAnswers"));
            Question q;
            String question = request.getParameter("question");
            String[] answers = new String[size];
            String[] preCorrectAnswers = request.getParameterValues("checkbox");
            if (preCorrectAnswers==null) request.getRequestDispatcher("/testsEditor/addQuestion").forward(request,response);
            int[] correctAnswers = new int[preCorrectAnswers.length];
            for (int i = 0; i < correctAnswers.length; i++) {
                correctAnswers[i] = Integer.parseInt(preCorrectAnswers[i]);
            }
            for (int i = 0; i < size; i++) {
                answers[i] = request.getParameter("answer" + i);
            }
            q = new Question(question, answers, correctAnswers);
            boolean changed = true;
            if (size==test.getQuestions().get(Integer.parseInt(request.getParameter("questionNumber"))).getAnswers().length){
                boolean change = false;
                Question  oldQ = test.getQuestions().get(Integer.parseInt(request.getParameter("questionNumber")));
                if(oldQ.getCorrectAnswers().length==q.getCorrectAnswers().length) {
                    for (int i = 0; i < oldQ.getCorrectAnswers().length; i++) {
                        if (oldQ.getCorrectAnswers()[i] != q.getCorrectAnswers()[i]) {
                            change = true;
                            break;
                        }
                    }
                }
                else change=true;
                if(!change) changed=false;
            }
            ((HashMap<String, Test>) request.getSession().getAttribute("editTestMap")).remove(Integer.parseInt(request.getParameter("testId")));
            try {
                Connection con=(Connection) request.getSession(true).getAttribute("connection");
                if(con==null){
                    con =dataSource.getConnection();
                    request.getSession().setAttribute("connection", con);
                }
                TestDao.editQuestion(con, Integer.parseInt(request.getParameter("testId")), Integer.parseInt(request.getParameter("questionNumber")), q, changed);
                log.info("Question in test with id="+request.getParameter("testId")+"has been edited");
                request.getRequestDispatcher("/testsCreator/mytests.jsp").forward(request,response);
            } catch (SQLException e) {
                log.error("Can not edit question in test with id="+request.getParameter("testId"), e);
            }
        }
        else   {
            ((HashMap<Integer,Test>)request.getSession().getAttribute("editTestMap")).remove(Integer.parseInt(request.getParameter("testId")));
            request.getRequestDispatcher("/index.jsp").forward(request,response);
    }
    }
}
