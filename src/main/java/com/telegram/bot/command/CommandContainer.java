package com.telegram.bot.command;

import com.telegram.bot.command.impl.*;
import com.telegram.bot.messagesender.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandContainer {

    // TODO: 09.08.2022 try to change impl with static

    private Map<String, Command> commands;

    @Autowired
    private MessageSender messageSender;

    public Command getCommand(String commandName) {
        if (null == commands){
            commands = new HashMap<>();

            commands.put("/start", new StartCommand(messageSender));
            commands.put("/turnon", new AddUserCommand(messageSender));
            commands.put("/turnoff", new DeleteUserCommand(messageSender));
            commands.put("/amount", new AskToSetAmountCommand(messageSender));
            commands.put("", new SetAmountCommand(messageSender));
        }
        return commands.get(commandName);
    }
}
