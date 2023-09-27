package attornatus.teste.javajr.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long bithDate;
    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private List<Address> adresses = new ArrayList<>();

    public Person(Long id, String name, Long bithDate) {
        this.id = id;
        this.name = name;
        this.bithDate = bithDate;
    }

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
