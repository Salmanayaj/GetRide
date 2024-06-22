package com.example.GetRide.Repository;

import com.example.GetRide.Enum.Gender;
import com.example.GetRide.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findByEmailId(String email);

    List<Customer> findByName(String name);

//    @Query(value = "select * from customer where gender_value = :gender and age >= :age",nativeQuery = true)
//    List<Customer> getAllByGenderAndAgeGreaterThan(String gender, int age);


    @Query(value = "select x from Customer x where x.gender = :gender and x.age >= :age")
    List<Customer> getAllByGenderAndAgeGreaterThan(Gender gender, int age);
}
