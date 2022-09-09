package cybersoft.javabackend.java18.gamedoanso.repository;

import cybersoft.javabackend.java18.gamedoanso.jdbc.MySqlConnection;
import cybersoft.javabackend.java18.gamedoanso.model.GameSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class GameSessionRepository extends AbstractRepository<GameSession> {
    public void save(GameSession gameSession) {
        final String query = """
                insert into game_session
                (id, target, start_time, is_completed, is_active, username)
                values(?, ?, ?, ?, ?, ?)
                """;
        executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, gameSession.getId());
            statement.setInt(2, gameSession.getTargetNumber());
            statement.setTimestamp(3, Timestamp.from(
                    gameSession.getStartTime().toInstant(ZoneOffset.of("+07:00")))
            );
            statement.setInt(4, gameSession.getIsCompleted() ? 1 : 0);
            statement.setInt(5, gameSession.isActive() ? 1 : 0);
            statement.setString(6, gameSession.getUsername());

            return statement.executeUpdate();
        });
    }

    public List<GameSession> findByUsername(String username) {
        final String query = """
                    select id,  target, start_time, end_time, is_completed, is_active, username
                    from game_session
                    where username = ?
                """;
        return executeQuery(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            List<GameSession> sessions = new ArrayList<>();

            while (resultSet.next()) {
                sessions.add(new GameSession()
                        .id(resultSet.getString("id"))
                        .targetNumber(resultSet.getInt("target"))
                        .startTime(getDateTimeFromResultSet("start_time", resultSet))
                        .endTime(getDateTimeFromResultSet("end_time", resultSet))
                        .isCompleted(resultSet.getInt("is_completed") == 1)
                        .isActive(resultSet.getInt("is_active") == 1)
                        .username(resultSet.getString("username")));
            }

            return sessions;
        });
    }

    public GameSession findById(String id) {
        final String query = """
                    select id,  target, start_time, end_time, is_completed, is_active, username
                    from game_session
                    where id = ?
                """;
        return executeQuerySingle(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;

            return new GameSession()
                    .id(resultSet.getString("id"))
                    .targetNumber(resultSet.getInt("target"))
                    .startTime(getDateTimeFromResultSet("start_time", resultSet))
                    .endTime(getDateTimeFromResultSet("end_time", resultSet))
                    .isCompleted(resultSet.getInt("is_completed") == 1)
                    .isActive(resultSet.getInt("is_active") == 1)
                    .username(resultSet.getString("username"));
        });
    }

    public void deactivateAllGames(String username) {
        final String query = """
                update game_session
                set is_active = 0
                where username = ?
                """;
        executeUpdate(connection -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            return statement.executeUpdate();
        });
    }

    public void completeGame(String sessionId, LocalDateTime endTime) {

        final String query = """
                update game_session
                set is_completed = 1, end_time = ?
                where id = ?
                """;
        executeUpdate(connection -> {
            System.out.println(Timestamp.valueOf(endTime));
            System.out.println(sessionId);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(2, sessionId);
            statement.setTimestamp(1, Timestamp.valueOf(endTime));
            return statement.executeUpdate();
        });
    }

    public List<GameSession> ranking() {
        final String query = """
                select id,  target, start_time, end_time, is_completed, is_active, username
                from game_session
                where is_completed = 1 and end_time is not null
                order by end_time - start_time
                limit 10
                """;
        return executeQuery(connection -> {
           PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<GameSession> sessions = new ArrayList<>();

            while (resultSet.next()) {
                sessions.add(new GameSession()
                        .id(resultSet.getString("id"))
                        .targetNumber(resultSet.getInt("target"))
                        .startTime(getDateTimeFromResultSet("start_time", resultSet))
                        .endTime(getDateTimeFromResultSet("end_time", resultSet))
                        .isCompleted(resultSet.getInt("is_completed") == 1)
                        .isActive(resultSet.getInt("is_active") == 1)
                        .username(resultSet.getString("username")));
            }
            return sessions;
        });
    }

    private LocalDateTime getDateTimeFromResultSet(String columnName, ResultSet resultSet) {
        Timestamp time;

        try {
            time = resultSet.getTimestamp(columnName);
        } catch (SQLException e) {
            return null;
        }

        if (time == null)
            return null;

        return time.toLocalDateTime();
    }

}
