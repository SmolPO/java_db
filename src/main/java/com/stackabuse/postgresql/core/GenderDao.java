/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stackabuse.postgresql.core;

import com.stackabuse.postgresql.api.Gender_train;
import com.stackabuse.postgresql.api.Gender_train;
import com.stackabuse.postgresql.spi.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GenderDao implements Dao<Gender_train, Integer>   {
        private static final Logger LOGGER = Logger.getLogger(Gender_train.class.getName());
        private final Optional<Connection> connection;
        private String name_table = "genders";
    public GenderDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
        public Optional get(int id) {
        return connection.flatMap(conn -> {
            Optional<Gender_train> item = Optional.empty();
            String sql = "SELECT * FROM " + name_table + " WHERE customer_id=" + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    Integer customer_id = resultSet.getInt("customer_id");
                    Integer gender = resultSet.getInt("gender");

                    item = Optional.of(new Gender_train(customer_id, gender));

                    LOGGER.log(Level.INFO, "Found {0} in database", item.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return item;
        });
    }
        @Override
        public Collection<Gender_train> getAll() {
        Collection<Gender_train> list = new ArrayList<>();
        String sql = "SELECT * FROM " + name_table + ";";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    Integer customer_id = resultSet.getInt("customer_id");
                    Integer gender = resultSet.getInt("gender");

                   Gender_train item = new Gender_train(customer_id, gender);

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
        public Optional<Integer> save(Gender_train item) {
        String message = "The Gendern to be added should not be null";
        Gender_train nonNullGender_trainaction = Objects.requireNonNull(item, message);
        String sql = "INSERT INTO "
                + name_table
                + " VALUES ("
                + "customer_id = ?, "
                + "gender = ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {

                statement.setInt(1, nonNullGender_trainaction.getCustomer_id());
                statement.setInt(2, nonNullGender_trainaction.getGender());

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
                        new Object[]{nonNullGender_trainaction, numberOfInsertedRows > 0});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

        @Override
        public void update(Gender_train item) {
        String message = "The Gender to be updated should not be null";
        Gender_train nonNullGender_trainaction = Objects.requireNonNull(item, message);
        String sql = "UPDATE "
                + name_table
                + "SET "
                + "customer_id = ?, "
                + "gender = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullGender_trainaction.getCustomer_id());
                statement.setInt(2, nonNullGender_trainaction.getGender());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the Gender action updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

        @Override
        public void delete(Gender_train item) {
        String message = "The Gender_trainaction to be deleted should not be null";
        Gender_train nonNullGender_trainaction = Objects.requireNonNull(item, message);
        String sql = "DELETE FROM " + name_table + " WHERE customer_id=?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullGender_trainaction.getCustomer_id());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the Gender_trainaction deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}
