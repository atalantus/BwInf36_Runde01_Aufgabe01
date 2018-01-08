package Main;

import java.util.List;
import java.util.ArrayList;

public class Inhabitant {
    private List<Inhabitant> plusWishes;
    private List<Inhabitant> minusWishes;
    private String forename;
    private String surname;
    private Room room;

    public Inhabitant(String forename, RoomAssignment roomAssignment) {
        this.forename = forename;
        this.surname = "";
        plusWishes = new ArrayList<>();
        minusWishes = new ArrayList<>();
        room = new Room(this);
        roomAssignment.addRoomToList(room);
    }

    public Inhabitant(String forename) {
        this.forename = forename;
        this.surname = "";
        plusWishes = new ArrayList<>();
        minusWishes = new ArrayList<>();
    }

    public void addPlusWish(Inhabitant inhabitant) {
        plusWishes.add(inhabitant);
    }

    public void addMinusWish(Inhabitant inhabitant) throws Exception {
        if(inhabitant == this) {
            throw new Exception(getName() + " will nicht mit sich selbst ins Zimmer.");
        }

        minusWishes.add(inhabitant);
        if (room != null) {
            room.addRoomMinusWish((inhabitant));
        }
    }

    String getName() {
        if (!surname.equals("")) {
            return forename + " " + surname;
        } else {
            return forename;
        }
    }

    String getInformation() {
        String information = "";
        if (!surname.equals("")) {
            information += forename + " " + surname;
        } else {
            information += forename;
        }
        information += " (+ " + getPlusWishesString() + " | - " + getMinusWishesString() + ")";
        return information;
    }

    List<Inhabitant> getPlusWishes() {
        return plusWishes;
    }

    List<Inhabitant> getMinusWishes() {
        return minusWishes;
    }

    private String getPlusWishesString() {
        StringBuilder plusWishesString = new StringBuilder();
        for (int i = 0; i < plusWishes.size(); i++) {
            plusWishesString.append(plusWishes.get(i).getName());
            if (i != plusWishes.size()-1) {
                plusWishesString.append("; ");
            }
        }
        return plusWishesString.toString();
    }

    private String getMinusWishesString() {
        StringBuilder minusWishesString = new StringBuilder();
        for (int i = 0; i < minusWishes.size(); i++) {
            minusWishesString.append(minusWishes.get(i).getName());
            if (i != minusWishes.size()-1) {
                minusWishesString.append("; ");
            }
        }
        return minusWishesString.toString();
    }

    String getForename() {
        return forename;
    }

    Room getRoom() {
        return room;
    }

    void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object object) {
        if (getClass() != object.getClass())
            return false;
        Inhabitant other = (Inhabitant) object;
        return other.getName().equals(getName());
    }
}
