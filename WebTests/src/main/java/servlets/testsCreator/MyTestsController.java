package servlets.testsCreator;

import model.Person;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


public class MyTestsController extends HttpServlet {
    private static final Logger log= Logger.getLogger(MyTestsController.class);
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        if (request.getParameter("createNew")!=null){
            log.info("User with id="+ ((Person)request.getSession(true).getAttribute("user")).getId()+"has been started creating new test");
            request.getRequestDispatcher("/testsCreator/newTest.jsp").forward(request,response);
        }
        else{
            Enumeration eNames = request.getParameterNames();
            while (eNames.hasMoreElements()) {
                String attributeName = (String) eNames.nextElement();
                if (request.getParameter(attributeName)!=null){
                    String name=attributeName;
                    if (name.contains("edit")){
                        String id=name.replace("edit","");
                        request.setAttribute("testId",id);
                        log.info("User with id="+ ((Person)request.getSession(true).getAttribute("user")).getId()+"has been started edit test with id="+id);
                        request.getRequestDispatcher("/testsEditor/editTests").forward(request,response);
                    }
                    if (name.contains("delete")){
                        String id=name.replace("delete","");
                        request.setAttribute("testId",id);
                        request.getRequestDispatcher("/testsEditor/deleteTest.jsp").forward(request,response);
                    }
                    if (name.contains("studentsresult")){
                        String id=name.replace("studentsresult","");
                        request.setAttribute("testId",id);
                        request.getRequestDispatcher("/testsCreator/studentsResults.jsp").forward(request,response);
                    }

                }
            }
        }
    }
}
