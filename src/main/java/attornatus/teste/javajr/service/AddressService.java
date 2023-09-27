package attornatus.teste.javajr.service;

import attornatus.teste.javajr.domain.dto.AddressPost;
import attornatus.teste.javajr.domain.entities.Address;
import attornatus.teste.javajr.domain.entities.Person;
import attornatus.teste.javajr.repository.AddressRepository;
import attornatus.teste.javajr.service.exceptions.AddressException;
import attornatus.teste.javajr.service.exceptions.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PersonService personService;
    @Autowired
    private CityService cityService;

    private final Logger logger = LoggerFactory.getLogger(AddressService.class);

    public Address createAddress(AddressPost address) throws ObjectNotFoundException {
        logger.info("** SERVICE :: Creating address and convert DTO to Entity **");
        var newAddress = fromDto(address);
        return addressRepository.save(newAddress);
    }

    public Address findByAddress(Long id){
      return addressRepository.findById(id).orElseThrow( () -> {
          logger.error("** SERVICE :: Shearching address by ID **");
          return new ObjectNotFoundException("Error: Address not found");
      });
    }

    public List<Address> findAllAddress(Long id) {
        logger.error("** SERVICE :: People found **");
        return personService.findByPerson(id).getAdresses();
    }

    public Address fromDto(AddressPost addressPost) throws ObjectNotFoundException {
        var person = personService.findByPerson(addressPost.getPersonId());
        var city = cityService.findCityByID(addressPost.getCity());
        validatePriority(addressPost);
        findPriorityAddress(person,addressPost);
        return new Address(null, city, person, addressPost.getStreet(), addressPost.getZipcode(), addressPost.getNumber(), addressPost.getPriorityAddress());
    }

    private void validatePriority(AddressPost addressPost) throws AddressException {
        logger.info("** SERVICE :: Validating address priority **");
        if(isValidPriority(addressPost)){
            logger.error("** SERVICE :: Error a validation **");
            throw new AddressException("Error: use Y for true and N for false.");
        }
    }

    private boolean isValidPriority(AddressPost addressPost) {
        return addressPost.getPriorityAddress() != 'Y' && addressPost.getPriorityAddress() != 'N';
    }

    private void findPriorityAddress(Person person1, AddressPost addressPost){
        logger.info("** SERVICE :: Sheaching address priority **");
        for(Address x : person1.getAdresses()){
            if(addressPost.getPriorityAddress() == 'Y' && x.getPriorityAddress()== 'Y') {
                throw new AddressException("Error: Address is priority");
            }
            return;
        }
    }
}
