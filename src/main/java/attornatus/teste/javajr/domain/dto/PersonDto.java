package attornatus.teste.javajr.domain.dto;

import attornatus.teste.javajr.domain.entities.Address;

import java.util.ArrayList;
import java.util.List;

public class PersonDto {

    private String name;
    private Long bithDate;
    private List<Address> adresses = new ArrayList<>();

    public PersonDto(String name, Long bithDate, List<Address> adresses) {
        this.name = name;
        this.bithDate = bithDate;
        this.adresses = adresses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBithDate() {
        return bithDate;
    }

    public void setBithDate(Long bithDate) {
        this.bithDate = bithDate;
    }

    public List<Address> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Address> adresses) {
        this.adresses = adresses;
    }
}
