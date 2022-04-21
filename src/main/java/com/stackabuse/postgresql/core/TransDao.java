/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stackabuse.postgresql.core;

import com.stackabuse.postgresql.api.Transaction;
import com.stackabuse.postgresql.api.Gender_train;
import com.stackabuse.postgresql.api.Transaction;
import com.stackabuse.postgresql.spi.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransDao implements Dao<Transaction, Integer> {

    private static final Logger LOGGER = Logger.getLogger(TransDao.class.getName());
    private final Optional<Connection> connection;

    public TransDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional get(int id) {
        return connection.flatMap(conn -> {
            Optional<Transaction> trans = Optional.empty();
            String sql = "SELECT * FROM transaction WHERE id = " + id;

            try (Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    Integer Transaction_id = resultSet.getInt("Transaction_id");
                    String tr_datetime = resultSet.getString("tr_datetime");
                    Integer mcc_code = resultSet.getInt("mcc_code");
                    Integer tr_type = resultSet.getInt("tr_type");
                    Integer amount = resultSet.getInt("amount");
                    Integer term_id = resultSet.getInt("term_id");

                    trans = Optional.of(new Transaction(Transaction_id, 
                            tr_datetime, mcc_code, tr_type, amount, term_id));

                    LOGGER.log(Level.INFO, "Found {0} in database", trans.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return trans;
        });
    }
    
    @Override
    public Collection<Transaction> getAll() {
        Collection<Transaction> Transactions = new ArrayList<>();
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

                    Transaction trans = new Transaction(Transaction_id,
                            tr_datetime, mcc_code, tr_type, amount, term_id);

                    Transactions.add(trans);

                    LOGGER.log(Level.INFO, "Found {0} in database", trans);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return Transactions;
    }

    @Override
    public Optional<Integer> save(Transaction Transaction) {
        String message = "The Transaction to be added should not be null";
        Transaction nonNullTransaction = Objects.requireNonNull(Transaction, message);
        String sql = "INSERT INTO "
                + "Transaction(first_name, last_name, email) "
                + "VALUES(?, ?, ?)";

        return connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullTransaction.getFirstName());
                statement.setString(2, nonNullTransaction.getLastName());
                statement.setString(3, nonNullTransaction.getEmail());

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
    public void update(Transaction Transaction) {
        String message = "The Transaction to be updated should not be null";
        Transaction nonNullTransaction = Objects.requireNonNull(Transaction, message);
        String sql = "UPDATE Transaction "
                + "SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "email = ? "
                + "WHERE "
                + "Transaction_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, nonNullTransaction.getFirstName());
                statement.setString(2, nonNullTransaction.getLastName());
                statement.setString(3, nonNullTransaction.getEmail());
                statement.setInt(4, nonNullTransaction.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the Transaction updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(int id) {
        String message = "The Transaction to be deleted should not be null";
        Transaction nonNullTransaction = Objects.requireNonNull(Transaction, message);
        String sql = "DELETE FROM Transaction WHERE id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullTransaction.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the Transaction deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}
