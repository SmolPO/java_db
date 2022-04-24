/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stackabuse.postgresql.core;

import com.stackabuse.postgresql.api.Tr_type;
import com.stackabuse.postgresql.spi.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TypesDao implements Dao<Tr_type, Integer>   {
    private static final Logger LOGGER = Logger.getLogger(Tr_type.class.getName());
    private final Optional<Connection> connection;
    private String name_table = "types";

    public TypesDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
        public Optional get(int id) {
        return connection.flatMap(conn -> {
            Optional<Tr_type> item = Optional.empty();
            String sql = "SELECT * FROM " + name_table + " WHERE tr_type=" + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    Integer tr_type = resultSet.getInt("tr_type");
                    String tr_description = resultSet.getString("tr_description");

                    item = Optional.of(new Tr_type(tr_type, tr_description));

                    LOGGER.log(Level.INFO, "Found {0} in database", item.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return item;
        });
    }
        @Override
        public Collection<Tr_type> getAll() {
        Collection<Tr_type> list = new ArrayList<>();
        String sql = "SELECT * FROM " + name_table + ";";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    Integer tr_type = resultSet.getInt("tr_type");
                    String tr_description = resultSet.getString("tr_description");

                   Tr_type item = new Tr_type(tr_type, tr_description);

                    list.add(item);

                    LOGGER.log(Level.INFO, "Found {0} in database", item);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return list;
    }

        @Override
        public Optional<Integer> save(Tr_type item) {
        String message = "The Tr_typeaction to be added should not be null";
        Tr_type nonNullTr_typeaction = Objects.requireNonNull(item, message);
        String sql = "INSERT INTO "
                + name_table
                + "SET "
                + "tr_type = ?, "
                + "tr_description = ?";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {

                statement.setInt(1, nonNullTr_typeaction.getTr_type());
                statement.setString(2, nonNullTr_typeaction.getTr_description());

                int numberOfInsertedRows = statement.executeUpdate();

                //Retrieve the auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

                LOGGER.log(Level.INFO, "{0} created successfully? {1}",
                        new Object[]{nonNullTr_typeaction, numberOfInsertedRows > 0});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

        @Override
        public void update(Tr_type item) {
        String message = "The Tr_type action to be updated should not be null";
        Tr_type nonNullTr_typeaction = Objects.requireNonNull(item, message);
        String sql = "UPDATE "
                + name_table
                + "SET "
                + "tr_type = ?, "
                + "gender = ? "
                + "WHERE "
                + "tr_type= ?;";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullTr_typeaction.getTr_type());
                statement.setString(2, nonNullTr_typeaction.getTr_description());
                statement.setInt(3, nonNullTr_typeaction.getTr_type());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the Tr_type action updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

        @Override
        public void delete(Tr_type item) {
        String message = "The type action to be deleted should not be null";
        Tr_type nonNullTr_typeaction = Objects.requireNonNull(item, message);
        String sql = "DELETE FROM " + name_table + " WHERE tr_type=?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullTr_typeaction.getTr_type());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the Tr_type action deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}
