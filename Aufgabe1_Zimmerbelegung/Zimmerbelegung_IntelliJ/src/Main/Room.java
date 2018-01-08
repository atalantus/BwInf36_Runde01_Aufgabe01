package Main;

import java.util.ArrayList;
import java.util.List;

class Room {
    private List<Inhabitant> inhabitants;
    private List<Inhabitant> roomMinusWishes;

    Room(Inhabitant inhabitant) {
        inhabitants = new ArrayList<>();
        inhabitants.add(inhabitant);
        roomMinusWishes = new ArrayList<>();
        inhabitant.setRoom(this);
        roomMinusWishes.addAll(inhabitant.getMinusWishes());
    }

    void addInhabitants(List<Inhabitant> newInhabitants) {
        for (Inhabitant i : newInhabitants) {
            if (inhabitants.contains(i))
                continue;

            inhabitants.add(i);
            i.setRoom(this);
            roomMinusWishes.addAll(i.getMinusWishes());
        }
    }

    List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    private List<Inhabitant> getRoomMinusWishes() { return roomMinusWishes; }

    void addRoomMinusWish(Inhabitant newMinusWish) {
        roomMinusWishes.add(newMinusWish);
    }

    Inhabitant checkOtherRoom(Room otherRoom) {
        for (Inhabitant roomMinusWish : roomMinusWishes) {
            for (Inhabitant otherRoomInhabitant : otherRoom.getInhabitants()) {
                if (roomMinusWish.equals(otherRoomInhabitant)) {
                    return roomMinusWish;
                }
            }
        }
        for (Inhabitant otherRoomMinusWish : otherRoom.getRoomMinusWishes()) {
            for (Inhabitant roomInhabitant : this.inhabitants) {
                if (otherRoomMinusWish.equals(roomInhabitant)) {
                    return otherRoomMinusWish;
                }
            }
        }
        return null;
    }

    void printInformation(RoomAssignment roomAssignment, int roomNumber) {
        System.out.println("\n--- Zimmer " + roomNumber + " ---");
        roomAssignment.addOutput("--------------------");
        roomAssignment.addOutput("--- Zimmer " + roomNumber + " ---");
        roomAssignment.addOutput("--------------------");

        for (Inhabitant p : inhabitants) {
            System.out.println(p.getInformation());
            roomAssignment.addOutput(p.getInformation());
        }
        roomAssignment.addOutput(" ");
    }
}
