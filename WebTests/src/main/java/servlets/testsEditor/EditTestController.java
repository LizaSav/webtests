package servlets.testsEditor;

import dao.TestDao;
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
 * Created by Elizaveta on 22.05.2016.
 */
public class EditTestController extends HttpServlet {
    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Test test =((HashMap<Integer, Test>)request.getSession().getAttribute("editTestMap")).get(Integer.parseInt(request.getParameter("testId")));
        if(request.getParameter("updateTitle")!=null){
        test.setTitle(request.getParameter("title"));
        ((HashMap<Integer, Test>)request.getSession().getAttribute("editTestMap")).remove(test.getId());
        try {
            Connection con=(Connection) request.getSession(true).getAttribute("connection");
            if(con==null){
                con =dataSource.getConnection();
                request.getSession().setAttribute("connection", con);
            }
            TestDao.editTitle(con, test);
        } catch (SQLException e) {
            //логирование ошибка подключения
        }
        request.getRequestDispatcher("/testsCreator/mytests.jsp").forward(request, response);
        }
        else if (request.getParameter("addQuestion")!=null){
            request.setAttribute("numberOfAnswers", request.getParameter("numberOfAnswers"));
            request.setAttribute("testId",test.getId());
            request.getRequestDispatcher("/testsEditor/addQuestion").forward(request,response);

    }else {
            for (int i=0; i<Integer.parseInt(request.getParameter("size")); i++){
                if(request.getParameter("deleteQuestion"+i)!=null) {
                    if (Integer.parseInt(request.getParameter("size")) > 1) {
                        request.setAttribute("questionNumber", i);
                        request.setAttribute("testId", test.getId());
                        request.setAttribute("question", test.getQuestions().get(i));
                        request.getRequestDispatcher("/testsEditor/deleteQuestion.jsp").forward(request, response);
                    }
                    else request.getRequestDispatcher("/testsCreator/mytests.jsp").forward(request,response);
                }
                else if (request.getParameter("updateQuestion"+i)!=null){
                    int numberOfAnswers = Integer.parseInt(request.getParameter("answersCount"+i));
                    int oldAnswersCount =test.getQuestions().get(i).getAnswers().length;
                        request.setAttribute("questionNumber", i);
                        request.setAttribute("testId", test.getId());
                        request.setAttribute("numberOfAnswers", numberOfAnswers);
                        if(numberOfAnswers<oldAnswersCount) request.setAttribute("deleteAnswer", true);
                        request.getRequestDispatcher("/testsEditor/editQuestion").forward(request,response);
                }
            }

        }
    }
}
