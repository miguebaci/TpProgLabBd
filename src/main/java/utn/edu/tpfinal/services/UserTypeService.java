package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.repositories.UserTypeRepository;

import java.util.List;

import static java.util.Objects.isNull;


@Service
public class UserTypeService {
    private final UserTypeRepository userTypeRepository;

    @Autowired
    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public List<UserType> getAll(String name){
        if(isNull(name)) {
            return userTypeRepository.findAll();
        }
        return userTypeRepository.findByName(name);
    }

    public void addUserType(UserType userType) {
        userTypeRepository.save(userType);
    }
}
