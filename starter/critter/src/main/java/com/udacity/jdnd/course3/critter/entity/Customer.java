package com.udacity.jdnd.course3.critter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
  @Id
  @SequenceGenerator(
      name = "customer_seq",
      sequenceName = "customer_seq",
      allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
                  generator = "customer_seq")

  private Long id;

  @Column(unique = true)
  private String name;
  private String phoneNumber;
  private String notes;

  @OneToMany( mappedBy = "customer",
      cascade = CascadeType.ALL,
  fetch = FetchType.LAZY)
  @JsonBackReference
   private List<Pet> pets;




}
