package me.kambing.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.kambing.Main;
import me.kambing.listeners.RouletteListener;
import me.kambing.utils.UnExceedAbleInteger;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.Duration;
import java.util.ArrayList;

public class RussianRoulette extends Command {
    public static ArrayList<User> players = new ArrayList<>();
    public static UnExceedAbleInteger turn;
    public static boolean running = false;
    public RussianRoulette() {
        this.name = "roulette";
        this.help = "a classic russian roulette";
    }
    @Override
    protected void execute(CommandEvent event) {
        boolean run;
        try {
            run = event.getMessage().getContentRaw().split(" ")[1].equals("start");
        } catch (ArrayIndexOutOfBoundsException exception) {
            run = false;
        }
        if (running) {
            event.reply("A game is running, please wait.");
            return;
        }
        if (players.size() == 0) {
            players.add(event.getAuthor());
            event.reply("Created game. Type ;roulette to join (other players)");
        } else {
            if (!event.getAuthor().equals(players.get(0)) && !players.contains(event.getAuthor()) && !run) {
                players.add(event.getAuthor());
                event.reactSuccess();
            } else event.reactError();
        }
        if (event.getAuthor().equals(players.get(0)) && run) {
            event.getMessage().reply("Russian roulette starting.").delay(Duration.ofSeconds(1)).queue(message -> message.delete().delay(Duration.ofSeconds(3)).queue());
            turn = new UnExceedAbleInteger(1, players.size());
            running = true;
            Main.rouletteListener.onGuildMessageReceived(new GuildMessageReceivedEvent(event.getJDA(), event.getResponseNumber(), event.getMessage()));
            return;
        }
        if (players.size() == 2) {
            event.getMessage().getChannel().sendMessage("Game is ready to start, type ;roulette start").queue();
        }

    }
}


