package Devices;

import java.util.regex.Matcher;

public interface Command {
    public void execute(ThreadChannel channel, Matcher match);
}
