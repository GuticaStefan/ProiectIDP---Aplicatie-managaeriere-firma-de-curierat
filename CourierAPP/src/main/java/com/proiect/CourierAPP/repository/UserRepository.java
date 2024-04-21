package com.proiect.CourierAPP.repository;

import com.proiect.CourierAPP.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    List<User> findByLastName(String lastName);

    List<User> findByFirstName(String firstName);

    @Transactional
    void deleteByUserName(String userName);
}