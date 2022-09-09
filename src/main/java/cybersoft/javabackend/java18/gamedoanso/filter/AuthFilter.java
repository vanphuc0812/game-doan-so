package cybersoft.javabackend.java18.gamedoanso.filter;

import cybersoft.javabackend.java18.gamedoanso.utils.UrlUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = UrlUtils.ALL)
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        // process before the request get in servlet
        // filterChain.doFilter(req, resp);
        // process response from servlet
        if (isLoginUser(req) || isAuthUrl(req)) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + UrlUtils.LOGIN);
        }
    }

    private boolean isAuthUrl(HttpServletRequest req) {
        var path = req.getServletPath();
        return path.startsWith(UrlUtils.LOGIN)
                || path.startsWith(UrlUtils.REGISTER)
                || path.startsWith(UrlUtils.HEALTH);
    }

    private boolean isLoginUser(HttpServletRequest req) {
        var currentUser = req.getSession().getAttribute("currentUser");
        return currentUser != null;
    }
}
