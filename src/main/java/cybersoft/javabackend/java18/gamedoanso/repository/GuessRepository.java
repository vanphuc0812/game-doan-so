package cybersoft.javabackend.java18.gamedoanso.repository;

import cybersoft.javabackend.java18.gamedoanso.model.Guess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;

public class GuessRepository extends AbstractRepository<Guess> {
    public List<Guess> findBySession(String session) {
        final String query = """
                select value, moment, session_id, result
                from guess
                where session_id = ?
                """;
        return executeQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, session);

            ResultSet resultSet = statement.executeQuery();

            List<Guess> guesses = new LinkedList<>();
            while (resultSet.next()) {
                Guess newGuess = new Guess(
                        resultSet.getInt("value"),
                        resultSet.getString("session_id"),
                        resultSet.getDate("moment").toLocalDate().atStartOfDay(),
                        resultSet.getInt("result")
                );
                guesses.add(newGuess);
            }

            return guesses;
        });
    }

    public void save(Guess guess) {
        final String query = """
                insert into guess(value, moment, session_id, result)
                values(?, ?, ?, ?)
                """;
        executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, guess.getValue());
            statement.setTimestamp(2, Timestamp.from(
                    guess.getTimestamp().toInstant(ZoneOffset.of("+07:00")))
            );
            statement.setString(3, guess.getGameSession());
            statement.setInt(4, guess.getResult());

            return statement.executeUpdate();
        });
    }
}
