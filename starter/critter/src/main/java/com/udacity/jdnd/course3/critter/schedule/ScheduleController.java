package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    CustomerService customerService;
    @Autowired
    PetService petService;


    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

     List<Employee> employee = customerService.findAllById(scheduleDTO.getEmployeeIds());
     List<Pet> pet = petService.findAllById(scheduleDTO.getPetIds());

     Schedule schedule = new Schedule();
     BeanUtils.copyProperties(scheduleDTO,schedule);
     schedule.setEmployee(employee);
     schedule.setPet(pet);

     scheduleService.saveSchedule(schedule);

     employee.forEach(emp ->{
       if(emp.getSchedules() == null){
         emp.setSchedules(new ArrayList<>());
       } else{
         emp.getSchedules().add(schedule);
       }
     });

     pet.forEach(newPet ->{
       if(newPet.getSchedules() == null){
         newPet.setSchedules(new ArrayList<>());
       } else{
         newPet.getSchedules().add(schedule);
       }
     });

     return scheduleDTO;

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {


        List<Schedule> schedules = scheduleService.getAllSchedule();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        ScheduleDTO newSchedule;
        if(schedules != null){
          for(Schedule schedule: schedules){
            newSchedule = new ScheduleDTO();
            BeanUtils.copyProperties(schedule,newSchedule);
            newSchedule.setEmployeeIds(schedule.getEmployee().stream().map(Employee::getId).collect(
                Collectors.toList()));
            List<Long> pets = new ArrayList<>();
            for(Pet p: schedule.getPet()){
              pets.add(p.getId());
            }
            newSchedule.setPetIds(pets);
            scheduleDTOS.add(newSchedule);
          }
        }

        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
      List<Schedule> schedules = scheduleService.getScheduleForPet(petId);

      List<ScheduleDTO> scheduleDTOS= new ArrayList<>();
      ScheduleDTO newScheduleDTO;

      if(schedules != null){
        for(Schedule schedule : schedules){
          newScheduleDTO = new ScheduleDTO();
          newScheduleDTO.setId(schedule.getId());
         newScheduleDTO.setActivities(schedule.getActivities());
         newScheduleDTO.setDate(schedule.getDate());
         newScheduleDTO.setEmployeeIds(schedule.getEmployee().stream().map(Employee::getId).collect(
             Collectors.toList()));

         List<Long> pets = new ArrayList<>();
         for(Pet pet : schedule.getPet()){
           pets.add(pet.getId());
         }
         newScheduleDTO.setPetIds(pets);
         scheduleDTOS.add(newScheduleDTO);
        }
      }

      return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> employees = scheduleService.getScheduleForEmployee(employeeId);

      List<ScheduleDTO> scheduleDTOS= new ArrayList<>();
      ScheduleDTO newScheduleDTO;

      if(employees != null){
        for(Schedule schedule : employees){
          newScheduleDTO = new ScheduleDTO();
          newScheduleDTO.setId(schedule.getId());
          newScheduleDTO.setActivities(schedule.getActivities());
          newScheduleDTO.setDate(schedule.getDate());
          newScheduleDTO.setEmployeeIds(schedule.getEmployee().stream().map(Employee::getId).collect(
              Collectors.toList()));

          List<Long> pets = new ArrayList<>();
          for(Pet pet : schedule.getPet()){
            pets.add(pet.getId());
          }
          newScheduleDTO.setPetIds(pets);
          scheduleDTOS.add(newScheduleDTO);
        }
      }

      return scheduleDTOS;


    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

      List<ScheduleDTO> scheduleDTOS= new ArrayList<>();
      Customer customer = customerService.getCustomerById(customerId);
      List<Pet> pets = customer.getPets();

      ArrayList<Schedule> schedules = new ArrayList<>();

      for(Pet pet: pets){
        schedules.addAll(scheduleService.getScheduleForCustomer(pet.getId()));
      }

      ScheduleDTO newScheduleDTO;

      if(schedules != null){
        for(Schedule schedule : schedules){
          newScheduleDTO = new ScheduleDTO();
          newScheduleDTO.setId(schedule.getId());
          newScheduleDTO.setActivities(schedule.getActivities());
          newScheduleDTO.setDate(schedule.getDate());
          newScheduleDTO.setEmployeeIds(schedule.getEmployee().stream().map(Employee::getId).collect(
              Collectors.toList()));

          List<Long> petids = new ArrayList<>();
          for(Pet pet : schedule.getPet()){
            petids.add(pet.getId());
          }
          newScheduleDTO.setPetIds(petids);
          scheduleDTOS.add(newScheduleDTO);
        }
      }

      return scheduleDTOS;



    }




}
