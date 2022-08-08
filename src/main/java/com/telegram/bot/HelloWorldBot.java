package com.telegram.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class HelloWorldBot extends TelegramLongPollingBot {

    Set<Long> usersChatId = new HashSet<>();

    @Value("${telegram.bot.username}")
    private String userName;
    @Value("${telegram.bot.token}")
    private String token;
    @Value("${telegram.channel.id}")
    private Long channelId;

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    // TODO: 08.08.2022 implement logic to filter alarm for different regions 
    // TODO: 08.08.2022 try to replace implementation with command pattern 
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                String messageText = message.getText();
                Long currentChatID = message.getChatId();

                if (messageText.equals("start")) {
                    // TODO: 08.08.2022 add logic for input amount of message that bot should send 
                    addUserChatId(message.getChatId());
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Your ID was added");
                    sendMessage.setChatId(currentChatID);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText(messageText + " is a wrong command");
                    sendMessage.setChatId(currentChatID);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (update.hasChannelPost()) {
            Message channelPost = update.getChannelPost();
            if (channelPost.getChatId().equals(channelId)) {
                String messageText = channelPost.getText();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Channel send: " + messageText);
                // TODO: 08.08.2022 foreach loop for sending messages to all users 
                sendMessage.setChatId((Long) usersChatId.toArray()[0]);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addUserChatId(Long chatId) {
        // TODO: 08.08.2022 check how to implement this method 
        usersChatId.add(chatId);
    }
}
