package com.telegram.bot.storage;

import java.util.HashMap;
import java.util.Map;

public class UsersChatIdStorage {

    private static final Map<Long, Integer> usersChatId = new HashMap<>();

    private UsersChatIdStorage() {
    }

    public static Map<Long, Integer> getUsersChatId() {
        return usersChatId;
    }

    public static void addUser(Long chatId) {
        usersChatId.put(chatId, 1);
    }

    public static void addUserWithAmountOfMessages(Long chatId, Integer amountOfMessages) {
        usersChatId.put(chatId, amountOfMessages);
    }

    public static void removeUser(Long chatId) {
        usersChatId.remove(chatId);
    }
}
