package com.telegram.bot.command.impl;

import com.telegram.bot.command.Command;
import com.telegram.bot.messagesender.MessageSender;
import com.telegram.bot.storage.UsersChatIdStorage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SetAmountCommand implements Command {

    private MessageSender messageSender;

    public SetAmountCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void execute(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Integer amountOfMessages = Integer.parseInt(callbackQuery.getData().replaceAll("[\\D]", ""));
        Long currentId = callbackQuery.getFrom().getId();

        UsersChatIdStorage.addUserWithAmountOfMessages(currentId, amountOfMessages);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ви змінили кількість повідомлень при сповіщенні тривоги та ввімкнули сповіщення! Тепер кількість повідомлень: " + amountOfMessages);
        sendMessage.setChatId(currentId);
        messageSender.sendMessage(sendMessage);
    }
}
