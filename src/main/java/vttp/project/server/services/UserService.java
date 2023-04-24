package vttp.project.server.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.project.server.models.User;
import vttp.project.server.respositories.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepo;

  @Transactional(rollbackFor = { UserException.class })
  public String createUser(String username, String password, String email) throws UserException {

    String user_id = UUID.randomUUID().toString().substring(0, 8);
    String response;

    try {
      response = userRepo.createUser(user_id, username, password, email);
    } catch (Exception ex) {
      throw new UserException(ex.getMessage(), ex);
    }
    return response;
  }

  public Boolean userLogin(String email, String password) {

    Boolean auth_state = userRepo.userLogin(email, password);
    return auth_state;
  }

  public User getUser(String username) {

    return userRepo.getUser(username);
  }

  public User getUserByEmail(String email) {

    return userRepo.getUserByEmail(email);
  }

  @Transactional(rollbackFor = { UserException.class })
  public String editUser(String user_id, String username, String password, String email) throws UserException {

    String response;

    try {

      response = userRepo.editUser(user_id, username, password, email);

    } catch (Exception ex) {
      throw new UserException(ex.getMessage(), ex);
    }
    return response;
  }

  @Transactional(rollbackFor = { UserException.class })
  public String deleteUser(String user_id) throws UserException {
    String response;

    try {
      response = userRepo.deleteUser(user_id);

    } catch (Exception ex) {
      throw new UserException(ex.getMessage(), ex);
    }
    return response;
  }

}
