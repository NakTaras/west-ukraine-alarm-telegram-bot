package com.telegram.bot.command;

import com.telegram.bot.command.impl.*;
import com.telegram.bot.messagesender.MessageSender;
import com.telegram.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandContainer {

    private Map<String, Command> commands;

    @Autowired
    private MessageSender messageSender;
    @Autowired
    private UserRepository userRepository;

    public Command getCommand(String commandName) {
        if (null == commands){
            commands = new HashMap<>();

            commands.put("/start", new StartCommand(messageSender));
            commands.put("/turnon", new AddUserCommand(messageSender, userRepository));
            commands.put("/turnoff", new DeleteUserCommand(messageSender, userRepository));
            commands.put("/amount", new AskToSetAmountCommand(messageSender));
            commands.put("setamount", new SetAmountCommand(messageSender, userRepository));
            commands.put("", new AlarmNotificationCommand(messageSender, userRepository));
        }
        return commands.get(commandName);
    }
}
