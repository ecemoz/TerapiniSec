package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.User;
import com.yildiz.terapinisec.util.Specialization;
import com.yildiz.terapinisec.util.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Kullanıcı adından kullanıcıyı bul
    User findByUserName(String username);

    // Kullanıcı adı ve soyadına göre kullanıcıyı bul
    User findByFirstNameAndLastName(String firstName, String lastName);

    // E-posta adresine göre kullanıcıyı bul
    User findByEmail(String email);

    // Telefon numarasına göre kullanıcıyı bul
    User findByPhoneNumber(String phoneNumber);

    // Kullanıcı rolüne göre kullanıcıları bul
    List<User> findByUserRole(UserRole role);

    // Belirli bir tarihten önce giriş yapan kullanıcıları bul
    List<User> findByLastLoginDateTimeBefore(LocalDateTime dateTime);

    // Belirli bir tarihten sonra giriş yapan kullanıcıları bul
    List<User> findByLastLoginDateTimeAfter(LocalDateTime dateTime);

    // Premium olan kullanıcıları bul
    List<User> findByIsPremiumTrue();

    // Premium olmayan kullanıcıları bul
    List<User> findByIsPremiumFalse();

    // Belirli bir uzmanlık alanına sahip kullanıcıları bul
    List<User> findBySpecializationsContaining(Specialization specialization);

    // Belirli bir deneyim yılından daha fazla deneyime sahip kullanıcıları bul
    List<User> findByYearsOfExperienceGreaterThan(Integer years);

    // Kullanıcı adı veya e-posta adresine göre kullanıcıyı bul
    User findByUserNameOrEmail(String userName, String email);
}
