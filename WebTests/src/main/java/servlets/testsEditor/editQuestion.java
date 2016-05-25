package servlets.testsEditor;

import model.Question;
import model.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;

import static helperClasses.Function.get;

/**
 * Created by Elizaveta on 23.05.2016.
 */
public class editQuestion extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numberOfAnswers=0;
        try {
            numberOfAnswers = (int) request.getAttribute("numberOfAnswers");
        }
        catch (Exception ex){
            request.getRequestDispatcher("/index.jsp").forward(request,response); //страница которыя получает только сколько ответов в вопросе
        }
        if (numberOfAnswers>0) {
            Locale locale = (Locale) request.getSession().getAttribute("language");

            Test test = ((HashMap<Integer,Test>)request.getSession().getAttribute("editTestMap")).get(request.getAttribute("testId"));
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            Question q= test.getQuestions().get((int)request.getAttribute("questionNumber"));
            response.setContentType("text/html; UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\" >");
            out.println("<title>"+get("edq",locale)+"</title>");
            out.println("</head><body>");
            out.println("<form action=\"/testsEditor/NewEditQuestion\" method=\"post\">");
            out.println(get("question",locale)+":<br/>");
            out.println("<input type=\"text\" name=\"question\" required size=\"255\" value=\""+q.getQuestion()+"\"><br/>");
            out.println(get("answers",locale)+":<br/>");

            if (request.getAttribute("deleteAnswer")!=null){
                for (int i = 0; i < numberOfAnswers; i++) {
                    out.println("<input type=\"checkbox\" name=\"checkbox\" value=\"" + i + "\" CHECKED> <input type=\"text\"  required name=\"answer" + i + "\" size=\"127\" ><br/>");
                }
                out.println(get("pranswers",locale)+":<br/>");
                for( int i=0; i<q.getAnswers().length; i++){
                    out.println(q.getAnswers()[i]+"<br/>");
                }
            }
            else {
                for (int i = 0; i < numberOfAnswers; i++) {
                    String checked = "";
                    String value = "";
                    if (i < q.getAnswers().length) {
                        value = " value=\"" + q.getAnswers()[i] + "\" ";
                        for (int j = 0; j < q.getCorrectAnswers().length; j++) {
                            if (i == q.getCorrectAnswers()[j]) checked = " CHECKED ";
                        }
                    }
                    out.println("<input type=\"checkbox\" name=\"checkbox\" value=\"" + i + "\"" + checked + "> <input type=\"text\"  required name=\"answer" + i + "\" size=\"127\" " + value + "><br/>");
                }
            }
            out.println("<input type=\"submit\" name=\"editQuestion\" value=\""+get("edit",locale)+"\"><br/>");
            out.println("<input type=\"submit\" name=\"cancel\" value=\""+get("cancel",locale)+"\"><br/>");
            out.println("<input type=\"hidden\" name=\"currentNumberOfAnswers\" value=\""+numberOfAnswers+"\">");
            int t=(int)request.getAttribute("testId");
            out.println("<input type=\"hidden\" name=\"testId\" value=\""+t+"\">");
            out.println("<input type=\"hidden\" name=\"questionNumber\" value=\""+request.getAttribute("questionNumber")+"\">");
            out.println("</form>");
            out.println("</body></html>");
        }
        else  request.getRequestDispatcher("/index.jsp").forward(request,response); //страница которыя получает только сколько ответов в вопросе

    }
}
