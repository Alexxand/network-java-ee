package com.github.alexxand.db.postgresql;

import com.github.alexxand.db.ManagerDAO;
import com.github.alexxand.utils.exceptions.DAOException;
import com.github.alexxand.model.Manager;
import com.github.alexxand.utils.InputStreamWrapper;
import com.google.inject.Inject;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.github.alexxand.db.postgresql.Utils.getManager;

public class PgManagerDAOImpl implements ManagerDAO {
    private DataSource dataSource;

    @Inject
    public PgManagerDAOImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public boolean checkEmail(String email) {
        String query = "SELECT email FROM managers WHERE email=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try(ResultSet resultSet = statement.executeQuery()) {
                return !resultSet.next();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean add(Manager manager) throws DAOException{
        String query = "INSERT INTO " +
                "managers(managerId, email, passwordHash, fullName, company, position, photo) " +
                "VALUES(?,?,?,?,?,?,?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, manager.getId());
            statement.setString(2, manager.getEmail());
            statement.setString(3, manager.getPasswordHash());
            statement.setString(4, manager.getFullName());
            statement.setString(5, manager.getCompany());
            statement.setString(6, manager.getPosition());

            InputStream photoStream = manager.getPhoto();
            if (!(photoStream instanceof InputStreamWrapper))
                throw new DAOException("The field photo in a manager must be instance of InputStreamWrapper");
            InputStreamWrapper photoStreamWrapper = (InputStreamWrapper) photoStream;
            statement.setBinaryStream(7, manager.getPhoto(), photoStreamWrapper.available());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean checkEmailAndPassword(String email, String passwordHash) {
        String query = "SELECT email FROM managers WHERE email=? AND passwordHash=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, passwordHash);
            try(ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Manager get(String email) {
        String query = "SELECT " +
                "managerId, email, passwordHash, fullName, company, position, photo " +
                "FROM managers " +
                "WHERE email=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next())
                    return null;

                return getManager(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public InputStream getPhoto(int managerId) {
        String query = "SELECT photo FROM managers WHERE managerId=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, managerId);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next())
                    return null;

                return resultSet.getBinaryStream("photo");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean update(Manager manager) {
        return false;
    }
}
