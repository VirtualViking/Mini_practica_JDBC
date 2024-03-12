package repositories.imp;

import config.DatabaseConnection;
import model.Category;
import model.Toy;
import repositories.ToyRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToyRepositoryImp implements ToyRepository {
  @Override
  public List<Toy> findAll() {
    Connection connection = DatabaseConnection.getConnection();
    try(Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery("SELECT * FROM toys");
      List<Toy> toys = new ArrayList<>();
      while (resultSet.next()) {
        Toy toy = createToy(resultSet);
        toys.add(toy);
      }
      return toys;
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public Toy findById(Long id) {
    Connection connection = DatabaseConnection.getConnection();
    try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM toys WHERE id = ?")) {
      statement.setLong(1, id);

      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return createToy(resultSet);
      }
      return null;
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void save(Toy toy) {
Connection connection = DatabaseConnection.getConnection();
    try(PreparedStatement statement = connection.prepareStatement("INSERT INTO toys (name, price, stock, category) VALUES (?, ?, ?, ?)")) {
      statement.setString(1, toy.getToyName());
      statement.setDouble(2, toy.getToyPrice());
      statement.setInt(3, toy.getToyAmount());
      statement.setString(4, toy.getToyCategory().toString());
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }

  }

  @Override
  public void update(Toy toy) {
    Connection connection = DatabaseConnection.getConnection();
    try(PreparedStatement statement = connection.prepareStatement("UPDATE toys SET name = ?, price = ?, stock = ?, category = ? WHERE id = ?")) {
      statement.setString(1, toy.getToyName());
      statement.setDouble(2, toy.getToyPrice());
      statement.setInt(3, toy.getToyAmount());
      statement.setString(4, toy.getToyCategory().toString());
      statement.setLong(5, toy.getId());
      statement.executeUpdate();
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  private Toy createToy(ResultSet resultSet) throws SQLException {
    Toy toy = new Toy();
    toy.setId(resultSet.getLong("id"));
    toy.setToyName(resultSet.getString("name"));
    toy.setToyPrice(resultSet.getDouble("price"));
    toy.setToyAmount(resultSet.getInt("stock"));
    toy.setToyCategory(Category.valueOf(resultSet.getString("category")));
    return toy;
  }
}
