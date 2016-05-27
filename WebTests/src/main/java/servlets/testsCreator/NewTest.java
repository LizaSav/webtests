package servlets.testsCreator;

import model.Person;
import model.Subject;
import model.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/** Выбор названия и предмета для нового теста
 *
 */
public class NewTest extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        HashMap<String, model.Test> map;
        synchronized (this) {
           map= (HashMap<String, Test>) request.getSession().getAttribute("testMap");
           if (map == null) {
               map = new HashMap<>();
           }
       }
        model.Test test= new Test();
        String title =request.getParameter("title");
        test.setTitle(title);
        int creator_id = ((Person)request.getSession().getAttribute("user")).getId();
        test.setCreatorId(creator_id);
        String sub = request.getParameter("subject");
        switch (request.getParameter("subject")){ //дописать предметы
            case "GEOGRAPHY": {test.setSubject(Subject.GEOGRAPHY); break;}
            case "ALGEBRA": {test.setSubject(Subject.ALGEBRA);break;}
            case "COMPUTER_SCIENCE": {test.setSubject(Subject.COMPUTER_SCIENCE);break;}
            case "ENGLISH": {test.setSubject(Subject.ENGLISH);break;}
            case "GEOMETRY": {test.setSubject(Subject.GEOMETRY);break;}
            case "LITERATURE":{ test.setSubject(Subject.LITERATURE);break;}
            case "RUSSIAN": {test.setSubject(Subject.RUSSIAN);break;}
            case "OTHER": {test.setSubject(Subject.OTHER); break;}
        }
        request.setAttribute("testTitle", title);
        request.setAttribute("numberOfAnswers",request.getParameter("numberOfAnswers"));
        map.put(test.getTitle(),test);
        request.getSession().setAttribute("testMap",map);
        request.getRequestDispatcher("/testsCreator/addQuestion").forward(request,response);
    }
}
