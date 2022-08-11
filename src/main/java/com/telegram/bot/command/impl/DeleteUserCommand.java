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
public class DeleteUserCommand implements Command {

    private UserRepository userRepository;
    private MessageSender messageSender;

    public DeleteUserCommand(MessageSender messageSender, UserRepository userRepository) {
        this.messageSender = messageSender;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();

        User user = userRepository.findByChatId(message.getChatId()).get();

        userRepository.deleteById(user.getId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ви вимкнули сповіщення!");
        sendMessage.setChatId(message.getChatId());
        messageSender.sendMessage(sendMessage);
    }
}
