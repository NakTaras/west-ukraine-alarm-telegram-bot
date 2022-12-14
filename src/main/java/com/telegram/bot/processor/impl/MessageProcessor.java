package com.telegram.bot.processor.impl;

import com.telegram.bot.command.Command;
import com.telegram.bot.command.CommandContainer;
import com.telegram.bot.message.MessagesText;
import com.telegram.bot.messagesender.MessageSender;
import com.telegram.bot.processor.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageProcessor implements Processor {

    @Autowired
    private MessageSender messageSender;
    @Autowired
    private CommandContainer commandContainer;

    @Override
    public void process(Update update) {
        Message message = update.getMessage();

        if (message.hasText()) {
            String messageText = message.getText();
            Long currentChatID = message.getChatId();

            if (messageText.equals("start")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Your ID was added");
                sendMessage.setChatId(currentChatID);
                messageSender.sendMessage(sendMessage);
            }

            Command command = commandContainer.getCommand(messageText);
            if (null != command && '/' == messageText.charAt(0)) {
                command.execute(update);
            } else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText(MessagesText.WRONG_COMMAND_MESSAGE_TEXT);
                sendMessage.setChatId(currentChatID);
                messageSender.sendMessage(sendMessage);
            }
        }
    }
}
