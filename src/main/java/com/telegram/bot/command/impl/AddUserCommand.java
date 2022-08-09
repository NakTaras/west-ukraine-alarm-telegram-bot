package com.telegram.bot.command.impl;

import com.telegram.bot.command.Command;
import com.telegram.bot.messagesender.MessageSender;
import com.telegram.bot.storage.UsersChatIdStorage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AddUserCommand implements Command {

    private MessageSender messageSender;

    public AddUserCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        UsersChatIdStorage.addUser(message.getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ви ввімкнули сповіщення! За замовчуванням вам приходитиме одне повідомлення.");
        sendMessage.setChatId(message.getChatId());
        messageSender.sendMessage(sendMessage);
    }
}
