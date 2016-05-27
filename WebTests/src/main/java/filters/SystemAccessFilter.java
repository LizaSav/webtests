package filters;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/** Проверка того, что поьзователь залогинен
 *
 */
public class SystemAccessFilter implements Filter {
    private FilterConfig filterConfig;
    public void setFilterConfig(FilterConfig fc) {
        filterConfig = fc;
    }
    public FilterConfig getFilterConfig() {
        return filterConfig;
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        if (filterConfig == null){
            return;
        }
        ServletContext ctx = filterConfig.getServletContext();
        String logged = (String)((HttpServletRequest)request).getSession().getAttribute("isLogged");
        if (!"true".equals(logged)){
            RequestDispatcher dispatcher = ctx.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        } else {
            chain.doFilter(request,response);
        }
    }
    public void init(FilterConfig config)  throws ServletException {
        this.filterConfig = config;
    }
    public void destroy() { }
}