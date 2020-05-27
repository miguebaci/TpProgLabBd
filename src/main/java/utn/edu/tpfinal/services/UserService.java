package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAll(Integer id){
        if(isNull(id)) {
            return userRepository.findAll();
        }
        return userRepository.getById(id);
    }
    public void addUser(User newUser) {
        userRepository.save(newUser);
    }

    public User login(String username, String password) {
        User user = userRepository.userExists(username, password);
        return Optional.ofNullable(user).orElseThrow(() -> new RuntimeException("User does not exists"));
    }
}
