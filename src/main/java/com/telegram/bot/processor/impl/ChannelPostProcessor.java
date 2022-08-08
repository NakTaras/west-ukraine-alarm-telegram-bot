package com.telegram.bot.processor.impl;

import com.telegram.bot.messagesender.MessageSender;
import com.telegram.bot.processor.Processor;
import com.telegram.bot.storage.UsersChatIdStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ChannelPostProcessor implements Processor {

    @Value("${telegram.channel.id}")
    private Long channelId;

    private MessageSender messageSender;

    public ChannelPostProcessor(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void process(Update update) {
        Message channelPost = update.getChannelPost();
        if (channelPost.getChatId().equals(channelId)) {
            String messageText = channelPost.getText();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Channel send: " + messageText);
            // TODO: 08.08.2022 foreach loop for sending messages to all users
            sendMessage.setChatId((Long) UsersChatIdStorage.getUsersChatId().toArray()[0]);
            messageSender.sendMessage(sendMessage);
        }
    }
}
