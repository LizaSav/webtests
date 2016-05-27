package servlets.testsEditor;

import dao.TestDao;
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

/**Определяет как нужно редактировать тест
 *
 * удалить вопрос -происходит здесь
 * добавить вопрос -{@link addQuestion}
 * изменить имеющийся вопрос -{@Link editQuestion}
 */
public class EditTestController extends HttpServlet {
    private static final Logger log= Logger.getLogger(EditTestController.class);

    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Test test =((HashMap<Integer, Test>)request.getSession().getAttribute("editTestMap")).get(Integer.parseInt(request.getParameter("testId")));
        if(request.getParameter("updateTitle")!=null){
            if (test==null) request.getRequestDispatcher("/testsCreator/mytests.jsp").forward(request,response);
        test.setTitle(request.getParameter("title"));
        ((HashMap<Integer, Test>)request.getSession().getAttribute("editTestMap")).remove(test.getId());
        try {
            Connection con=(Connection) request.getSession(true).getAttribute("connection");
            if(con==null){
                con =dataSource.getConnection();
                request.getSession().setAttribute("connection", con);
            }
            TestDao.editTitle(con, test);
            log.info("Title in test with id="+test.getId()+"has been changed");
        } catch (SQLException e) {
            log.error("Can not edit title in test with id="+test.getId(), e);
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
