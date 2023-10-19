package org.keepcode.task2.process.command;

import java.time.LocalTime;
import java.util.Date;

public class Command {
    private final CommandType commandType;
    private final String commandText;
    private final int maxAttempts;
    private int sendCounter;
    private Date sendDate;

    public Command(CommandType commandType, int maxAttempts) {
        this.commandType = commandType;
        this.maxAttempts = maxAttempts;
        this.sendDate = new Date();

        this.commandText = "commandText";
        this.sendCounter = 0;
    }

    public Command(CommandType commandType, int maxAttempts, int sendCounter, String commandText) {
        this.commandType = commandType;
        this.maxAttempts = maxAttempts;
        this.sendCounter = sendCounter;
        this.commandText = commandText;
        this.sendDate = null;
    }

    public Command(CommandType commandType, int maxAttempts, int sendCounter, Date date, String commandText) {
        this.commandType = commandType;
        this.maxAttempts = maxAttempts;
        this.sendCounter = sendCounter;
        this.sendDate = date;
        this.commandText = commandText;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String getCommandText() {
        return commandText;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public boolean isAttemptsNumberExhausted() {
        return !(maxAttempts > sendCounter);
    }

    public boolean isTimeToSend() {
        if (sendDate == null) {
            return false;
        }
        return true;
    }

    public void incSendCounter() {
        sendCounter += 1;
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandType=" + commandType +
                ", maxAttempts=" + maxAttempts +
                ", sendCounter=" + sendCounter +
                '}';
    }
}
