package me.itsmas.tab.util;

import java.util.logging.Logger;

public final class Logs
{
    private static final Logger logger = UtilServer.getPlugin().getLogger();

    public static void info(String msg, Object... params)
    {
        logger.info(String.format(msg, params));
    }

    public static void error(String msg, Object... params)
    {
        logger.severe(String.format(msg, params));
    }
}
