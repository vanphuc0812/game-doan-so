package cybersoft.javabackend.java18.gamedoanso.store;

import cybersoft.javabackend.java18.gamedoanso.model.GameSession;
import cybersoft.javabackend.java18.gamedoanso.model.Guess;
import cybersoft.javabackend.java18.gamedoanso.model.Player;

import java.util.ArrayList;
import java.util.List;

public class GameStore {
    private final List<Player> players;
    private final List<GameSession> gameSessions;
    private final List<Guess> guesses;

    GameStore() {
        players = new ArrayList<>();
        gameSessions = new ArrayList<>();
        guesses = new ArrayList<>();

        players.add(new Player("admin", "1234", "Admin"));
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<GameSession> getGameSessions() {
        return gameSessions;
    }

    public List<Guess> getGuesses() {
        return guesses;
    }
}
