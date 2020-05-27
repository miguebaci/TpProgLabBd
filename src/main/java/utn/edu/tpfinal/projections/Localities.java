package utn.edu.tpfinal.projections;

import utn.edu.tpfinal.models.Province;

public interface Localities {
    Integer getPrefix();
    String getLocalityName();
    Province getFrom();
}
