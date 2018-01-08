package Main;

import java.util.ArrayList;
import java.util.List;

public class RoomAssignment {
    private List<Room> allRooms;
    public List<Inhabitant> inhabitants;
    private List<String> outputs;

    public List<String> getOutput() {
        return outputs;
    }

    void addOutput(String output) {
        this.outputs.add(output);
    }

    public RoomAssignment() {
        allRooms = new ArrayList<>();
        inhabitants = new ArrayList<>();
        outputs = new ArrayList<>();
    }

    public Exception startRoomAssignment() {
        System.out.println("\n-----------------\n-----------------\n-----------------\n");
        System.out.println("\n------------------\n----- RESULT -----\n------------------\n");
        try {
            checkWishes();
            checkAloneInhabitants();
            return null;
        } catch (Exception e) {
            System.out.println("Fehler!");
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            e.printStackTrace();
            return e;
        } finally {
            System.out.println("\nZimmerkonstellation:\n");
            for (int i = 0; i < allRooms.size(); i++) {
                allRooms.get(i).printInformation(this, i+1);
            }

            System.out.println("\n------------------\n------------------\n------------------\n");
        }

    }

    private void checkWishes() throws Exception {
        System.out.println("-----------------\n----- DEBUG -----\n-----------------\n");
        for (Inhabitant curInhabitantToCheck : inhabitants) {
            System.out.println("--------------------------\n\nCheck: " + curInhabitantToCheck.getName());
            //Jeden Schueler in der Liste durchgehen

            //Liste mit Plus Wuenschen des aktuellen Schuelers
            List<Inhabitant> inhabitantPlusWishes = curInhabitantToCheck.getPlusWishes();

            for (Inhabitant plusWishInhabitant : inhabitantPlusWishes) {
                System.out.println("\n    Plus Wunsch: " + plusWishInhabitant.getName());
                if (plusWishInhabitant.getRoom() == curInhabitantToCheck.getRoom()) {
                    continue;
                }
                //Jeden Schueler, die ein Plus Wunsch des aktuellen Schuelers ist
                //Liste mit Minus Wuenschen des Schuelers, der in der Plus Wunsch Liste des aktuellen Schuelers ist
                List<Inhabitant> plusWishInhabitantMinusWishes = plusWishInhabitant.getMinusWishes();

                for (Inhabitant p : plusWishInhabitantMinusWishes) {
                    System.out.println("        Minus Wunsch von Plus Wunsch: " + p.getName());
                    //Jede Inhabitant in der Minus Wunsch Liste des Schuelers, der in der Plus Wunsch Liste des aktuellen Schuelers ist
                    if (p.equals(curInhabitantToCheck)) {
                        //Aktueller Schueler ist in der Minus Wunsch Liste!
                        //Exception
                        throw new Exception(curInhabitantToCheck.getName() + " will mit " +
                                plusWishInhabitant.getName() + " in einem Zimmer sein, " +
                                "aber " + plusWishInhabitant.getName() + " nicht mit " + curInhabitantToCheck.getName() + ".");
                    }
                }
                //Aktueller Schueler ist kein Minus Wunsch seines Plus Wunsch Schuelers

                Inhabitant errorInhabitant = curInhabitantToCheck.getRoom().checkOtherRoom(plusWishInhabitant.getRoom());

                if(errorInhabitant == null) {

                    changeRooms(plusWishInhabitant.getRoom(), curInhabitantToCheck.getRoom());
                } else {
                    throw new Exception(curInhabitantToCheck.getName() + " will mit " + plusWishInhabitant.getName() + " in ein Zimmer, " +
                            "aber " + errorInhabitant.getName() + " kann nicht ins andere Zimmer.");
                }
            }
        }
    }


    private void checkAloneInhabitants() {
        for (Inhabitant curInhabitantToCheck : inhabitants) {
            //Liste mit Plus Wuenschen des aktuellen Schuelers
            List<Inhabitant> inhabitantPlusWishes = curInhabitantToCheck.getPlusWishes();

            if (inhabitantPlusWishes.size() == 0 && curInhabitantToCheck.getRoom().getInhabitants().size() == 1) {
                //Aktueller Schueler hat keine Plus Wuensche und ist alleine in einem Zimmer
                for (Room r : allRooms) {
                    //Pruefen, ob dieser Schueler in irgendein bisheriges Zimmer passt
                    if (r != curInhabitantToCheck.getRoom()) {
                        if (curInhabitantToCheck.getRoom().checkOtherRoom(r) == null) {
                            //Kann in dieses Zimmer
                            changeRooms(curInhabitantToCheck.getRoom(), r);
                            break;
                        }
                    }
                }
            }
        }
    }

    public Inhabitant searchInhabitant(String name, boolean fullName) {
        for (Inhabitant i : inhabitants) {
            if (fullName && i.getName().equals(name)) {
                return i;
            } else if (i.getForename().equals(name)) {
                return i;
            }
        }
        return null;
    }

    private void changeRooms(Room oldRoom, Room newRoom) {
        newRoom.addInhabitants(oldRoom.getInhabitants());
        allRooms.remove(oldRoom);
    }

    void addRoomToList(Room newRoom) {
        allRooms.add(newRoom);
    }
}
