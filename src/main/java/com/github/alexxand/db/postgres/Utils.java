package com.github.alexxand.db.postgres;

import com.github.alexxand.model.Event;
import com.github.alexxand.model.Manager;
import com.github.alexxand.model.ManagerContact;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.alexxand.model.ManagerContact.Type.BOSS;
import static com.github.alexxand.model.ManagerContact.Type.EMPLOYEE;
import static com.github.alexxand.model.ManagerContact.Type.NONE;

class Utils {

    private Utils(){
    }

    static String prepareLongInsertQuery(String firstPart, int nRows, int nArgs){

        StringBuilder queryStringBuilder = new StringBuilder(firstPart);

        for(int i = 0; i < nRows; ++i){
            queryStringBuilder.append("(");
            for(int j = 0; j < nArgs - 1; ++j)
                queryStringBuilder.append("?, ");
            queryStringBuilder.append("?)");
            if (i < nRows - 1)
                queryStringBuilder.append(",\n");
        }

        return queryStringBuilder.toString();
    }

    static Manager getManager(ResultSet resultSet) throws SQLException{
        int managerId = resultSet.getInt("managerId");
        String email = resultSet.getString("email");
        String passwordHash = resultSet.getString("passwordHash");
        String fullName = resultSet.getString("fullName");
        String company = resultSet.getString("company");
        String position = resultSet.getString("position");
        InputStream photo = resultSet.getBinaryStream("photo");

        return Manager.builder().
                id(managerId).
                email(email).
                passwordHash(passwordHash).
                fullName(fullName).
                company(company).
                position(position).
                photo(photo).
                build();
    }

    static List<Manager> getManagers(ResultSet resultSet) throws SQLException{
        List<Manager> managerList = new ArrayList<>();

        while (resultSet.next()) {
            Manager contact = getManager(resultSet);
            managerList.add(contact);
        }

        return managerList;
    }

    private static ManagerContact.Type getContactType(ResultSet resultSet) throws SQLException{
        int managerId = resultSet.getInt("managerId");
        int promoterId = resultSet.getInt("promoterId");
        int receiverId = resultSet.getInt("receiverId");
        String boss = resultSet.getString("boss");

        switch (boss){
            case "none":
                return NONE;
            case "promoter":
                return managerId == promoterId ? BOSS
                        : managerId == receiverId ? EMPLOYEE
                        : null;
            case "receiver":
                return managerId == promoterId ? EMPLOYEE
                        : managerId == receiverId ? BOSS
                        : null;
            default:
                return null;
        }
    }

    static List<ManagerContact> getManagerContacts(ResultSet resultSet) throws SQLException{
        List<ManagerContact> managerContactList = new ArrayList<>();

        while (resultSet.next()) {
            Manager contact = getManager(resultSet);
            ManagerContact.Type type = getContactType(resultSet);
            ManagerContact managerContact = new ManagerContact(contact,type);

            managerContactList.add(managerContact);
        }

        return managerContactList;
    }

    static Event getEvent(ResultSet resultSet) throws SQLException{
        int eventId = resultSet.getInt("eventId");
        LocalDateTime dateTime = resultSet.getTimestamp("date").toLocalDateTime();
        String text = resultSet.getString("text");
        int creatorId = resultSet.getInt("creatorId");
        Integer[] participantIdsArray = (Integer[]) resultSet.getArray("array_agg").getArray();
        List<Integer> participantIds = Arrays.asList(participantIdsArray);
        return Event.builder().
                eventId(eventId).
                dateTime(dateTime).
                text(text).
                creatorId(creatorId).
                participantIds(participantIds).
                build();
    }

    static boolean update(Connection connection, String query, int firstArg, int secondArg) throws SQLException{
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, firstArg);
            statement.setInt(2, secondArg);
            return statement.executeUpdate() != 0;
        }
    }
}
