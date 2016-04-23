package com.asb.ms;

import com.asb.ms.conductor.impl.ConsoleGameConductorImpl;

public class Main {

    public static void main(String[] args) {
        Thread game = new Thread(new ConsoleGameConductorImpl());
        game.start();
    }
}
