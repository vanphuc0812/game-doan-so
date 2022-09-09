package cybersoft.javabackend.java18.gamedoanso.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cybersoft.javabackend.java18.gamedoanso.utils.JspUtils;
import cybersoft.javabackend.java18.gamedoanso.utils.UrlUtils;
import cybersoft.javabackend.java18.gamedoanso.service.GameService;

@WebServlet(name = "authServlet", urlPatterns = {
		UrlUtils.REGISTER,
		UrlUtils.LOGIN,
		UrlUtils.LOGOUT
})
public class AuthServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		switch (req.getServletPath()) {
            case UrlUtils.REGISTER -> req.getRequestDispatcher(JspUtils.REGISTER)
					.forward(req, resp);
			case UrlUtils.LOGIN -> req.getRequestDispatcher(JspUtils.LOGIN)
					.forward(req, resp);
			case UrlUtils.LOGOUT -> {
				req.getSession().invalidate();
				resp.sendRedirect(req.getContextPath() + UrlUtils.LOGIN);
			}
			default -> resp.sendRedirect(req.getContextPath() + UrlUtils.NOT_FOUND);
		}
	}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtils.REGISTER -> processRegister(req, resp);
            case UrlUtils.LOGIN -> processLogin(req, resp);
            default -> resp.sendRedirect(req.getContextPath() + UrlUtils.NOT_FOUND);
        }
    }

    private void processLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var player = GameService.getINSTANCE().dangNhap(req.getParameter("username"), req.getParameter("password"));

        if (player == null) {
            req.setAttribute("errors", "Username or password is incorrect!");
            this.doGet(req, resp);
        } else {
            req.getSession().setAttribute("currentUser", player);
            resp.sendRedirect(req.getContextPath() + UrlUtils.GAME);
        }
    }

    private void processRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        var newPlayer = GameService.getINSTANCE().register(username, password, name);

        if (newPlayer != null) {
            req.getSession().setAttribute("currentUser", newPlayer);
            resp.sendRedirect(req.getContextPath() + UrlUtils.GAME);
        } else {
            req.setAttribute("errors", "User infomation is invalid or used.");
            doGet(req, resp);
        }
    }
}
