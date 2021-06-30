package ee.ilja.evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import ee.ilja.evaluation.model.Role;
import ee.ilja.evaluation.model.User;
import ee.ilja.evaluation.repository.RoleRepository;
import ee.ilja.evaluation.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("test_01@test");
        user.setPassword("password1");
        user.setUsername("Wow");
        user.setIsActive(true);

        User savedUser = userRepository.save(user);
        User userFromDB = entityManager.find(User.class, savedUser.getId());

        assertThat(userFromDB.getEmail().equals(user.getEmail()));
    }

    @Test
    public void saveUserWithRoles() {

        User user = new User();
        user.setEmail("test_01@test");
        user.setPassword("password1");
        user.setUsername("Wow");
        user.setIsActive(true);

        user.addRole(new Role ("Admin"));
        user.addRole(new Role("User"));
        userRepository.save(user);

        User foundUser = entityManager.find(User.class, user.getId());

        assertThat(foundUser.getRoles().size()).isEqualTo(2);

    }

}
