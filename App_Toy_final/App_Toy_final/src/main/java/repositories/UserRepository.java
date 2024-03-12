package repositories;

import model.User;

import java.util.List;

public interface UserRepository {
  List<User> findAll();
  List<User> findAllByRole(String role);
  User findById(Long id);
  void save(User user); //9
}
