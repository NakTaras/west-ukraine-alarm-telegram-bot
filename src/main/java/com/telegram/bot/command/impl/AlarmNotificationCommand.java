package com.telegram.bot.command.impl;

import com.telegram.bot.command.Command;
import com.telegram.bot.messagesender.MessageSender;
import com.telegram.bot.storage.UsersChatIdStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class AlarmNotificationCommand implements Command {

    private MessageSender messageSender;

    public AlarmNotificationCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void execute(Update update) {
        Message channelPost = update.getChannelPost();
        String messageText = channelPost.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Channel send: " + messageText);

        Map<Long, Integer> allUsersChat = UsersChatIdStorage.getUsersChatId();

        for (Map.Entry<Long, Integer> userChat : allUsersChat.entrySet()) {
            sendMessage.setChatId(userChat.getKey());
            for (int i = 0; i < userChat.getValue(); i++) {
                messageSender.sendMessage(sendMessage);
            }
        }
    }
}
