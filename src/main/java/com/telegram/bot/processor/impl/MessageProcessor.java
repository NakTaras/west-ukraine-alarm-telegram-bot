package com.telegram.bot.processor.impl;

import com.telegram.bot.messagesender.MessageSender;
import com.telegram.bot.processor.Processor;
import com.telegram.bot.storage.UsersChatIdStorage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageProcessor implements Processor {

    private MessageSender messageSender;

    public MessageProcessor(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void process(Update update) {
        Message message = update.getMessage();

        if (message.hasText()) {
            String messageText = message.getText();
            Long currentChatID = message.getChatId();

            if (messageText.equals("start")) {
                // TODO: 08.08.2022 add logic for input amount of message that bot should send
                UsersChatIdStorage.addUserChatId(message.getChatId());
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Your ID was added");
                sendMessage.setChatId(currentChatID);
                messageSender.sendMessage(sendMessage);
            } else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText(messageText + " is a wrong command");
                sendMessage.setChatId(currentChatID);
                messageSender.sendMessage(sendMessage);
            }
        }
    }
}
