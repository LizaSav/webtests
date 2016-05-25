package filters;

import dao.TestResultDao;
import model.Person;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Elizaveta on 24.05.2016.
 */
public class TestsAccessFilter implements Filter {
    @Resource(name = "jdbc/ProdDB")
    private DataSource dataSource;

    private FilterConfig filterConfig;
    public void setFilterConfig(FilterConfig fc) {
        filterConfig = fc;
    }
    public FilterConfig getFilterConfig() {
        return filterConfig;
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (filterConfig == null) {
            return;
        }
        ServletContext ctx = filterConfig.getServletContext();
        if (((HttpServletRequest) request).getSession().getAttribute("user") != null) {
            int studentId = ((Person) ((HttpServletRequest) request).getSession().getAttribute("user")).getId();
            int testId = Integer.parseInt(request.getParameter("id"));
            boolean passed = true;
            try {
                Connection con = (Connection) ((HttpServletRequest) request).getSession(true).getAttribute("connection");
                if (con == null) {
                    con = dataSource.getConnection();
                    ((HttpServletRequest) request).getSession().setAttribute("connection", con);
                }
                passed = TestResultDao.isPassed(con, studentId, testId);
            } catch (SQLException e) {
                //логирование ошибка подключения
            }
            if (passed) {
                RequestDispatcher dispatcher = ctx.getRequestDispatcher("/student/mypassedtests.jsp");
                dispatcher.forward(request, response);
            } else chain.doFilter(request, response);

        } else {
            RequestDispatcher dispatcher = ctx.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);

        }
    }

    public void init(FilterConfig config)  throws ServletException {
        this.filterConfig = config;
    }
    public void destroy() { }
}