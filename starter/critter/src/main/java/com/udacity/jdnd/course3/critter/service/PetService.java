package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PetService {

  @Autowired
  PetRepository petRepository;

  @Autowired
  CustomerRepository customerRepository;

  public Pet savePet(Pet pet){

    Pet savedPet = petRepository.save(pet);

    Customer customer = savedPet.getCustomer();
    List<Pet> customerPets = customer.getPets();

    if(customerPets == null){
      customerPets = new ArrayList<>();
    }

    customerPets.add(savedPet);
    customer.setPets(customerPets);
    customerRepository.save(customer);

    return savedPet;
  }

  public Pet getPetById(Long id){
    Optional<Pet> pet = petRepository.findById(id);
    if(pet.isPresent()){
      return pet.get();
    }else{
      throw new ObjectNotFoundException("pet not found");
    }
  }

  public List<Pet> getAllPets(){
    List<Pet> petList =  petRepository.findAll();
    if(petList.size()== 0){
      throw new ObjectNotFoundException("Pet List is zero");
    }
    return petList;
  }

  public List<Pet> getPetsByOwner(Long ownerId){
    return petRepository.getPetsByCustomer_Id(ownerId);
  }



  private boolean isPetInDB(Pet pet, Customer customer) {
    List<Pet> pets = customer.getPets();
    if(pets.size() == 0)
      return false;
    for(Pet myPet: pets){
      if(myPet.getName().equalsIgnoreCase(pet.getName()) &&
         myPet.getType().equals(pet.getType()) &&
          myPet.getBirthDate().equals(pet.getBirthDate())
        ){
        return true;
      }
    }
    return false;
  }

}
