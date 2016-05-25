package servlets.testsCreator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by Elizaveta on 22.05.2016.
 */
public class MyTestsController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if (request.getParameter("createNew")!=null){
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
                        request.getRequestDispatcher("/testsEditor/editTests").forward(request,response);
                    }
                    if (name.contains("delete")){
                        String id=name.replace("delete","");
                        request.setAttribute("testId",id);
                        request.getRequestDispatcher("/testsEditor/deleteTest.jsp").forward(request,response);
                    }
                }
            }
        }
    }
}
