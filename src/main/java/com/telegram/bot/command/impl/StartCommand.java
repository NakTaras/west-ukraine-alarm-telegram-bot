package com.telegram.bot.command.impl;

import com.telegram.bot.command.Command;
import com.telegram.bot.message.MessagesText;
import com.telegram.bot.messagesender.MessageSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommand implements Command {

    private MessageSender messageSender;

    public StartCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(MessagesText.START_MESSAGE_TEXT);
        sendMessage.setChatId(message.getChatId());
        messageSender.sendMessage(sendMessage);
    }
}
