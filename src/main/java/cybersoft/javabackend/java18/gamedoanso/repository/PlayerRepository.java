package cybersoft.javabackend.java18.gamedoanso.repository;

import cybersoft.javabackend.java18.gamedoanso.model.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlayerRepository extends AbstractRepository<Player> {
    public Player findByUsername(String username) {
        // write a query to find the player by username
        final String query = """
                select username, password, name
                from player
                where username = ?
                """;
        return executeQuerySingle(connection -> {
            // create a prepared statement to execute the query
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet results = statement.executeQuery();

            // get result from result set
            if (results.next()) {
                return new Player(
                        results.getString("username"),
                        results.getString("password"),
                        results.getString("name")
                );
            }
            return null;
        });
    }

    public boolean existedByUsername(String username) {
        // create a connection to database
        final String query = """
                select username
                from player
                where username = ?
                """;
        return existedBy(connection -> {
            // write a query to find the player by username
            // create a prepared statement to execute the query
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet results = statement.executeQuery();

            // get result from result set
            return results.next();
        });
    }

    public void save(Player newUser) {
        // write a query to save new player
        final String query = """
                insert into player(username, password, name)
                values(?, ?, ?)
                """;
        executeUpdate(connection -> {
            // create a statement to execute the query
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newUser.getUsername());
            statement.setString(2, newUser.getPassword());
            statement.setString(3, newUser.getName());

            return statement.executeUpdate();
        });
    }
}
