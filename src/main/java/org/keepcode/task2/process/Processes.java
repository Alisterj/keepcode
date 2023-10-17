package org.keepcode.task2.process;


import org.keepcode.task2.process.admin.AdminController;
import org.keepcode.task2.process.admin.DblIncomeUssdMessage;
import org.keepcode.task2.process.admin.EnumGoip;
import org.keepcode.task2.process.command.Command;
import org.keepcode.task2.process.command.CommandType;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Processes {
    private final String ipAddress = "example.com";
    private final int udpPort = 80;
    private List<Command> commands = new ArrayList<>(Arrays.asList(
            new Command(CommandType.REBOOT_CHANNEL, 4, 1),
            new Command(CommandType.REBOOT_CHANNEL, 4, 2),
            new Command(CommandType.SEND_MESSAGE, 1, 0),
            new Command(CommandType.SEND_MESSAGE, 6, 1)
    ));
    private Command currentCommand = new Command(CommandType.STOP_TASK, 6, 2);

    private CustomLogger logger = CustomLogger.getInstance();

    public String getIpAddress() {
        return ipAddress;
    }

    public int getUdpPort() {
        return udpPort;
    }

    private void deleteCommand(CommandType commandType) {
        commands.removeIf(command -> command.getCommandType() == commandType);
    }
    private List<Command> getAllCommands() {
        return commands;
    }

    private String getGoipModel() {
        return "MODEL_A";
    }

    private void sendCommandToContext(ChannelHandlerContext channelHandlerContext, InetSocketAddress inetSocketAddress, String commandText) {

    }

    private void sendKeepAliveOkAndFlush(ChannelHandlerContext channelHandlerContext) {

    }

    public void process(ChannelHandlerContext channelHandlerContext) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(getIpAddress(), getUdpPort());

        for (Command command : getAllCommands()) {
            if (command.getCommandType() == CommandType.REBOOT_CHANNEL) {
                if (!command.isAttemptsNumberExhausted()) {
                    if (command.isTimeToSend()) {
                        sendCommandToContext(channelHandlerContext, inetSocketAddress, command.getCommandText());

                        try {
                            AdminController.getInstance()
                                .processUssdMessage(new DblIncomeUssdMessage(
                                    inetSocketAddress,
                                    EnumGoip.getByModel(getGoipModel()),
                                    command.getCommandText()
                                ), false);
                        } catch (Exception ignored) {
                        }

                        command.setSendDate(new Date());

                        logger.write("send message : {}", command.getCommandText());

                        command.incSendCounter();
                    }
                } else {
                    deleteCommand(command.getCommandType());
                }
            } else {
                if (!currentCommand.isAttemptsNumberExhausted()) {
                    sendCommandToContext(channelHandlerContext, inetSocketAddress, command.getCommandText());

                    try {
                        AdminController.getInstance()
                            .processUssdMessage(new DblIncomeUssdMessage(
                                inetSocketAddress,
                                EnumGoip.getByModel(getGoipModel()),
                                command.getCommandText()
                            ), false);
                    } catch (Exception ignored) {
                    }

                    logger.write("send message : {}", command.getCommandText());

                    command.setSendDate(new Date());
                    command.incSendCounter();
                } else {
                    CommandType typeToRemove = command.getCommandType();
                    deleteCommand(command.getCommandType());
                }
            }
        }
        sendKeepAliveOkAndFlush(channelHandlerContext);
    }
}
