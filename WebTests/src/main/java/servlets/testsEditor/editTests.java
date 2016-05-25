package servlets.testsEditor;

import dao.TestDao;
import model.Question;
import model.Test;

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
import java.util.HashMap;
import java.util.Locale;

import static helperClasses.Function.get;

/**
 * Created by Elizaveta on 22.05.2016.
 */
public class editTests extends HttpServlet {
    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        model.Test test =new Test();
        try {
            Connection con=(Connection) request.getSession(true).getAttribute("connection");
            if(con==null){
                con =dataSource.getConnection();
                request.getSession().setAttribute("connection", con);
            }
            test = TestDao.getTestById(con, (String) request.getAttribute("testId"));
        } catch (SQLException e) {
            //логирование ошибка подключения
        }
        HashMap<Integer, Test> map;
        synchronized (this) {
            map= (HashMap<Integer, Test>) request.getSession().getAttribute("editTestMap");
            if (map == null) {
                map = new HashMap<>();
            }
        }
        map.put(test.getId(),test);
        request.getSession().setAttribute("editTestMap",map);

        Locale locale = (Locale) request.getSession().getAttribute("language");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" >");
        out.println("<title>"+get("editq",locale)+"</title>");
        out.println("</head><body>");
        out.println("<form action=\"/testsEditor/EditTestController\" method=\"post\"> ");
        out.println("<input type=\"hidden\" name=\"testId\" value=\""+test.getId()+"\">");
        out.println("<input type=\"text\" name=\"title\" required value=\""+test.getTitle()+"\">" +
                "<input type=\"submit\" name=\"updateTitle\" value=\""+get("updtitle",locale)+"\"><br/>");
        out.println("<input type=\"hidden\" name=\"size\" value=\""+test.getQuestions().size()+"\">");
        for(int i=0; i<test.getQuestions().size(); i++){
            Question q = test.getQuestions().get(i);
            out.println(q.getQuestion()+"<input type=\"submit\" name=\"updateQuestion"+i+"\" value=\""+get("editt",locale)+"\">" +"" +
                    "<input type=\"text\" name=\"answersCount"+i+"\" required size=\"3\" value=\""+q.getAnswers().length+"\" > "+get("with",locale)+
                    "<input type=\"submit\" name=\"deleteQuestion"+i+"\" value=\""+get("deletq",locale)+"\"><br/>");
            for (int j=0;j<q.getAnswers().length; j++ ){
                String checked="";
                for (int k=0; k<q.getCorrectAnswers().length; k++) if(q.getCorrectAnswers()[k]==j) checked="CHECKED";
                out.println("<input type=\"checkbox\" name=\""+i+"\" value=\""+j+"\""+ checked+"/>"+q.getAnswers()[j]+"<br/>");
            }
            out.println("<br/>");
        }
        out.println(get("addq",locale)+" <input type=\"text\" name=\"numberOfAnswers\" required size=\"3\" value=\"2\"> "+get("with",locale));
        out.println("<input type=\"submit\" name=\"addQuestion\" value=\""+get("add",locale)+"\"><br/>");
        out.println("</form>");
        out.println("</body></html>");
    }


}
