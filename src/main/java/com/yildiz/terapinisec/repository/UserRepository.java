package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface  UserRepository  extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByFirstNameAndLastName(String firstName, String lastName);
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    User findByRole(UserRole role);
    User findByLastLoginDateTimeBefore(LocalDateTime dateTime);
    User findByLastLoginDateTimeAfter(LocalDateTime dateTime);
    User findByIsPremiumTrue();
    User findByIsPremiumFalse();
    User findBySpecializationContains(Specialization specialization);
    User findByYearsOfExperienceGreaterThan(Integer years);
}
