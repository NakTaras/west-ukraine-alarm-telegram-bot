package com.telegram.bot.command.impl;

import com.telegram.bot.command.Command;
import com.telegram.bot.messagesender.MessageSender;
import com.telegram.bot.model.User;
import com.telegram.bot.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class SetAmountCommand implements Command {

    private UserRepository userRepository;
    private MessageSender messageSender;

    public SetAmountCommand(MessageSender messageSender, UserRepository userRepository) {
        this.messageSender = messageSender;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Integer amountOfMessages = Integer.parseInt(callbackQuery.getData().replaceAll("[\\D]", ""));
        Long currentId = callbackQuery.getFrom().getId();

        Optional<User> userOpt = userRepository.findByChatId(currentId);

        User user;

        if (userOpt.isPresent()){
            user = userOpt.get();
        } else {
            user = new User();
            user.setChatId(currentId);
        }

        user.setAmountOfMessages(amountOfMessages);
        userRepository.save(user);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ви змінили кількість повідомлень при сповіщенні тривоги та ввімкнули сповіщення! Тепер кількість повідомлень: " + amountOfMessages);
        sendMessage.setChatId(currentId);
        messageSender.sendMessage(sendMessage);
    }
}
