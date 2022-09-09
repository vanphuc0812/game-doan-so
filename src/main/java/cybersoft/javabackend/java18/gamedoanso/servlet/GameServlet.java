package cybersoft.javabackend.java18.gamedoanso.servlet;

import cybersoft.javabackend.java18.gamedoanso.model.GameSession;
import cybersoft.javabackend.java18.gamedoanso.model.Guess;
import cybersoft.javabackend.java18.gamedoanso.model.Player;
import cybersoft.javabackend.java18.gamedoanso.service.GameService;
import cybersoft.javabackend.java18.gamedoanso.utils.JspUtils;
import cybersoft.javabackend.java18.gamedoanso.utils.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "gameServlet", urlPatterns = {
        UrlUtils.GAME,
        UrlUtils.NEW_GAME,
        UrlUtils.GAME_ID
})
public class GameServlet extends HttpServlet {
    private GameService gameService;

    // init -> service -> destroy
    @Override
    public void init() throws ServletException {
        super.init();
        gameService = GameService.getINSTANCE();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getRequestURI().replace(UrlUtils.CONTEXT_PATH, "");
        switch (servletPath) {
            case UrlUtils.GAME:
            case UrlUtils.NEW_GAME:
                loadCurrentGame(req, resp);
                break;
            default:
                if (servletPath.matches(UrlUtils.GAME + "/.*"))
                    loadGameById(req, resp);
                resp.sendRedirect(req.getContextPath() + UrlUtils.NOT_FOUND);
                break;
        }
    }

    private void loadGameById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String gameId = req.getRequestURI().replace(UrlUtils.CONTEXT_PATH + UrlUtils.GAME + "/", "");
        GameSession game = gameService.getGameSession(gameId);
        if (game != null) {
            game.getGuess().forEach(guess -> {
                System.out.println(guess.getValue());
                System.out.println(guess.getTimestamp());
            });
            req.setAttribute("game", game);
            req.getRequestDispatcher(JspUtils.GAME)
                    .forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + UrlUtils.NOT_FOUND);
        }
    }

    private void loadCurrentGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var currentUser = (Player) req.getSession().getAttribute("currentUser");
        // create new game/get existed game
        GameSession game = gameService.getCurrentGame(currentUser.getUsername());
        // put in req
        req.setAttribute("game", game);
        req.getRequestDispatcher(JspUtils.GAME)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case UrlUtils.GAME -> processGame(req, resp);
            case UrlUtils.NEW_GAME -> processNewGame(req, resp);
            default -> resp.sendRedirect(req.getContextPath() + UrlUtils.NOT_FOUND);
        }
    }

    private void processNewGame(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var currentUser = (Player) req.getSession().getAttribute("currentUser");
        // create new game/get existed game
        gameService.skipAndPlayNewGame(currentUser.getUsername());

        resp.sendRedirect(req.getContextPath() + UrlUtils.GAME);
    }

    private void processGame(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String gameSessionId = req.getParameter("game-session");
        int guessNumber = Integer.parseInt(req.getParameter("guess"));

        var gameSession = gameService.getGameSession(gameSessionId);

        if (gameSession == null) { // if the session is not existed, ask the player to sign in again
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + UrlUtils.LOGIN);
            return;
        }

        gameSession.getGuess().add(createGuess(gameSession, guessNumber));

        if (guessNumber == gameSession.getTargetNumber()) {
            gameService.completeGame(gameSessionId);
            System.out.println("Complete game");
        }

        resp.sendRedirect(req.getContextPath() + UrlUtils.GAME);
    }

    private Guess createGuess(GameSession gameSession, int guessNumber) {
        int result = Integer.compare(guessNumber, gameSession.getTargetNumber());
        Guess newGuess = new Guess(guessNumber, gameSession.getId(), result);
        gameService.saveGuess(newGuess);
        return newGuess;
    }
}
