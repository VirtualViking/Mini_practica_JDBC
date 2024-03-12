package repositories.imp;

import config.DatabaseConnection;
import model.Sale;
import model.SaleDetail;
import repositories.SaleRepository;
import repositories.ToyRepository;
import repositories.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleRepositoryImp implements SaleRepository {
  private ToyRepository toyRepository;
  private UserRepository userRepository;
  public SaleRepositoryImp(ToyRepository toyRepository, UserRepository userRepository) {
    this.toyRepository = toyRepository;
    this.userRepository = userRepository;
  }
  @Override
  public List<Sale> findAll() {
    Connection connection = DatabaseConnection.getConnection();
    try(Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery("SELECT * FROM sales");
      return createSales(resultSet);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void saveSale(Sale sale) {
    Connection connection = DatabaseConnection.getConnection();
    try {
      try(PreparedStatement statement = connection.prepareStatement("INSERT INTO sales (customer_id, employee_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
        statement.setLong(1, sale.getCustomer().getId());
        statement.setLong(2, sale.getEmployee().getId());
        statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
          sale.setId(generatedKeys.getLong(1));
        }
      }
      saveSaleDetail(sale);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }

  }
  private List<Sale> createSales(ResultSet resultSet) throws SQLException {
    List<Sale> sales = new ArrayList<>();
    while (resultSet.next()) {
      Sale sale = new Sale();
      sale.setId(resultSet.getLong("id"));
      sale.setSaleDetails(getSaleDetail(sale.getId()));
      sale.setCustomer(userRepository.findById(resultSet.getLong("customer_id")));
      sale.setEmployee(userRepository.findById(resultSet.getLong("employee_id")));
      sales.add(sale);
    }
    return sales;
  }

  private List<SaleDetail> getSaleDetail(Long saleId) {
    Connection connection = DatabaseConnection.getConnection();
    try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM sale_details WHERE sale_id = ?")) {
      statement.setLong(1, saleId);
      ResultSet resultSet = statement.executeQuery();
      List<SaleDetail> saleDetails = new ArrayList<>();
      while (resultSet.next()) {
        SaleDetail saleDetail = new SaleDetail();
        saleDetail.setId(resultSet.getLong("id"));
        saleDetail.setQuantity(resultSet.getInt("quantity"));
        saleDetail.setToy(toyRepository.findById(resultSet.getLong("toy_id")));
        saleDetails.add(saleDetail);
      }
      return saleDetails;
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void saveSaleDetail(Sale sale) {
    Connection connection = DatabaseConnection.getConnection();
    try {
      try(PreparedStatement statement = connection.prepareStatement("INSERT INTO sale_details (sale_id, toy_id, quantity) VALUES (?, ?, ?)")) {
        for (SaleDetail saleDetail : sale.getSaleDetails()) {
          statement.setLong(1, sale.getId());
          statement.setLong(2, saleDetail.getToy().getId());
          statement.setInt(3, saleDetail.getQuantity());
          statement.executeUpdate();
        }
      }
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }
}
