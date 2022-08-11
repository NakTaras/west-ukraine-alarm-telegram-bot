package com.telegram.bot.repository;

import com.telegram.bot.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByChatId(Long chatId);

    void deleteByChatId(Long chatId);
}
