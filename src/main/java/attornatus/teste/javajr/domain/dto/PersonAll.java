package attornatus.teste.javajr.domain.dto;

import attornatus.teste.javajr.domain.entities.Person;

public class PersonAll {

    private String name;
    private Long bithDate;



    public PersonAll(String name, Long bithDate) {
        this.name = name;
        this.bithDate = bithDate;
    }

    public static PersonAll toDto(Person person) {
        return new PersonAll(person.getName(), person.getBithDate());
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
}
