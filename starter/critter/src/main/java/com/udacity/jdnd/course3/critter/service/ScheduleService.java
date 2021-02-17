package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ScheduleService {

  @Autowired
  ScheduleRepository scheduleRepository;
  @Autowired
  EmployeeRepository employeeRepository;
  @Autowired
  CustomerRepository customerRepository;
  @Autowired
  PetRepository petRepository;

  public Schedule saveSchedule(Schedule schedule){
    return scheduleRepository.save(schedule);
  }

  public List<Schedule> getAllSchedule(){
    return scheduleRepository.findAll();
  }

  public List<Schedule> getScheduleForPet(Long petId){
    return scheduleRepository.getDetailsByPet(petRepository.getOne(petId));
  }

  public List<Schedule> getScheduleForEmployee(Long employeeId){
    return scheduleRepository.getDetailsByEmployee(employeeRepository.getOne(employeeId));
  }

  public List<Schedule> getScheduleForCustomer(Long petId){
    return scheduleRepository.findByPet_Id(petId);
  }

}
