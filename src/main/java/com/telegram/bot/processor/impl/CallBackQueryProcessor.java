package com.telegram.bot.processor.impl;

import com.telegram.bot.processor.Processor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallBackQueryProcessor implements Processor {
    @Override
    public void process(Update update) {

    }
}
