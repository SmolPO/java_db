/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stackabuse.postgresql.core;

import com.stackabuse.postgresql.api.Trans;
import com.stackabuse.postgresql.api.Gender_train;
import com.stackabuse.postgresql.spi.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TransDao implements Dao<Trans, Integer> {

    private static final Logger LOGGER = Logger.getLogger(TransDao.class.getName());
    private final Optional<Connection> connection;

    private String name_table = "transactions";
    public TransDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional get(int id) { /// получить все операции одного клиента
        return connection.flatMap(conn -> {
            Optional<Trans> item = Optional.empty();
            String sql = "SELECT * FROM " + name_table + " WHERE customer_id=" + id;

            try (Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    Integer Transaction_id = resultSet.getInt("Transaction_id");
                    String tr_datetime = resultSet.getString("tr_datetime");
                    Integer mcc_code = resultSet.getInt("mcc_code");
                    Integer tr_type = resultSet.getInt("tr_type");
                    Integer amount = resultSet.getInt("amount");
                    Integer term_id = resultSet.getInt("term_id");

                    item = Optional.of(new Trans(Transaction_id,
                            tr_datetime, mcc_code, tr_type, amount, term_id));

                    LOGGER.log(Level.INFO, "Found {0} in database", item.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return item;
        });
    }
    
    @Override
    public Collection<Trans> getAll() {
        Collection<Trans> list = new ArrayList<>();
        String sql = "SELECT * FROM Transaction";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    Integer Transaction_id = resultSet.getInt("Transaction_id");
                    String tr_datetime = resultSet.getString("tr_datetime");
                    Integer mcc_code = resultSet.getInt("mcc_code");
                    Integer tr_type = resultSet.getInt("tr_type");
                    Integer amount = resultSet.getInt("amount");
                    Integer term_id = resultSet.getInt("term_id");

                    Trans item = new Trans(Transaction_id,
                            tr_datetime, mcc_code, tr_type, amount, term_id);

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
    public Optional<Integer> save(Trans item) {
        String message = "The Transaction to be added should not be null";
        Trans nonNullTransaction = Objects.requireNonNull(item, message);
        String sql = "INSERT INTO "
                + "SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "email = ? "
                + "WHERE "
                + "Transaction_id = ?";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {

                statement.setInt(1, nonNullTransaction.getCustomer_id());
                statement.setString(2, nonNullTransaction.getTr_datetime());
                statement.setInt(3, nonNullTransaction.getMcc_code());
                statement.setInt(4, nonNullTransaction.getTr_type());
                statement.setInt(5, nonNullTransaction.getAmount());
                statement.setInt(6, nonNullTransaction.getTerm_id());

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
                        new Object[]{nonNullTransaction, numberOfInsertedRows > 0});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }

            return generatedId;
        });
    }

    @Override
    public void update(Trans item) {
        String message = "The Transaction to be updated should not be null";
        Trans nonNullTransaction = Objects.requireNonNull(item, message);
        String sql = "UPDATE Transaction "
                + "SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "email = ? "
                + "WHERE "
                + "Transaction_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullTransaction.getCustomer_id());
                statement.setString(2, nonNullTransaction.getTr_datetime());
                statement.setInt(3, nonNullTransaction.getMcc_code());
                statement.setInt(4, nonNullTransaction.getTr_type());
                statement.setInt(5, nonNullTransaction.getAmount());
                statement.setInt(6, nonNullTransaction.getTerm_id());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the Transaction updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Trans item) {
        String message = "The Transaction to be deleted should not be null";
        Trans nonNullTransaction = Objects.requireNonNull(item, message);
        String sql = "DELETE FROM Transaction WHERE id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullTransaction.getCustomer_id());
                statement.setString(2, nonNullTransaction.getTr_datetime());
                statement.setInt(3, nonNullTransaction.getMcc_code());
                statement.setInt(4, nonNullTransaction.getTr_type());
                statement.setInt(5, nonNullTransaction.getAmount());
                statement.setInt(6, nonNullTransaction.getTerm_id());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the Transaction deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}
