package com.sample.servlet.dao;

import com.sample.servlet.infrastructure.models.User;
import com.sample.servlet.infrastructure.services.DbService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User> {

    private Connection connection;

    public UserDao() {
        connection = DbService.getConnection();
    }

    @Override
    public Optional<User> get(String login) {
        if (connection != null) {
            String sql = "select * from users where login = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, login);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        return Optional.ofNullable(
                                new User(rs.getString("login"), rs.getString("password"), rs.getString("email")));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<User>();

        if (connection != null) {
            String sql = "select * from users";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        users.add(new User(rs.getString("login"), rs.getString("email"), rs.getString("password")));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    @Override
    public boolean save(User user) {
        boolean isSave = false;

        if (connection != null) {
            String sql = "insert into users (login, password, email) values (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPass());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.executeUpdate();
                isSave = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isSave;
    }

    @Override
    public boolean update(User user) {
        boolean isSave = false;

        if (connection != null) {
            String sql = "update users set login = ? email = ? password = ? where login = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getPass());
                preparedStatement.setString(4, user.getLogin());
                preparedStatement.executeUpdate();
                isSave = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isSave;
    }

    @Override
    public boolean delete(User user) {
        boolean isSave = false;

        if (connection != null) {
            String sql = "delete from users where login = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.executeUpdate();
                isSave = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isSave;
    }
}