package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.repositories.UserTypeRepository;

import java.util.List;
import java.util.Optional;


@Service
public class UserTypeService {
    private final UserTypeRepository userTypeRepository;

    @Autowired
    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public Optional<UserType> getOneUserType(Integer idUserType) {
        return userTypeRepository.findById(idUserType);
    }

    public List<UserType> getAllUserTypes(){
        return userTypeRepository.findAll();
    }

    public void addUserType(UserType newUserType) {
        userTypeRepository.save(newUserType);
    }

    public void deleteOneUserType(Integer idUserType) {
        userTypeRepository.deleteById(idUserType);
    }

    public void updateOneUserType(UserType newUserType, Integer idUserType) {
        Optional<UserType> resultUserType = getOneUserType(idUserType);
        UserType currentUserType = resultUserType.get();

        if(resultUserType != null) {
            currentUserType.setIdUserType(newUserType.getIdUserType());
            currentUserType.setUserTypeName(newUserType.getUserTypeName());
            currentUserType.setUsers(newUserType.getUsers());
            addUserType(currentUserType);
        }
    }
}
