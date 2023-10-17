package org.keepcode.task2.process.admin;

public class AdminController {
    private static final AdminController instance = new AdminController();

    private AdminController() {
        // Приватный конструктор для синглтона
    }

    public static AdminController getInstance() {
        return instance;
    }

    public void processUssdMessage(DblIncomeUssdMessage message, boolean someFlag) {
        // Логика обработки сообщения
        System.out.println("Processing USSD message: " + message);
        System.out.println("Flag: " + someFlag);
    }
}
