package com.asb.ms.conductor.impl;

import com.asb.ms.brain.Game;
import com.asb.ms.brain.impl.GameImpl;
import com.asb.ms.conductor.GameConductor;
import com.asb.ms.exceptions.InvalidCellException;
import com.asb.ms.exceptions.InvalidGameException;
import com.asb.ms.exceptions.InvalidOperationException;
import com.asb.ms.exceptions.InvalidStateException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Console Game Conductor Impl.
 * Created by arjun on 23/04/16.
 */
public class ConsoleGameConductorImpl implements GameConductor, Runnable {

    @Override
    public void run() {
        this.startGame();
    }

    @Override
    public void startGame() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            Game game = readGameData(reader);
            conductGame(reader, game);
            concludeGame(game);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidGameException e) {
            System.err.println("Invalid Game data: " + e.getMessage());
        } catch (InvalidStateException e) {
            System.err.println("Invalid State of the game: " + e.getMessage());
        }
    }

    private void concludeGame(Game game) throws InvalidStateException {
        switch (game.getGameState()) {
            case NEW:
            case IN_PROGRESS:
                throw new InvalidStateException("Invalid State.");
            case LOST:
                System.out.println("Oops, you stepped on a mine ! Game Over !");
                break;
            case WON:
                System.out.println("Wow, you cleared the minefield ! Game Over !");
                break;
        }
    }

    private void conductGame(BufferedReader reader, Game game) throws IOException {
        while (!game.isOver()) {
            try {
                System.out.print("Enter option: ");
                game.doOperation(reader.readLine());
                System.out.println(game.getGameData());
            } catch (InvalidOperationException e) {
                System.err.println("Invalid Operation: " + e.getMessage());
            } catch (InvalidCellException e) {
                System.err.println("Invalid Cell: " + e.getMessage());
            }
        }
    }

    private Game readGameData(BufferedReader reader) throws InvalidGameException, IOException {
        System.out.print("Enter the minefield layout: ");
        Game game = new GameImpl(reader.readLine());
        System.out.println(game.getGameData());
        return game;
    }
}
