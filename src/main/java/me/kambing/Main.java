package me.kambing;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.command.PingCommand;
import com.jagrosh.jdautilities.examples.command.ShutdownCommand;
import me.kambing.commands.RussianRoulette;
import me.kambing.commands.trolla.FakeVirusCommand;
import me.kambing.commands.trolla.ResTrollCommand;
import me.kambing.commands.trolla.ShutdownPCCommand;
import me.kambing.listeners.RouletteListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Main {

    public static CommandClientBuilder client;
    public static JDA jda;
    public static RouletteListener rouletteListener;

    public static void main(String[] args) throws IOException, LoginException, IllegalArgumentException, RateLimitedException {
        String token = ""; //insert ur token

        String ownerId = "806897032337817610";

        EventWaiter waiter = new EventWaiter();
        client = new CommandClientBuilder();
        client.useDefaultGame();
        client.setOwnerId(ownerId);
        client.setStatus(OnlineStatus.ONLINE);
        client.setEmojis("\u2705", "\u26A0", "\uD83D\uDEAB");
        client.setPrefix(";");
        client.addCommands( //register all da commands
                new PingCommand(),
                new ShutdownCommand(),
                new FakeVirusCommand(),
                new ShutdownPCCommand(),
                new ResTrollCommand(),
                new RussianRoulette()

     );
        jda = JDABuilder.createDefault(token)
                .addEventListeners(rouletteListener = new RouletteListener(), waiter, client.build())
                .build();
        }
    }
