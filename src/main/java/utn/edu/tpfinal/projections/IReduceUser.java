package utn.edu.tpfinal.projections;


public interface IReduceUser {
    Integer getDni();

    String getUsername();

    String getName();

    String getSurname();

    void setDni(Integer dni);

    void setUsername(String username);

    void setName(String name);

    void setSurname(String surname);
}
