package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import Main.*;

public class FileChooser {
    private JFileChooser jFileChooser;
    private ResultOutputDialog dialog;
    private String fileName;

    public FileChooser(ResultOutputDialog dialog) {
        jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileNameExtensionFilter("txt files (*.txt)", "txt"));
        jFileChooser.showOpenDialog(null);

        if (dialog != null) {
            dialog.clearResultOutputDialog();
            this.dialog = dialog;
        } else {
            this.dialog = new ResultOutputDialog();
        }
        runFileChooser();
    }

    private void runFileChooser() {
        System.out.println("------------------\n-- Reading File --\n------------------\n");
        File file = jFileChooser.getSelectedFile();

        if (file == null)
            return;

        fileName = file.getName();

        RoomAssignment roomAssignment = new RoomAssignment();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String readLine;

            Inhabitant curInhabitant = null;

            while ((readLine = bufferedReader.readLine()) != null) {
                if (readLine.equals("") || readLine.equals(" ")) {
                    continue;
                }
                System.out.println("Current Line: |" + readLine + "|");

                if (!readLine.contains("+") && !readLine.contains("-")) {
                    System.out.println("\nNAME: |" + readLine + "|");
                    //readLine = name (nur buchstaben, kein + oder -)
                    if (roomAssignment.inhabitants.contains(new Inhabitant(readLine))) {
                        //inhabitant schon in liste
                        System.out.println("Inhabitant already in list! | " + roomAssignment.searchInhabitant(readLine, false));
                        curInhabitant = roomAssignment.searchInhabitant(readLine, false);
                    } else {
                        curInhabitant = new Inhabitant(readLine, roomAssignment);
                        roomAssignment.inhabitants.add(curInhabitant);
                    }
                } else if (readLine.substring(0, 1).equals("+")) {
                    //wenn readLine = + (first char is +)
                    String[] names = readLine.split(" ");
                    //get all names
                    for (String s : names) {
                        if (!s.contains("+")) {
                            System.out.println("PLUS WISH NAME: |" + s + "|");
                            if (curInhabitant != null) {
                                //wenn string ein name
                                if (roomAssignment.inhabitants.contains(new Inhabitant(s))) {
                                    curInhabitant.addPlusWish(roomAssignment.searchInhabitant(s, false));
                                    System.out.println("Added new Plus Wish with existing inhabitant.");
                                } else {
                                    Inhabitant newInhabitant = new Inhabitant(s, roomAssignment);
                                    curInhabitant.addPlusWish(newInhabitant);
                                    roomAssignment.inhabitants.add(newInhabitant);
                                    System.out.println("Added new Plus Wish with new inhabitant.");
                                }
                            }
                        }
                    }
                } else if (readLine.substring(0, 1).equals("-")) {
                    //wenn readLine = + (first char is +)
                    String[] names = readLine.split(" ");
                    //get all names
                    for (String s : names) {
                        if (!s.contains("-")) {
                            System.out.println("MINUS WISH NAME: |" + s + "|");
                            if (curInhabitant != null) {
                                //wenn string ein name
                                try {
                                    if (roomAssignment.inhabitants.contains(new Inhabitant(s))) {
                                        curInhabitant.addMinusWish(roomAssignment.searchInhabitant(s, false));
                                        System.out.println("Added new Minus Wish with existing inhabitant.");
                                    } else {
                                        Inhabitant newInhabitant = new Inhabitant(s, roomAssignment);
                                        curInhabitant.addMinusWish(newInhabitant);
                                        roomAssignment.inhabitants.add(newInhabitant);
                                        System.out.println("Added new Minus Wish with new inhabitant.");
                                    }
                                } catch (Exception e) {
                                    showResult(e, null);
                                    return;
                                }
                            }
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n------------------\n------------------\n------------------\n");

        showResult(roomAssignment.startRoomAssignment(), roomAssignment.getOutput());
    }

    private void showResult(Exception e, List<String> output) {
        dialog.showResultOutputDialog(e, output, fileName);
        dialog.pack();
        dialog.setVisible(true);
    }
}
