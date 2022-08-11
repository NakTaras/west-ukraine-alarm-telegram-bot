package com.telegram.bot.command.impl;

import com.telegram.bot.command.Command;
import com.telegram.bot.messagesender.MessageSender;
import com.telegram.bot.model.User;
import com.telegram.bot.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AddUserCommand implements Command {

    private UserRepository userRepository;
    private MessageSender messageSender;

    public AddUserCommand(MessageSender messageSender, UserRepository userRepository) {
        this.messageSender = messageSender;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();

        User user = new User();
        user.setChatId(message.getChatId());
        user.setAmountOfMessages(1);
        userRepository.save(user);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ви ввімкнули сповіщення! За замовчуванням вам приходитиме одне повідомлення.");
        sendMessage.setChatId(message.getChatId());
        messageSender.sendMessage(sendMessage);
    }
}
