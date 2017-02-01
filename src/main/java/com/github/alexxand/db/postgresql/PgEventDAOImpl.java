package com.github.alexxand.db.postgresql;

import com.github.alexxand.db.EventDAO;
import com.github.alexxand.model.Event;
import com.github.alexxand.utils.exceptions.DAOException;
import com.google.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.github.alexxand.db.postgresql.Utils.getEvent;
import static com.github.alexxand.db.postgresql.Utils.prepareLongInsertQuery;
import static com.github.alexxand.db.postgresql.Utils.update;

public class PgEventDAOImpl implements EventDAO {
    private DataSource dataSource;

    @Inject
    public PgEventDAOImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }


    @Override
    public List<Event> getEvents(int managerId) throws DAOException {
        String query = "SELECT " +
                "events.eventId, events.dateTime, events.text, events.creatorId, " +
                "array_agg(participants.participantId) " +
                "FROM events, participants " +
                "WHERE events.eventId = participants.eventId " +
                "GROUP BY events.eventId, events.dateTime, events.text, events.creatorId " +
                "HAVING events.creatorId = ? OR bool_or(participants.participantId = ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, managerId);
            statement.setInt(2, managerId);

            try(ResultSet resultSet = statement.executeQuery()) {
                List<Event> eventList = new ArrayList<>();

                while (resultSet.next()) {
                    Event event = getEvent(resultSet);
                    eventList.add(event);
                }

                return eventList;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static int createEvent(Connection connection, Event event) throws SQLException{
        String insEvQuery = "INSERT INTO events(eventId, date, text, creatorId) " +
                "VALUES (DEFAULT, ?, ?, ?) " +
                "RETURNING eventId";

        try(PreparedStatement insEvStatement = connection.prepareStatement(insEvQuery)) {
            insEvStatement.setTimestamp(1, Timestamp.valueOf(event.getDateTime()));
            insEvStatement.setString(2, event.getText());
            insEvStatement.setInt(3, event.getCreatorId());
            try (ResultSet resultSet = insEvStatement.executeQuery()){
                resultSet.next();
                return resultSet.getInt("eventId");
            }
        } catch (SQLException e){
            connection.rollback();
            throw e;
        }
    }

    private static void addParticipants(Connection connection, Event event, int eventId) throws SQLException{
        List<Integer> participantIds = event.getParticipantIds();

        String insPartQuery = prepareLongInsertQuery(
                "INSERT INTO participants(eventId,participantId) VALUES\n",
                participantIds.size(), 2);

        try (PreparedStatement insPartStatement = connection.prepareStatement(insPartQuery)) {
            for (int participantId : participantIds) {
                insPartStatement.setInt(eventId, participantId);
            }

            insPartStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    @Override
    public void addEvent(Event event) throws DAOException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            int eventId = createEvent(connection, event);
            addParticipants(connection, event, eventId);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private boolean checkWithQuery(String query,int eventId, int managerId){
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, eventId);
            statement.setInt(1, managerId);

            try(ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean checkIfCreator(int eventId, int managerId) throws DAOException{
        String query = "SELECT eventId " +
                "FROM events " +
                "WHERE eventId=? AND creatorId = ?";

        return checkWithQuery(query,eventId,managerId);
    }

    @Override
    public boolean checkIfParticipant(int eventId, int managerId){
        String query = "SELECT eventId " +
                "FROM participants " +
                "WHERE eventId=? AND participantId = ?";

        return checkWithQuery(query,eventId,managerId);
    }

    private static void deleteParticipants(Connection connection,int eventId) throws SQLException{
        String delPartQuery = "DELETE FROM participants WHERE eventId=?";
        try(PreparedStatement delPartStatement = connection.prepareStatement(delPartQuery)) {
            delPartStatement.setInt(1, eventId);
            delPartStatement.executeUpdate();
        } catch(SQLException e){
            connection.rollback();
            throw e;
        }
    }

    private static boolean deleteRestEvent(Connection connection,int eventId) throws SQLException{
        String delEvQuery = "DELETE FROM events WHERE eventId=?";
        try(PreparedStatement delEvStatement = connection.prepareStatement(delEvQuery)) {
            delEvStatement.setInt(1, eventId);
            delEvStatement.executeUpdate();
            int delEvRows = delEvStatement.executeUpdate();
            connection.commit();
            return delEvRows != 0;
        } catch (SQLException e){
            connection.rollback();
            throw e;
        }
    }

    @Override
    public boolean delete(int eventId) throws DAOException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            deleteParticipants(connection,eventId);
            return deleteRestEvent(connection,eventId);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean decline(int eventId) throws DAOException {
        String query = "DELETE FROM participants WHERE eventId=?";
        try (Connection connection = dataSource.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, eventId);
                return statement.executeUpdate() != 0;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
