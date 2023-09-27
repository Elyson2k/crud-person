package attornatus.teste.javajr.domain.dto;

import javax.validation.constraints.NotEmpty;

public class PersonPost {


    @NotEmpty
    private String name;
    @NotEmpty
    private Long birthDate;
    @NotEmpty
    private Long cityId;

    @NotEmpty
    private String street;

    @NotEmpty
    private String zipcode;

    @NotEmpty
    private Long number;

    @NotEmpty
    private Character priorityAddress;

    public PersonPost(String name, Long birthDate, Long cityId, String street, String zipcode, Long number, Character priorityAddress) {
        this.name = name;
        this.birthDate = birthDate;
        this.cityId = cityId;
        this.street = street;
        this.zipcode = zipcode;
        this.number = number;
        this.priorityAddress = priorityAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBithDate() {
        return birthDate;
    }

    public void setBithDate(Long birthDate) {
        this.birthDate = birthDate;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Character getPriorityAddress() {
        return priorityAddress;
    }

    public void setPriorityAddress(Character priorityAddress) {
        this.priorityAddress = priorityAddress;
    }

    @Override
    public String toString() {
        return "PersonPost{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", cityId=" + cityId +
                ", street='" + street + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", number=" + number +
                ", priorityAddress=" + priorityAddress +
                '}';
    }
}
