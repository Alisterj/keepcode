package org.keepcode.task2.process.admin;

import java.net.InetSocketAddress;

public class DblIncomeUssdMessage {
    private final InetSocketAddress inetSocketAddress;
    private final EnumGoip enumGoip;
    private final String textCommand;
    public DblIncomeUssdMessage(InetSocketAddress inetSocketAddress, EnumGoip enumGoip, String textCommand){
        this.inetSocketAddress = inetSocketAddress;
        this.enumGoip = enumGoip;
        this.textCommand = textCommand;

    }
}
