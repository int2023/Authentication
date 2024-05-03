package com.example.demo.repository;
import com.example.demo.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser,Long> {

    MyUser findByLogin(String login);

}
