package servlets.passTests;

import dao.TestResultDao;
import model.Person;
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

/** Вычисление результатов пройденного пользователем теста
 *
 * оценка берётся как количество правильных ответов делённое на общее количество вопросов с округлением до ближайшего
 */
public class TestChecking extends HttpServlet {
    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;

    private static final Logger log= Logger.getLogger(TestChecking.class);

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        HashMap<Integer,Test> map =((HashMap<Integer,Test>)request.getSession().getAttribute("currentTests"));
       int h = Integer.parseInt(request.getParameter("testId"));
        Test test=map.get(h);
        if (test==null) request.getRequestDispatcher("/student/mypassedtests.jsp").forward(request,response);
        int mark=0;
        StringBuffer myAnswer=new StringBuffer();
        for (int i=0; i<test.getQuestions().size(); i++){
            Question q = test.getQuestions().get(i);
            myAnswer.append("<b>"+q.getQuestion()+"</b><br/>");
            StringBuffer checkedAnswers = new StringBuffer();
            for (int a:q.getCorrectAnswers()) {
                checkedAnswers.append(a);
            }
            StringBuffer myCheckedAnswers = new StringBuffer();
            String [] parametrs = request.getParameterValues(i+"");
            if (parametrs!=null){
            for (String parametr:parametrs){
               int j=Integer.parseInt(parametr);
                myAnswer.append(q.getAnswers()[j]+"<br/>");
                myCheckedAnswers.append(j);
            }
            }
            else{
                myAnswer.append("<br/>");
            }
            if(myCheckedAnswers.toString().equals(checkedAnswers.toString())) mark++;
        }
        mark=(int)Math.round(mark*5.0/test.getQuestions().size());
        try {
            Connection con=(Connection) request.getSession(true).getAttribute("connection");
            if(con==null){
                con =dataSource.getConnection();
                request.getSession().setAttribute("connection", con);
            }
            String string=request.getParameter("testDate");
            TestResultDao.addResult(con, ((Person)request.getSession().getAttribute("user")).getId(), test.getId(), myAnswer.toString(), request.getParameter("testDate"),mark);
        } catch (SQLException e) {
            log.error("Can not add test result. User id="+((Person)request.getSession().getAttribute("user")).getId()+" test id="+test.getId(), e);
        }
        map.remove(test.getId());
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }
}

