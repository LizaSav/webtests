package filters;

import model.Person;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Elizaveta on 20.05.2016.
 */
public class CreatorTestsFilter implements Filter {
    private FilterConfig filterConfig;
    public void setFilterConfig(FilterConfig fc) {
        filterConfig = fc;
    }
    public FilterConfig getFilterConfig() {
        return filterConfig;
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (filterConfig == null){
            return;
        }
        ServletContext ctx = filterConfig.getServletContext();
        if (((HttpServletRequest)request).getSession().getAttribute("user")==null){
            RequestDispatcher dispatcher = ctx.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }

        Person person = (Person)((HttpServletRequest)request).getSession().getAttribute("user");
        if (!(person.isPermission())){
            RequestDispatcher dispatcher = ctx.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        } else {
            ((HttpServletRequest) request).getSession().setAttribute("creator","true");
            chain.doFilter(request,response);
        }
    }
    public void init(FilterConfig config)  throws ServletException {
        this.filterConfig = config;
    }
    public void destroy() { }
}

