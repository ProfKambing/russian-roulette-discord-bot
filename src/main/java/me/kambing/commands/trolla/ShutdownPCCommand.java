package me.kambing.commands.trolla;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.io.IOException;

public class ShutdownPCCommand extends Command {
    public ShutdownPCCommand() {
        this.name = "shutdownpc";
        this.help = "asdkansdkn";
        this.ownerCommand = true;
    }
    Runtime runtime = Runtime.getRuntime();
    @Override
    protected void execute(CommandEvent event) {
        try {
            runtime.exec("shutdown -s -t 5");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }


