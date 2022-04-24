/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stackabuse.postgresql.core;

import com.stackabuse.postgresql.api.MCC_code;
import com.stackabuse.postgresql.spi.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MCCDao implements Dao<MCC_code, Integer>   {
    private static final Logger LOGGER = Logger.getLogger(MCC_code.class.getName());
    private final Optional<Connection> connection;
    private String name_table = "mccs";
    public MCCDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
        public Optional get(int id) {
        return connection.flatMap(conn -> {
            Optional<MCC_code> item = Optional.empty();
            String sql = "SELECT * FROM " + name_table + " WHERE mcc_code=" + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    Integer mcc_code = resultSet.getInt("mcc_code");
                    String mcc_desc = resultSet.getString("mcc_description");

                    item = Optional.of(new MCC_code(mcc_code, mcc_desc));

                    LOGGER.log(Level.INFO, "Found {0} in database", item.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return item;
        });
    }
        @Override
        public Collection<MCC_code> getAll() {
        Collection<MCC_code> list = new ArrayList<>();
        String sql = "SELECT * FROM " + name_table + ";";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    Integer mcc_code = resultSet.getInt("mcc_code");
                    String mcc_desc = resultSet.getString("mcc_description");

                   MCC_code item = new MCC_code(mcc_code, mcc_desc);

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
        public Optional<Integer> save(MCC_code item) {
        String message = "The MCC_codeaction to be added should not be null";
        MCC_code nonNullMCC_codeaction = Objects.requireNonNull(item, message);
        String sql = "INSERT INTO "
                + name_table
                + "SET "
                + "mcc_code = ?, "
                + "mcc_description = ?";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {

                statement.setInt(1, nonNullMCC_codeaction.getMcc_code());
                statement.setString(2, nonNullMCC_codeaction.getMcc_description());

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
                        new Object[]{nonNullMCC_codeaction, numberOfInsertedRows > 0});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

        @Override
        public void update(MCC_code item) {
        String message = "The MCC_code action to be updated should not be null";
        MCC_code nonNullMCC_codeaction = Objects.requireNonNull(item, message);
        String sql = "UPDATE "
                + name_table
                + "SET "
                + "mcc_code = ?, "
                + "mcc_description = ?, "
                + "WHERE "
                + "mcc_code = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullMCC_codeaction.getMcc_code());
                statement.setString(2, nonNullMCC_codeaction.getMcc_description());
                statement.setInt(3, nonNullMCC_codeaction.getMcc_code());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the MCC_code action updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

        @Override
        public void delete(MCC_code item) {
        String message = "The MCC_codeaction to be deleted should not be null";
        MCC_code nonNullMCC_codeaction = Objects.requireNonNull(item, message);
        String sql = "DELETE FROM MCC_codeaction WHERE id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullMCC_codeaction.getMcc_code());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the MCC_codeaction deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}
