import java.util.ArrayList;

/**
 *
 * @author Tom Adams
 * @author Max Huang
 * @author Mitchie Maluschnig
 * @author Asher Statham
 */
public class SummitGame {

    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>();

        players.add(0, new HumanPlayer());
        //players.add(1, new RollPlayer());
        players.add(1, new DeductionPlayer());
        // players.add(2, new RandomPlayer());
        // players.add(3, new RollPlayer());
        // players.add(4, new RandomPlayer());
        // players.add(5, new RollPlayer());

        Round r = new Round(players.toArray(new Player[0]));
    }
    
}
