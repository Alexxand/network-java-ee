package com.github.alexxand.db.postgres;

import com.github.alexxand.db.ManagerDAO;
import com.github.alexxand.model.ManagerInf;
import com.github.alexxand.model.Photo;
import com.github.alexxand.utils.exceptions.DAOException;
import com.github.alexxand.model.Manager;
import com.google.inject.Inject;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.github.alexxand.db.postgres.Utils.getManager;

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
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void add(ManagerInf managerInf, Photo photo) throws DAOException{
        String query = "INSERT INTO " +
                "managers(email, passwordHash, fullName, company, position, photo) " +
                "VALUES(?,?,?,?,?,?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, managerInf.getEmail());
            statement.setString(2, managerInf.getPasswordHash());
            statement.setString(3, managerInf.getFullName());
            statement.setString(4, managerInf.getCompanyInf().getCompany());
            statement.setString(5, managerInf.getCompanyInf().getPosition());
            statement.setBinaryStream(6, photo.getInputStream(), photo.getSize());
            statement.executeUpdate();
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
    public String getPasswordHash(String email) {
        String query = "SELECT passwordHash FROM managers WHERE email=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next())
                    return null;

                return resultSet.getString("passwordHash");
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
