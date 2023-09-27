package attornatus.teste.javajr.service;

import attornatus.teste.javajr.domain.dto.PersonAll;
import attornatus.teste.javajr.domain.dto.PersonPost;
import attornatus.teste.javajr.domain.dto.PersonPut;
import attornatus.teste.javajr.domain.entities.Address;
import attornatus.teste.javajr.domain.entities.City;
import attornatus.teste.javajr.domain.entities.Person;
import attornatus.teste.javajr.repository.AddressRepository;
import attornatus.teste.javajr.repository.PersonRepository;
import attornatus.teste.javajr.service.exceptions.AddressException;
import attornatus.teste.javajr.service.exceptions.ObjectNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CityService cityService;
    @Autowired
    private AddressRepository addressRepository;


    public Person createPerson(PersonPost person){
        logger.info("SERVICE :: Updating person and change dto to entity.");
        Person personAtt = fromDto(person);
        personRepository.save(personAtt);
        addressRepository.saveAll(personAtt.getAdresses());
        return personAtt;
    }

    public Person findByPerson(Long id) {
      return personRepository.findById(id).orElseThrow( () -> {
          logger.error("ERROR SERVICE :: Object not found.");
          return new ObjectNotFoundException("Error: Person not found.");
      });
    }

    public List<PersonAll> findAllPerson(){
        logger.info("SERVICE :: Looking for all people");
        return personRepository.findAll().stream().map(PersonAll::toDto).collect(Collectors.toList());
    }

    public void changePerson(Long id, PersonPut personPut){
        Optional<Person> personNew = personRepository.findById(id);
        personNew.ifPresent(person -> updatePerson(person, personPut));
        personRepository.save(personNew.get());
    }

    public void updatePerson(Person person1, PersonPut person2){
        validatePriority(person2);
        person1.setName(person2.getName());
        findPriorityAddress(person1, person2);
        numberBeetwenAddress(person1, person2);
    }

    private void validatePriority(PersonPut person) throws AddressException {
        logger.info("SERVICE :: Validating address priority");
        if(isValidPriority(person)){
            logger.error("m=validatePriority stage=error");
            throw new AddressException("Error: use Y for true and N for false.");
        }
    }

    private boolean isValidPriority(PersonPut person) {
        return person.getPriorityAddress() != 'Y' && person.getPriorityAddress() != 'N';
    }

    private void numberBeetwenAddress(Person person1, PersonPut person2) throws AddressException {
        if(person2.getAddressId() > person1.getAdresses().size() || person2.getAddressId() < person1.getAdresses().size()){
            logger.error("m=numberBeetwenAddress stage=error");
            throw new AddressException("Error: insert number beetwen 1 and " + person1.getAdresses().size());
        }
        person1.getAdresses().get((int) (person2.getAddressId() -1L)).setPriorityAddress(person2.getPriorityAddress());
    }

    private void findPriorityAddress(Person person1, PersonPut person2){
        logger.info("SERVICE :: Sheaching address priority");
        for(Address x : person1.getAdresses()){
            if(person2.getPriorityAddress() == 'Y' && x.getPriorityAddress()== 'Y') {
                throw new AddressException("Error: Address is priority");
            }
            return;
        }

    }

    public Person fromDto(PersonPost p2){
        logger.info("SERVICE :: Change DTO to ENTITY");
        Person person = new Person();
        City city = cityService.findCityByID(p2.getCityId());
        Address address = new Address(null, city, person, p2.getStreet(), p2.getZipcode(), p2.getNumber(), p2.getPriorityAddress());
        if(address.getPriorityAddress()==null) address.setPriorityAddress('N');
        person.setName(p2.getName());
        person.setBithDate(p2.getBithDate());
        person.getAdresses().add(address);
        return person;
    }
}
