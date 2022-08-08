package com.telegram.bot.command.impl;

import com.telegram.bot.command.Command;
import com.telegram.bot.messagesender.MessageSender;
import com.telegram.bot.storage.UsersChatIdStorage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DeleteUserCommand implements Command {

    private MessageSender messageSender;

    public DeleteUserCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        UsersChatIdStorage.addUserChatId(message.getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("DeleteUserCommand should be implemented");
        sendMessage.setChatId(message.getChatId());
        messageSender.sendMessage(sendMessage);
    }
}
