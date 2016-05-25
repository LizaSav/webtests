package servlets;

import model.Question;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;

import static helperClasses.Function.get;

/**
 * Created by Elizaveta on 21.05.2016.
 */
public class Test extends HttpServlet {
    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con=(Connection) request.getSession(true).getAttribute("connection");
        if(con==null){
            try {
                con =dataSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.getSession().setAttribute("connection", con);
        }
        LocalDateTime ld = LocalDateTime.now();
        model.Test test =dao.TestDao.getTestById(con, request.getParameter("id"));
        synchronized (this) {
            HashMap<Integer, model.Test> map= (HashMap<Integer, model.Test>) request.getSession().getAttribute("currentTests");
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(test.getId(), test);
            request.getSession().setAttribute("currentTests", map);
        }

        if (test!=null) {
            Locale locale =(Locale) request.getSession().getAttribute("language");

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\" >");
            out.println("<title>"+test.getTitle()+"</title>");
            out.println("</head><body>");
            out.println("<h1>"+test.getTitle()+"</h1>");
            if(request.getParameter("rePass")!=null){
                out.println(get("prans",locale)+":<br/>"+request.getParameter("rePass"));
            }
            out.println("<form action=\"/passTests/TestChecking\" method=\"post\"> ");
            out.println("<input type=\"hidden\" name=\"testId\" value=\""+test.getId()+"\">");
            out.println("<input type=\"hidden\" name=\"testDate\" value=\""+ Timestamp.valueOf(ld)+"\">");
            for(int i=0; i<test.getQuestions().size(); i++){
                Question q = test.getQuestions().get(i);
                out.println(q.getQuestion()+"<br/>");
                for (int j=0;j<q.getAnswers().length; j++ ){
                    out.println("<input type=\"checkbox\" name=\""+i+"\" value=\""+j+"\" />"+q.getAnswers()[j]+"<br/>");
                }
                out.println("<br/>");
            }
            String s=get("subm",locale);
            out.println("<input type=\"submit\" value=\""+s+"\">");
            out.println("</form>");
            out.println("</body></html>");
        }
        else{
            request.getRequestDispatcher("/tests/subjects.jsp").forward(request, response);
        }
    }
}
