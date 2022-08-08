package com.telegram.bot;

import com.telegram.bot.processor.impl.ChannelPostProcessor;
import com.telegram.bot.processor.impl.MessageProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelloWorldBot extends TelegramLongPollingBot {

    private final MessageProcessor messageProcessor;
    private final ChannelPostProcessor channelPostProcessor;

    @Value("${telegram.bot.username}")
    private String userName;
    @Value("${telegram.bot.token}")
    private String token;

    public HelloWorldBot(MessageProcessor messageProcessor, ChannelPostProcessor channelPostProcessor) {
        this.messageProcessor = messageProcessor;
        this.channelPostProcessor = channelPostProcessor;
    }

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
            messageProcessor.process(update);
        }
        if (update.hasChannelPost()) {
            channelPostProcessor.process(update);
        }
    }
}
