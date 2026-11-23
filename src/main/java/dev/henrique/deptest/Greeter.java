package dev.henrique.deptest;

import org.apache.commons.lang3.StringUtils;

public final class Greeter {
    private Greeter() {}

    public static String greet(String name) {
        return "Olá, " + StringUtils.capitalize(StringUtils.defaultIfBlank(name, "mundo")) + "!";
    }
}
