package com.telegram.bot.command.impl;

import com.telegram.bot.command.Command;
import com.telegram.bot.messagesender.MessageSender;
import com.telegram.bot.model.User;
import com.telegram.bot.repository.UserRepository;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class AlarmNotificationCommand implements Command {

    private static final List<String> WEST_UKRAINE_REGIONS = List.of("Волинська", "Рівненська", "Вінницька",
            "Івано-Франківська", "Хмельницька", "Тернопільська",
            "Закарпатська", "Чернівецька", "Житомирська", "Львівська");

    private UserRepository userRepository;
    private MessageSender messageSender;

    public AlarmNotificationCommand(MessageSender messageSender, UserRepository userRepository) {
        this.messageSender = messageSender;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Update update) {
        Message channelPost = update.getChannelPost();
        String messageText = channelPost.getText();

        boolean alarmStatus = isAlarmInWestRegion(messageText);

        if (alarmStatus) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(messageText);

            notifyAllUsers(sendMessage);
        }
    }

    private void notifyAllUsers(SendMessage sendMessage) {
        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            sendMessage.setChatId(user.getChatId());
            for (int i = 0; i < user.getAmountOfMessages(); i++) {
                messageSender.sendMessage(sendMessage);
            }
        }
    }

    private boolean isAlarmInWestRegion(String messageText) {
        boolean alarmStatus;
        for (String region : WEST_UKRAINE_REGIONS) {
            alarmStatus = messageText.contains(region);
            if (alarmStatus) {
                return true;
            }
        }
        return false;
    }
}
