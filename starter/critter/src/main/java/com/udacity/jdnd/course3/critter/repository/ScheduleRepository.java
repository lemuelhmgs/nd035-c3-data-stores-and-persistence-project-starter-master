package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

  List<Schedule> getDetailsByPet(Pet pet);
  List<Schedule> getDetailsByEmployee(Employee employee);
  List<Schedule> findByPet_Id(Long petId);

}
