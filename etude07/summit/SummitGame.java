import java.util.ArrayList;

/**
 *
 * @author Michael Albert
 */
public class SummitGame {

    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>();

        players.add(0, new HumanPlayer());
        players.add(1, new RollPlayer());
        players.add(2, new RandomPlayer());
        players.add(3, new CodePlayer());
//        players.add(4, new RandomPlayer());
//        players.add(5, new RollPlayer());

        Round r = new Round(players.toArray(new Player[0]));
    }
    
}
