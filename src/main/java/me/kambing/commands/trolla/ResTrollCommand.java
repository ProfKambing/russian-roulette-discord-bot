package me.kambing.commands.trolla;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ResTrollCommand extends Command {
    public ResTrollCommand() {
        this.name = "restroll";
        this.help = ";restroll 1680,1050";
        this.ownerCommand = true;
        this.guildOnly = false;
    }


    @Override
    protected void execute(CommandEvent event) {
        String splitSpace = event.getMessage().getContentRaw().split(" ")[1];
        String[] splitComma = splitSpace.split(",");
        String x = splitComma[0];
        String y = splitComma[1];
        for (int i = 0; i < 50; i++) {
            try {
                changeRes(x,y);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                changeRes("1920", "1080");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    void changeRes(String x, String y) throws IOException {
        executeCommand("C:/Users/USER/Downloads/QRes.exe /x:" + x + " /y:" + y);
    }
    private void executeCommand(String command) {
        try {
            log(command);
            Process process = Runtime.getRuntime().exec(command);
            logOutput(process.getInputStream(), "");
            logOutput(process.getErrorStream(), "Error: ");
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void logOutput(InputStream inputStream, String prefix) {
        new Thread(() -> {
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            while (scanner.hasNextLine()) {
                synchronized (this) {
                    log(prefix + scanner.nextLine());
                }
            }
            scanner.close();
        }).start();
    }

    private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss:SSS");

    private synchronized void log(String message) {
        System.out.println(format.format(new Date()) + ": " + message);
    }

}


