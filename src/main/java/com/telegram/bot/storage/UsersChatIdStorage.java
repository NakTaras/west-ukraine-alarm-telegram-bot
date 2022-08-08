package com.telegram.bot.storage;

import java.util.HashSet;
import java.util.Set;

public class UsersChatIdStorage {

    private static final Set<Long> usersChatId = new HashSet<>();

    private UsersChatIdStorage() {
    }

    public static Set<Long> getUsersChatId() {
        return usersChatId;
    }

    public static void addUserChatId(Long chatId) {
        // TODO: 08.08.2022 check how to implement this method
        usersChatId.add(chatId);
    }
}
