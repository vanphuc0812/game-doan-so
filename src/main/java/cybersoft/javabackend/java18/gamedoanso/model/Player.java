package cybersoft.javabackend.java18.gamedoanso.model;

import java.io.Serializable;
import java.util.List;

public class Player implements Serializable {
    private final String username;
    private final String password;
    private final String name;
    private List<GameSession> manChoi;

    public Player(String username, String password, String name) {
        super();
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
    
    public List<GameSession> getManChoi() {
        return manChoi;
    }
}
