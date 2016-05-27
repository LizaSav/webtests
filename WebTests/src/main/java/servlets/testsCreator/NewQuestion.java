package servlets.testsCreator;

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
import java.time.LocalDateTime;
import java.util.HashMap;

/** Добавляет вопрос к тесту или сохраняет тест
 *
 */
public class NewQuestion extends HttpServlet {
    private static final Logger log= Logger.getLogger(NewQuestion.class);

    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        int size = Integer.parseInt(request.getParameter("currentNumberOfAnswers"));
        Question q;
        String question=request.getParameter("question");
        String [] answers = new String[size];
        String[] preCorrectAnswers = request.getParameterValues("checkbox");
        if (preCorrectAnswers==null) request.getRequestDispatcher("/testsCreator/addQuestion").forward(request,response);
        int [] correctAnswers = new int[preCorrectAnswers.length];
        for (int i=0; i<correctAnswers.length; i++){
            correctAnswers[i]=Integer.parseInt(preCorrectAnswers[i]);
        }
        for (int i=0; i<size; i++){
            answers[i]=request.getParameter("answer"+i);
        }
        q=new Question(question,answers,correctAnswers);
        HashMap<String,model.Test> tests = (HashMap<String, Test>) request.getSession().getAttribute("testMap");
        model.Test test = tests.get(request.getParameter("testTitle"));
        if(test==null) request.getRequestDispatcher("/testsCreator/mytests.jsp").forward(request,response);
        test.setLatest_update(LocalDateTime.now());
        test.getQuestions().add(q);

        if(request.getParameter("saveTest")!=null){
            try {
                Connection con=(Connection) request.getSession(true).getAttribute("connection");
                if(con==null){
                    con =dataSource.getConnection();
                    request.getSession().setAttribute("connection", con);
                }
            TestDao.addTest(con, test);
            } catch (SQLException e) {
                log.error("Can not save test="+test, e);
            }
            tests.remove(test.getTitle());
            request.getRequestDispatcher("/testsCreator/mytests.jsp").forward(request,response);
        }
        else{
            if (request.getParameter("addQuestion")!=null){
                request.setAttribute("testTitle", test.getTitle());
                request.setAttribute("numberOfAnswers",request.getParameter("numberOfAnswers"));
                request.getRequestDispatcher("/testsCreator/addQuestion").forward(request,response);
            }
            else request.getRequestDispatcher("/testsCreator/mytests.jsp").forward(request,response);

        }
    }
}
