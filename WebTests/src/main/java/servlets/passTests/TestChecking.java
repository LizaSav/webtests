package servlets.passTests;

import dao.TestResultDao;
import model.Person;
import model.Question;
import model.Test;

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

/**
 * Created by Elizaveta on 23.05.2016.
 */
public class TestChecking extends HttpServlet {
    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<Integer,Test> map =((HashMap<Integer,Test>)request.getSession().getAttribute("currentTests"));
       int h = Integer.parseInt(request.getParameter("testId"));
        Test test=map.get(h);
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
            for (String parametr:parametrs){
               int j=Integer.parseInt(parametr);
                myAnswer.append(q.getAnswers()[j]+"<br/>");
                myCheckedAnswers.append(j);
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
            TestResultDao.addResult(con, ((Person)request.getSession().getAttribute("user")).getId(), test.getId(), myAnswer.toString(), request.getParameter("testDate"),mark);
        } catch (SQLException e) {
            //логирование ошибка подключения
        }
        map.remove(test.getId());
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }
}

