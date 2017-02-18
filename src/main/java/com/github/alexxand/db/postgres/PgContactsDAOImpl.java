package com.github.alexxand.db.postgres;

import com.github.alexxand.db.ContactsDAO;
import com.github.alexxand.model.ManagerContact;
import com.github.alexxand.model.ManagerContact.Type;
import com.github.alexxand.utils.exceptions.DAOException;
import com.google.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static com.github.alexxand.db.postgres.Utils.getManagerContacts;
import static com.github.alexxand.db.postgres.Utils.update;

public class PgContactsDAOImpl implements ContactsDAO{
    private DataSource dataSource;

    @Inject
    public PgContactsDAOImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    private List<ManagerContact> getContactsWithQuery(String query,int managerId) throws DAOException{
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, managerId);
            statement.setInt(2, managerId);

            try(ResultSet resultSet = statement.executeQuery()) {
                return getManagerContacts(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<ManagerContact> getContacts(int managerId) throws DAOException {
        String query = "SELECT " +
                "managerId, email, passwordHash, fullName, company, position, photo, " +
                "promoterId, receiverId, boss" +
                "FROM managers, contacts " +
                "WHERE (promoterId=? AND receiverId=managerId " +
                "OR receiverId=? AND promoterId=managerId) " +
                "AND pending=false";

        return getContactsWithQuery(query,managerId);
    }

    @Override
    public List<ManagerContact> getGroup(int managerId) {
        String query = "SELECT " +
                "managerId, email, passwordHash, fullName, company, position, photo " +
                "promoterId, receiverId, boss" +
                "FROM managers, contacts " +
                "WHERE (promoterId=? AND boss='promoter' AND receiverId=managerId " +
                "OR receiverId=? AND boss='receiver' AND promoterId=managerId) " +
                "AND pending=false";

        return getContactsWithQuery(query,managerId);
    }


    @Override
    public List<ManagerContact> getBosses(int managerId) {
        String query = "SELECT " +
                "managerId, email, passwordHash, fullName, company, position, photo " +
                "promoterId, receiverId, boss" +
                "FROM managers, contacts " +
                "WHERE (promoterId=? AND boss='receiver' AND receiverId=managerId " +
                "OR receiverId=? AND boss='promoter' AND promoterId=managerId) " +
                "AND pending=false";

        return getContactsWithQuery(query,managerId);
    }

    @Override
    public List<ManagerContact> getCompanyContacts(int managerId) {
        String query = "SELECT " +
                "managerId, email, passwordHash, fullName, company, position, photo " +
                "promoterId, receiverId, boss" +
                "FROM managers, contacts " +
                "WHERE (promoterId=? AND receiverId=managerId " +
                "OR receiverId=? AND promoterId=managerId) " +
                "AND company IN (SELECT company FROM managers WHERE managerId=?)" +
                "AND pending=false";

        return getContactsWithQuery(query,managerId);
    }

    @Override
    public List<ManagerContact> getOutgoingRequests(int managerId) {
        String query = "SELECT " +
                "managerId, email, passwordHash, fullName, company, position, photo " +
                "promoterId, receiverId, boss" +
                "FROM managers, contacts " +
                "WHERE romoterId=? AND receiverId=managerId " +
                "AND pending=true";

        return getContactsWithQuery(query,managerId);
    }

    @Override
    public List<ManagerContact> getIncomingRequests(int managerId) {
        String query = "SELECT " +
                "managerId, email, passwordHash, fullName, company, position, photo " +
                "promoterId, receiverId, boss" +
                "FROM managers, contacts " +
                "WHERE OR receiverId=? AND promoterId=managerId) " +
                "AND pending=true";

        return getContactsWithQuery(query,managerId);
    }

    @Override
    public boolean addContact(int promoterId, int receiverId, Type type) {
        String query = "INSERT INTO contacts(promoterId, receiverId, boss, pending) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, promoterId);
            statement.setInt(2, receiverId);
            switch (type){
                case NONE:
                    statement.setString(3,"none");
                    break;
                case BOSS:
                    statement.setString(3,"receiver");
                    break;
                case EMPLOYEE:
                    statement.setString(3,"promoter");
                    break;
            }
            statement.setBoolean(4,true);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean confirm(int promoterId, int receiverId) {
        String query = "UPDATE contacts " +
                "SET pending = true " +
                "WHERE promoterId=? AND receiverId=?";

        try (Connection connection = dataSource.getConnection()){
            return update(connection, query, promoterId, receiverId);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean delete(int promoterId, int receiverId) {
        String query = "DELETE FROM contacts WHERE promoterId=? AND participantId=?";
        try (Connection connection = dataSource.getConnection()) {
            return update(connection, query, promoterId, receiverId);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
