package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface  UserRepository  extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByFirstNameAndLastName(String firstName, String lastName);
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    List<User> findByRole(UserRole role);
    List<User> findByLastLoginDateTimeBefore(LocalDateTime dateTime);
    List <User> findByLastLoginDateTimeAfter(LocalDateTime dateTime);
    List<User> findByIsPremiumTrue();
    List<User> findByIsPremiumFalse();
    List<User>findBySpecializationContains(Specialization specialization);
    List<User> findByYearsOfExperienceGreaterThan(Integer years);
    User findByUsernameOrEmail(String usernameOrEmail);
}