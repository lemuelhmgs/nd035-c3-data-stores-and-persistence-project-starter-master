package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;


    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
      Customer customer = new Customer();
       customer.setId(petDTO.getOwnerId());
       petDTO.setCustomer(customer);

        Pet savedPet = petService.savePet(convertPetDTOToPetEntity(petDTO));
        return convertPetToPetDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        PetDTO petDTO = convertPetToPetDTO(pet);
        Customer customer = pet.getCustomer();
        petDTO.setOwnerId(customer.getId());
        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO>  petDTOS = new ArrayList<>();

        List<Pet> pets= petService.getAllPets();

        for(Pet pet: pets){
            petDTOS.add(convertPetToPetDTO(pet));


        }
        return petDTOS;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        List<PetDTO> list = new ArrayList<>();
        for (Pet pet : petService.getPetsByOwner(ownerId)) {
            PetDTO petDTO = convertPetToPetDTO(pet);
            list.add(petDTO);
        }
        return list;
    }

    private static PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);

        Customer customer = pet.getCustomer();
        try {
            customer.getPets().forEach(petDto -> {
                petDTO.setOwnerId(customer.getId());
            });

        }catch (Exception e){
            System.out.println("customer "+customer.getId() + " doesn't exist");
        }
        return petDTO;
    }

    private static Pet convertPetDTOToPetEntity(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO,pet);
        return pet;
    }
}
