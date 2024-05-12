package com.proiect.CourierAPPAuth.repository;

import com.proiect.CourierAPPAuth.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUserName(String userName);

    List<User> findByLastName(String lastName);

    List<User> findByFirstName(String firstName);

    @Transactional
    void deleteByUserName(String userName);
}