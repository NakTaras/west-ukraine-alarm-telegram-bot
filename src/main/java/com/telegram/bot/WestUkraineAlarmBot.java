package com.telegram.bot;

import com.telegram.bot.processor.impl.CallBackQueryProcessor;
import com.telegram.bot.processor.impl.ChannelPostProcessor;
import com.telegram.bot.processor.impl.MessageProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class WestUkraineAlarmBot extends TelegramLongPollingBot {

    private final MessageProcessor messageProcessor;
    private final ChannelPostProcessor channelPostProcessor;
    private final CallBackQueryProcessor callBackQueryProcessor;

    @Value("${telegram.bot.username}")
    private String userName;
    @Value("${telegram.bot.token}")
    private String token;

    public WestUkraineAlarmBot(MessageProcessor messageProcessor, ChannelPostProcessor channelPostProcessor, CallBackQueryProcessor callBackQueryProcessor) {
        this.messageProcessor = messageProcessor;
        this.channelPostProcessor = channelPostProcessor;
        this.callBackQueryProcessor = callBackQueryProcessor;
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            messageProcessor.process(update);
        }
        if (update.hasChannelPost()) {
            channelPostProcessor.process(update);
        }
        if (update.hasCallbackQuery()) {
            callBackQueryProcessor.process(update);
        }
    }
}
