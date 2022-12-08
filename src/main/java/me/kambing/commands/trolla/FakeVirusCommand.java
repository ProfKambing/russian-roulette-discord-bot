package me.kambing.commands.trolla;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FakeVirusCommand extends Command {
    public FakeVirusCommand() {
        this.name = "fakevirus";
        this.help = ";fakevirus <message>";
        this.ownerCommand = true;
    }

    private final Random rd = new Random();
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int width = (int) screenSize.getWidth();
    private final int height = (int) screenSize.getHeight();


    public void BlockMouse() throws AWTException {
        Robot rb = new Robot();
        rb.mouseMove(rd.nextInt(width), rd.nextInt(height));

    }

    public void Popup(String message) {
        JWindow win = new JWindow();
        JLabel label = new JLabel("You Have Been Hacked!!! \n" + message, JLabel.CENTER);
        win.add(label);
        win.setSize(200, 75);
        win.setLocation(rd.nextInt(width), rd.nextInt(height));
        win.setVisible(true);

    }

    @Override
    protected void execute(CommandEvent event) {
        try {
            BlockMouse();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 1000; i++) {
            Popup(event.getMessage().getContentRaw().split(" ")[1]);
        }
    }
}

