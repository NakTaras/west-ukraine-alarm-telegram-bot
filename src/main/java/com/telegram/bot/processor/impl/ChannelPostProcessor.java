package com.telegram.bot.processor.impl;

import com.telegram.bot.command.Command;
import com.telegram.bot.command.CommandContainer;
import com.telegram.bot.messagesender.MessageSender;
import com.telegram.bot.processor.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ChannelPostProcessor implements Processor {

    @Value("${telegram.channel.id}")
    private Long channelId;

    @Autowired
    private CommandContainer commandContainer;

    @Override
    public void process(Update update) {
        Message channelPost = update.getChannelPost();
        if (channelPost.getChatId().equals(channelId)) {
            Command command = commandContainer.getCommand("");
            command.execute(update);
        }
    }
}
