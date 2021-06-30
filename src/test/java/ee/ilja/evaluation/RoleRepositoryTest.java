package ee.ilja.evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import ee.ilja.evaluation.model.Role;
import ee.ilja.evaluation.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateRole() {
        Role adminRole = new Role("Admin");
        Role userRole = new Role("User");
        List<Role> roles =new ArrayList<>();
        roles.add(adminRole);
        roles.add(userRole);

        roleRepository.saveAll(roles);

        List<Role> listRoles = roleRepository.findAll();

        assertThat(listRoles.size()).isEqualTo(2);
    }
}
