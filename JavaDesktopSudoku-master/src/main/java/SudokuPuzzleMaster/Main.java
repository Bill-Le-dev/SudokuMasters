package SudokuPuzzleMaster;

import SudokuPuzzleMaster.constants.SimpleAudioPlayer;
import SudokuPuzzleMaster.userinterface.Printer;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import static SudokuPuzzleMaster.computationlogic.GameGenerator.difficulty;
import static SudokuPuzzleMaster.constants.SimpleAudioPlayer.message;

public class Main {
    private static final File GAME_DATA = new File(
        System.getProperty("user.home"),
        "gamedata.txt"
    );
    static SimpleAudioPlayer audio;

    static {
        try {
            audio = new SimpleAudioPlayer();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public Main() {
    }

    public static void deleteGameData() throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
        } catch (IOException e) {
            throw new IOException("Unable to access Game Data");
        }
    }

    public static void menu() throws IOException {
        JFrame frame = new JFrame("Sudoku-Masters");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(161,19,280, 192);
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        // Create the "File" menu
        JMenu fileMenu = new JMenu("Settings");
        JMenuItem restart = new JMenuItem("Reset Board");
        JMenuItem instructions = new JMenuItem("How to Play");
        JMenuItem music = new JMenuItem("Music Options");
        JMenuItem printBoard = new JMenuItem("Print Board");
        JMenuItem difficulty = new JMenuItem("Set difficulty");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(restart);
        fileMenu.add(instructions);
        fileMenu.add(music);
        fileMenu.add(printBoard);
        fileMenu.add(difficulty);
        fileMenu.addSeparator(); // Add a separator line
        fileMenu.add(exitItem);
        // Create the "Help" menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        // Add menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        // Add action listeners to menu items
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start a new game
                JOptionPane.showMessageDialog(frame, "Deleting game file data...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(frame, "Done! Exiting game. Play again to refresh.");

                try {
                    deleteGameData();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });

        music.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Music control options
                String musicOption = JOptionPane.showInputDialog(frame, "Would you like to pause, resume, or restart?");
                if (musicOption.equalsIgnoreCase("pause")) {
                    audio.pause();
                    if (message != null) {
                        JOptionPane.showMessageDialog(frame, message);
                    }
                }
                if (musicOption.equalsIgnoreCase("resume")) {
                    try {
                        audio.resumeAudio();
                        if (message != null) {
                            JOptionPane.showMessageDialog(frame, message);
                        }
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (musicOption.equalsIgnoreCase("restart")) {
                    try {
                        audio.restart();
                    } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                //SECRET AUDIO METHOD!!!!!
                if (musicOption.equalsIgnoreCase("jump")) {
                    int timeframe = Integer.parseInt(JOptionPane.showInputDialog(frame, "time stamp:"));
                    try {
                        audio.jump(timeframe);
                    } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start a new game
                JOptionPane.showMessageDialog(frame,
                    "The Sudoku grid is a 9x9 square, divided into nine smaller 3x3 squares.\n" +
                    "Objective:\n" +
                    "\n" +
                    "Fill the grid so that every row, every column, and each 3x3 square contains all the numbers from 1 to 9.\n" +
                    "Specific Rules\n" +
                    "Unique Numbers in Rows:\n" +
                    "\n" +
                    "Each row must have the numbers 1 through 9 without repeating any numbers.\n" +
                    "Unique Numbers in Columns:\n" +
                    "\n" +
                    "Each column must have the numbers 1 through 9 without repeating any numbers.\n" +
                    "Unique Numbers in 3x3 Squares:\n" +
                    "\n" +
                    "Each of the nine 3x3 squares must have the numbers 1 through 9 without repeating any numbers.\n" +
                    "Press 0 or space or backspace on a square to revert it back to an empty square");
            }
        });
        printBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Robot robot = new Robot();
                    Rectangle rectangle = new Rectangle(475,0,640, 800);
                    BufferedImage image = robot.createScreenCapture(rectangle);
                    ImageIO.write(image,"png",new File("Screenshot.png"));
                    Printer printer = new Printer();
                    printer.printing(image);
                } catch (AWTException | IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        difficulty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"1. Restart the game\n2.Set difficulty");
            }
        });
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                JOptionPane.showMessageDialog(frame,"Ok, exiting program...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show about dialog
                JOptionPane.showMessageDialog(frame, "Sudoku Game\nMade for ISC3U summative project, enjoy!");
            }
        });
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }
        private static long startTime = 0;
        private static long stopTime = 0;
        private static boolean running = false;

        //start counting time
        public static void start() {
            startTime = System.currentTimeMillis();
            running = true;
        }


        public void stop() {
            stopTime = System.currentTimeMillis();
            running = false;
        }

        //elapsed time in seconds
        public static long getElapsedTimeSecs() {
            long elapsed;
            if (running) {
                elapsed = ((System.currentTimeMillis() - startTime) / 1000);
            } else {
                elapsed = ((stopTime - startTime) / 1000);
            }
            return elapsed;
        }

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        audio.play();
        //opening frame
        JFrame frame = new JFrame("Sudoku Masters");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,1690, 1960);
        frame.setVisible(true);
        //set difficulty level
        difficulty = JOptionPane.showInputDialog(frame, "Welcome to Sudoku Masters! Set difficulty: \uD83E\uDD17 e a s y...medium...HARD...M A S T E R \uD83D\uDC80:");
        while (!(difficulty.equalsIgnoreCase("easy")||difficulty.equalsIgnoreCase("medium")||difficulty.equalsIgnoreCase("hard")||difficulty.equalsIgnoreCase("master"))) {
            JOptionPane.showMessageDialog(null,"Please enter a difficulty level!");
            difficulty = JOptionPane.showInputDialog(frame, "Welcome to Sudoku Masters! Set difficulty: \uD83E\uDD17 e a s y...medium...HARD...M A S T E R \uD83D\uDC80:");
        }
        //start time
        start();
        frame.setVisible(false);
        menu();
        SudokuApplication.main(new String[]{});
    }
}
