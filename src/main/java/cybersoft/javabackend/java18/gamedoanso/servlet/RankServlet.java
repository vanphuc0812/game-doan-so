package cybersoft.javabackend.java18.gamedoanso.servlet;

import cybersoft.javabackend.java18.gamedoanso.model.GameSession;
import cybersoft.javabackend.java18.gamedoanso.service.GameService;
import cybersoft.javabackend.java18.gamedoanso.utils.JspUtils;
import cybersoft.javabackend.java18.gamedoanso.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "rankServlet", urlPatterns = UrlUtils.RANK)
public class RankServlet extends HttpServlet {
    private GameService gameService;

    // init -> service -> destroy
    @Override
    public void init() throws ServletException {
        super.init();
        gameService = GameService.getINSTANCE();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (UrlUtils.RANK.equals(req.getServletPath())) {
            processRanking(req, resp);
        }
    }

    private void processRanking(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<GameSession> rankingList = gameService.ranking();
        req.getSession().setAttribute("rank", rankingList);
        req.getRequestDispatcher(JspUtils.RANK)
                .forward(req, resp);
    }
}
