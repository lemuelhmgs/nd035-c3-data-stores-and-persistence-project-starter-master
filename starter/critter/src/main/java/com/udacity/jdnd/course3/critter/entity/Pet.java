package com.udacity.jdnd.course3.critter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.udacity.jdnd.course3.critter.pet.PetType;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pet {
  @Id
  @SequenceGenerator(
      name = "pet_sequence",
      sequenceName = "pet_sequence",
      allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
                  generator = "pet_sequence")
  private long id;

  @Enumerated(EnumType.STRING)
  private PetType type;

  private String name;

  @ManyToOne
  @JoinColumn(name="customer_id")
  @JsonBackReference
  private Customer customer;

  @ManyToMany(mappedBy = "pet")
  private List<Schedule> schedules;

  private LocalDate birthDate;
  private String notes;

}
