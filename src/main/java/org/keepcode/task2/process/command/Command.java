package org.keepcode.task2.process.command;

import java.time.LocalTime;
import java.util.Date;

public class Command {
    private final CommandType commandType;
    private String commandText = "TEXTCOMAND";
    private final int maxAttempts;
    private int sendCounter;
    private Date sendDate;
    private static final LocalTime SEND_TIME_IS_AFTER = LocalTime.of(9, 30);
    private static final LocalTime SEND_TIME_IS_BEFORE = LocalTime.of(22, 0);

    public Command(CommandType commandType, int maxAttempts) {
        this.commandType = commandType;
        this.maxAttempts = maxAttempts;
        this.sendDate = new Date();

        this.sendCounter = 0;
    }

    public Command(CommandType commandType, int maxAttempts, int sendCounter ) {
        this.commandType = commandType;
        this.maxAttempts = maxAttempts;
        this.sendCounter = sendCounter;
        this.sendDate = new Date();
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
        return maxAttempts <= sendCounter;
    }

    public boolean isTimeToSend() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.isAfter(SEND_TIME_IS_AFTER) && currentTime.isBefore(SEND_TIME_IS_BEFORE);
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
