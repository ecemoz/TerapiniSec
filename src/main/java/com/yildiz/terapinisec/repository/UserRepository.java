package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUserName(String username);


    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);


    User findByEmail(String email);


    User findByPhoneNumber(String phoneNumber);


    List<User> findByUserRole(UserRole role);


    List<User> findByLastLoginDateTimeBefore(LocalDateTime dateTime);


    List<User> findByLastLoginDateTimeAfter(LocalDateTime dateTime);


    List<User> findByIsPremiumTrue();


    List<User> findByIsPremiumFalse();


    List<User> findBySpecializationsContaining(Specialization specialization);


    List<User> findByYearsOfExperienceGreaterThan(Integer years);


    Optional<User> findByUserNameOrEmail(String userName, String email);

    boolean existsByIdAndIsPremiumTrue(Long id);
}
