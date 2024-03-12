package service;

import model.User;

import java.util.List;

public interface UserService {
  List<User> findAll();
  List<User> findAllByRole(String role);
  User findById(Long id);
  void save(User user);
}
