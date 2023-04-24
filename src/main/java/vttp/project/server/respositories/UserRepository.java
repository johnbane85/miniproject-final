package vttp.project.server.respositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.project.server.models.User;

import static vttp.project.server.respositories.Queries.*;

@Repository
public class UserRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public String createUser(String user_id, String username, String password, String email) {

    final SqlRowSet rs_1 = jdbcTemplate.queryForRowSet(SQL_CHECK_USER_BY_USERNAME,
        username);
    if (rs_1.next()) {
      if (rs_1.getBoolean("auth_state")) {
        return "Username: %s already exist".formatted(username);
      }
    }

    final SqlRowSet rs_2 = jdbcTemplate.queryForRowSet(SQL_CHECK_USER_BY_EMAIL,
        email);
    if (rs_2.next()) {
      if (rs_2.getBoolean("auth_state")) {
        return "Email: %s already exist".formatted(email);
      }
    }

    int count = jdbcTemplate.update(SQL_INSERT_USER, user_id, username, password, email);
    if (count > 0) {
      // return "User %s has been Created".formatted(username);
      return "Success";
    } else {
      return "Failed";
    }
  }

  public Boolean userLogin(String email, String password) {
    final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_AUTHENTICATE_USER,
        email, password);

    if (rs.next()) {
      return rs.getBoolean("auth_state");
    }
    return false;
  }

  public User getUser(String username) {
    final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SEARCH_USER_BY_USERNAME,
        username);

    if (rs.next()) {
      User user = User.toUser(rs);
      return user;
    }
    return null;
  }

  public User getUserByEmail(String email) {
    final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SEARCH_USER_BY_EMAIL,
        email);

    if (rs.next()) {
      User user = User.toUser(rs);
      return user;
    }
    return null;
  }

  public String editUser(String user_id, String username, String password, String email) {

    // System.out.println("user_id: " + user_id + ", username: " + username);

    final SqlRowSet rs_1 = jdbcTemplate.queryForRowSet(SQL_CHECK_USER_BY_USER_ID,
        user_id);
    if (rs_1.next()) {
      if (!rs_1.getBoolean("auth_state")) {
        return "User account does not exist";
      }
    }

    // update users
    int count = jdbcTemplate.update(SQL_UPDATE_USER_BY_USER_ID, username, password, email, user_id);

    if (count > 0) {

      // update comments
      jdbcTemplate.update(SQL_UPDATE_COMMENTS_BY_USER_ID, username, user_id);

      return "User: %s's profile was updated successfully".formatted(username);
    } else {
      return "User profile was not changed";
    }

  }

  public String deleteUser(String user_id) {

    int count = jdbcTemplate.update(SQL_DELETE_USER_BY_USER_ID, user_id);
    if (count > 0) {
      return "Success";
    } else {
      return "Failed";
    }
  }

}
