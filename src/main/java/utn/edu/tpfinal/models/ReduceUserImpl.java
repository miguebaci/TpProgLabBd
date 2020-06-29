package utn.edu.tpfinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import utn.edu.tpfinal.projections.IReduceUser;

@AllArgsConstructor
@Data

public class ReduceUserImpl implements IReduceUser {

    @Override
    public Integer getDni() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getSurname() {
        return null;
    }

    @Override
    public void setDni(Integer dni) {

    }

    @Override
    public void setUsername(String username) {

    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setSurname(String surname) {

    }
}
