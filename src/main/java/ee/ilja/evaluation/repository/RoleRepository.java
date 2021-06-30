package ee.ilja.evaluation.repository;

import ee.ilja.evaluation.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String roleName);
}
