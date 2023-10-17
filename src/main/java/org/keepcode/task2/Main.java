package org.keepcode.task2;

import org.keepcode.task2.process.ChannelHandlerContext;
import org.keepcode.task2.process.Processes;

public class Main {

    public static void main(String[] args) {
        Processes processes = new Processes();
        ChannelHandlerContext channelHandlerContext = new ChannelHandlerContext();
        processes.process(channelHandlerContext);
    }
}
