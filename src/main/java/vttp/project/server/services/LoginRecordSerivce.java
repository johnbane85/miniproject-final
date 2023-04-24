package vttp.project.server.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.project.server.models.LoginRecord;
import vttp.project.server.respositories.LoginRecordRepository;

@Service
public class LoginRecordSerivce {

  @Autowired
  private LoginRecordRepository recordRepo;

  public void updateLoginRecord(String user_id) {

    Date last_login = new Date();

    recordRepo.updateLoginRecord(user_id, last_login);
  }

  public List<LoginRecord> getLoginRecord(String user_id) {

    return recordRepo.getLoginRecord(user_id);
  }

}
