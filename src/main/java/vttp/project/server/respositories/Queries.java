package vttp.project.server.respositories;

public interface Queries {

  public static final String SQL_INSERT_USER = "insert into users(user_id, username, password,email) values(?,?,?,?)";

  public static final String SQL_SEARCH_USER_BY_USERNAME = "select user_id, username, password, email from users where username = ?";

  public static final String SQL_SEARCH_USER_BY_EMAIL = "select user_id, username, password, email from users where email = ?";

  public static final String SQL_AUTHENTICATE_USER = "select count(*) as auth_state from users where email = ? and password = ?";

  public static final String SQL_CHECK_USER_BY_USERNAME = "select count(*) as auth_state from users where username = ?";

  public static final String SQL_CHECK_USER_BY_USER_ID = "select count(*) as auth_state from users where user_id = ?";

  public static final String SQL_CHECK_USER_BY_EMAIL = "select count(*) as auth_state from users where email = ?";

  public static final String SQL_INSERT_COMMENT = "insert into comments(comment_id, user_id, username, location_name, text, comment_date) values(?,?,?,?,?,?)";

  public static final String SQL_GET_COMMENTS_BY_LOCATION_NAME = "select comment_id, user_id, username, location_name, text, comment_date from comments where location_name = ?";

  public static final String SQL_UPDATE_USER_BY_USER_ID = "UPDATE users SET username = ?, password = ? , email = ? WHERE user_id = ?";

  public static final String SQL_UPDATE_COMMENTS_BY_USER_ID = "UPDATE comments SET username = ? WHERE user_id = ?";

  public static final String SQL_UPDATE_LOGIN_RECORD = "UPDATE login_record SET login_count = login_count + 1, last_login = ? WHERE user_id = ?";

  public static final String SQL_INSERT_LOGIN_RECORD = "insert into login_record(user_id, login_count, last_login) values(?,?,?)";

  public static final String SQL_GET_LOGIN_RECORD = "select user_id, login_count, last_login from login_record WHERE user_id = ?";

  public static final String SQL_CHECK_LOGIN_RECORD = "select count(*) as auth_state from login_record where user_id = ?";

  public static final String SQL_DELETE_USER_BY_USER_ID = "DELETE from users where user_id = ?";

}
