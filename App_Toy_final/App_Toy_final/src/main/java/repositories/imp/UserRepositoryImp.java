package repositories.imp;

import config.DatabaseConnection;
import model.User;
import repositories.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImp implements UserRepository {
  @Override
  public List<User> findAll() {
    Connection connection = DatabaseConnection.getConnection();
    try(Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
      return createUsers(resultSet);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public List<User> findAllByRole(String role) {
    Connection connection = DatabaseConnection.getConnection();
    try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE role = ?")) {
      statement.setString(1, role);
      ResultSet resultSet = statement.executeQuery();
      return createUsers(resultSet);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  private List<User> createUsers(ResultSet resultSet) throws SQLException {
    List<User> users = new ArrayList<>();
    while (resultSet.next()) {
      User user = new User();
      user.setId(resultSet.getLong("id"));
      user.setName(resultSet.getString("name"));
      user.setRole(resultSet.getString("role"));
      users.add(user);
    }
    return users;
  }

  @Override
  public User findById(Long id) {
    Connection connection = DatabaseConnection.getConnection();
    try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setRole(resultSet.getString("role"));
        return user;
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }

    return null;
  }

  @Override
  public void save(User user) {
    Connection connection = DatabaseConnection.getConnection();
    try(PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, role) VALUES (?, ?)")) {
      statement.setString(1, user.getName());
      statement.setString(2, user.getRole());
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

}
