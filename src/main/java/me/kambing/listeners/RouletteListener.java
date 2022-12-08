package me.kambing.listeners;

import me.kambing.commands.RussianRoulette;
import me.kambing.utils.Chamber;
import me.kambing.utils.UnExceedAbleInteger;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.ArrayList;

public class RouletteListener extends ListenerAdapter {

    ArrayList<Chamber> chambers = new ArrayList<>();
    UnExceedAbleInteger currentChamber = new UnExceedAbleInteger(1, 6);
    Message deletingMessage, deletingMessage1;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getAuthor().equals(event.getJDA().getSelfUser())) return;
        if (!RussianRoulette.running) return;
        if (event.getMessage().getContentRaw().equals(";roulette start")) {
            for (int i = 1; i <= 6; i++) {
                chambers.add(new Chamber(i));
            }
            int random = getRandomNumber(1, 7); // only outputs 1-6
            chambers.get(random - 1).hasBullet = true;
            event.getChannel().sendMessage(":gun: Placed bullet in the `" + random + getSuffix(random) + "` chamber. \n :arrows_counterclockwise: Spinning current chamber...").delay(Duration.ofSeconds(2)).queue(message ->
                    message.editMessage(getAsPing(RussianRoulette.players.get(RussianRoulette.turn.getInteger() - 1).getId()) +
                            " its your turn. `spin` :arrows_counterclockwise: or `shoot` :gun: .").delay(Duration.ofSeconds(1)).queue());
            currentChamber.setInteger(currentChamber.getRandom());
        }
        if (event.getMessage().getContentRaw().equals(";roulette start")) return;
        if (RussianRoulette.players.size() > 1) {
            if (deletingMessage != null) {
                deletingMessage.delete().queue();
            }
            if (deletingMessage1 != null) {
                deletingMessage1.delete().queue();
            }
            if (event.getAuthor().getName().equals(RussianRoulette.players.get(RussianRoulette.turn.getInteger() - 1).getName())) {
                //event.getMessage().reply("u alive").queue();
                if (!event.getMessage().getContentRaw().equals("shoot")) {
                    event.getMessage().reply(":arrows_counterclockwise: spinning chambers.").delay(Duration.ofSeconds(1)).queue(message -> message.editMessage(":exploding_head: Shooting!").delay(
                            Duration.ofSeconds(1)).queue(
                                    message1 -> message1.delete().delay(Duration.ofMillis(400)).queue()));
                    currentChamber.setInteger(currentChamber.getRandom());
                }
                System.out.println("Chamber, " + currentChamber.getInteger() + " , has bullet ," + chambers.get(currentChamber.getInteger() - 1).hasBullet);
                if (chambers.get(currentChamber.getInteger() - 1).hasBullet) {
                    event.getMessage().reply(":dizzy_face: " + getAsPing(event.getAuthor().getId()) + ", There was a bullet in the chamber, you died.").delay(Duration.ofSeconds(3)).queue();
                    RussianRoulette.players.remove(event.getAuthor());
                    chambers.get(getRandomNumber(1, 7) - 1).hasBullet = true;
                } else {
                    //event.getMessage().reply("u alive").queue();
                    event.getMessage().addReaction("U+1F60C").delay(Duration.ofSeconds(3)).queue();
                }
                deletingMessage1 = event.getMessage();
                if (RussianRoulette.players.size() > 1) {
                    RussianRoulette.turn.increaseInteger();
                    currentChamber.increaseInteger();
                    event.getChannel().sendMessage(getAsPing(RussianRoulette.players.get(RussianRoulette.turn.getInteger() - 1).getId()) + " its your turn. spin or shoot.").queue(message -> deletingMessage = message);
                } else {
                    event.getChannel().sendMessage(":trophy: " + getAsPing(RussianRoulette.players.get(0).getId()) + " is the last one standing, winner.").delay(Duration.ofSeconds(4)).queue();
                    RussianRoulette.running = false;
                }
            }
        }
    }

    String getAsPing(String id) {
        return "<@" + id + ">";
    }

    String getSuffix(int number) {
        switch (number) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
        }
        return "th";
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
