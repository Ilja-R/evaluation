package ee.ilja.evaluation.service;

import ee.ilja.evaluation.model.Role;
import ee.ilja.evaluation.model.User;
import ee.ilja.evaluation.model.UserData;
import ee.ilja.evaluation.repository.RoleRepository;
import ee.ilja.evaluation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public User saveNewUser(User user, String roleName){

        User foundUser = userRepository.findByEmail(user.getEmail());

        if (foundUser != null) {
            return activateUser(user, foundUser);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Role role = roleRepository.findByName(roleName);

        if (role == null) {
            role = new Role(roleName);
        }

        user.addRole(role);

        user.setIsActive(true);

        return userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User updateUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    public void deactivateUser(Long userId){
        User user = userRepository.getById(userId);
        user.setIsActive(false);
        userRepository.save(user);
    }

    public List<User> listAll(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).get();
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    private User activateUser(User user, User foundUser) {

        user.setId(foundUser.getId());
        user.addRole(roleRepository.findByName("User"));
        user.setUserData(null);
        user.setIsActive(true);
        return updateUser(user);

    }

}
