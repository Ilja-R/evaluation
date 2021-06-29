package ee.ilja.evaluation.service;

import ee.ilja.evaluation.model.UserData;
import ee.ilja.evaluation.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {

    @Autowired
    private UserDataRepository userDataRepository;

    public UserData getUserDataByUserId(Long id) {
        UserData userData = userDataRepository.findByUserId(id);
        if (userData == null) {
            userData = new UserData();
        }
        return userData;
    }

}
