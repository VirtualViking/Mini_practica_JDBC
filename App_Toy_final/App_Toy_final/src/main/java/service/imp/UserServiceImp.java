package service.imp;

import model.User;
import repositories.UserRepository;
import service.UserService;

import java.util.List;

public class UserServiceImp implements UserService {
  private final UserRepository userRepository;
  public UserServiceImp(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public List<User> findAllByRole(String role) {
    return userRepository.findAllByRole(role);
  }

  @Override
  public User findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }
}
