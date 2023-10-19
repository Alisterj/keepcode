package org.keepcode.task2.process;


import org.keepcode.task2.process.admin.AdminController;
import org.keepcode.task2.process.admin.DblIncomeUssdMessage;
import org.keepcode.task2.process.admin.EnumGoip;
import org.keepcode.task2.process.command.Command;
import org.keepcode.task2.process.command.CommandType;
import org.keepcode.task2.process.logger.CustomLogger;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Processes {
    private final String ipAddress = "example.com";
    private final int udpPort = 80;
    private List<Command> commands = new ArrayList<>(Arrays.asList(
        new Command(CommandType.REBOOT_CHANNEL, 4, 1, new Date(), "First element of the array"),
        new Command(CommandType.SEND_MESSAGE, 1, 1, "Third element of the array"),
        new Command(CommandType.SEND_MESSAGE, 6, 1, "Fourth element of the array"),
        new Command(CommandType.REBOOT_CHANNEL, 2, 3, "Fourth element of the array")
    ));
    private Command currentCommand = new Command(CommandType.REBOOT_CHANNEL, 6, 6, "It's currentCommand");

    private CustomLogger logger = CustomLogger.getInstance();

    public String getIpAddress() {
        return ipAddress;
    }

    public int getUdpPort() {
        return udpPort;
    }

    private void deleteCommand(CommandType commandType) {
        logger.write("Was delete");
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
            if (command.getCommandType() == CommandType.REBOOT_CHANNEL && command.isAttemptsNumberExhausted() ||
                command.getCommandType() != CommandType.REBOOT_CHANNEL && currentCommand.isAttemptsNumberExhausted()) {
                deleteCommand(command.getCommandType());
            } else if (command.getCommandType() != CommandType.REBOOT_CHANNEL ||
                command.getCommandType() == CommandType.REBOOT_CHANNEL && command.isTimeToSend()) {
                sendAndProcessCommand(channelHandlerContext, inetSocketAddress, command);
            }
        }

        sendKeepAliveOkAndFlush(channelHandlerContext);
    }

    public void sendAndProcessCommand(ChannelHandlerContext channelHandlerContext, InetSocketAddress inetSocketAddress, Command command) {
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
    }
}
