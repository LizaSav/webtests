package servlets.testsEditor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import static helperClasses.Function.get;

/**
 * Created by Elizaveta on 22.05.2016.
 */
public class addQuestion extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numberOfAnswers=0;
        try {
            numberOfAnswers =Integer.parseInt((String)request.getAttribute("numberOfAnswers"));
        }
        catch (Exception ex){
            request.getRequestDispatcher("/index.jsp").forward(request,response); //страница которыя получает только сколько ответов в вопросе
        }
        if (numberOfAnswers>0) {
            Locale locale = (Locale) request.getSession().getAttribute("language");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\" >");
            out.println("<title>"+get("addquestion",locale)+"</title>");
            out.println("</head><body>");
            out.println("<form action=\"/testsEditor/NewQuestion\" method=\"post\">");
            out.println(get("question",locale)+":<br/>");
            out.println("<input type=\"text\" name=\"question\" required size=\"255\"><br/>");
            out.println(get("answers",locale)+":<br/>");
            for (int i = 0; i < numberOfAnswers; i++) {
                out.println("<input type=\"checkbox\" name=\"checkbox\" value=\"" + i + "\"> <input type=\"text\" name=\"answer" + i + "\" size=\"127\"><br/>");
            }
            out.println("<input type=\"submit\" name=\"addQuestion\" value=\""+get("add",locale)+"\"><br/>");
            out.println("<input type=\"hidden\" name=\"currentNumberOfAnswers\" value=\""+numberOfAnswers+"\">");
            out.println("<input type=\"hidden\" name=\"testId\" value=\""+request.getAttribute("testId")+"\">");
            out.println("</form>");
            out.println("</body></html>");
        }
        else  request.getRequestDispatcher("/index.jsp").forward(request,response); //страница которыя получает только сколько ответов в вопросе

    }
}
