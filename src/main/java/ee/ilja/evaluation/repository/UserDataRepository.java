package ee.ilja.evaluation.repository;

import ee.ilja.evaluation.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    @Query("SELECT u.userData from User u where u.id = ?1")
    public UserData findByUserId(Long id);
}
