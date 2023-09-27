package attornatus.teste.javajr.service;

import attornatus.teste.javajr.domain.dto.AddressPost;
import attornatus.teste.javajr.domain.dto.PersonPut;
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
        logger.info("m=createAddress stage=init");
        var newAddress = fromDto(address);
        logger.info("m=createAddress stage=init");
        return addressRepository.save(newAddress);
    }

    public Address findByAddress(Long id) throws Throwable {
      return addressRepository.findById(id).orElseThrow( () -> {
          logger.error("m=findByAddress stage=error");
          return new ObjectNotFoundException("Error: Address not found");
      });
    }

    public List<Address> findAllAddress(Long id) {
        logger.error("m=findAllAddress stage=init");
        List<Address> personAddresses = personService.findByPerson(id).getAdresses();
        return personAddresses;
    }

    public Address fromDto(AddressPost addressPost) throws ObjectNotFoundException {
        var person = personService.findByPerson(addressPost.getPersonId());
        var city = cityService.findCityByID(addressPost.getCity());
        validatePriority(addressPost);
        findPriorityAddress(person,addressPost);
        return new Address(null, city, person, addressPost.getStreet(), addressPost.getZipcode(), addressPost.getNumber(), addressPost.getPriorityAddress());
    }

    private void validatePriority(AddressPost addressPost) throws AddressException {
        logger.info("SERVICE :: Validating address priority");
        if(isValidPriority(addressPost)){
            logger.error("m=validatePriority stage=error");
            throw new AddressException("Error: use Y for true and N for false.");
        }
    }

    private boolean isValidPriority(AddressPost addressPost) {
        return addressPost.getPriorityAddress() != 'Y' && addressPost.getPriorityAddress() != 'N';
    }

    private void findPriorityAddress(Person person1, AddressPost addressPost){
        logger.info("SERVICE :: Sheaching address priority");
        for(Address x : person1.getAdresses()){
            if(x.getPriorityAddress()==null){
                x.setPriorityAddress(addressPost.getPriorityAddress());
            }
            if(addressPost.getPriorityAddress() == 'Y' && x.getPriorityAddress()== 'Y') {
                throw new AddressException("Error: Address is priority");
            }
            return;
        }
    }
}
