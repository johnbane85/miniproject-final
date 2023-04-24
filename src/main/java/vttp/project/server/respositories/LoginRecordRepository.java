package vttp.project.server.respositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.project.server.models.LoginRecord;

import static vttp.project.server.respositories.Queries.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Repository
public class LoginRecordRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void updateLoginRecord(String user_id, Date last_login) {

    final SqlRowSet rs_1 = jdbcTemplate.queryForRowSet(SQL_CHECK_LOGIN_RECORD, user_id);

    if (rs_1.next()) {
      if (!rs_1.getBoolean("auth_state")) {
        jdbcTemplate.update(SQL_INSERT_LOGIN_RECORD, user_id, 1, last_login);
      } else {
        jdbcTemplate.update(SQL_UPDATE_LOGIN_RECORD, last_login, user_id);
      }
    }
  }

  public List<LoginRecord> getLoginRecord(String user_id) {

    final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_GET_LOGIN_RECORD, user_id);

    final List<LoginRecord> records = new LinkedList<>();

    while (rs.next())
      records.add(LoginRecord.toLoginRecord(rs));

    return records;
  }

}
