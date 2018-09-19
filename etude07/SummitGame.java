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
        int numRounds = 200;
        int numPlayers = 5;
        ArrayList<Player> players = new ArrayList<>();
        int[] winners = new int[numPlayers];
        //players.add(0, new HumanPlayer());
        //players.add(1, new RollPlayer());
        players.add(0, new CombinedPlayer());
        players.add(1, new BotPlayer());
        players.add(2, new RandomPlayer());
        players.add(3, new DeductionPlayer());
        players.add(4, new RollPlayer());
        // players.add(5, new RollPlayer());
        int[] gameWinners = new int[numPlayers];
        for (int game = 0; game < 50; game++){
            winners = new int[numPlayers];
            for (int i = 0; i < numRounds*numPlayers; i++){
                Round r = new Round(players.toArray(new Player[0]));
                //winners[r.winner]++;
                winners[r.winner] += r.getPot();
                //ArrayList<Player> temp = new ArrayList<>();
                /*ArrayList<Player> temp = new ArrayList<>();
                  for(int j = 0;j<numPlayers-1;j++){
                  //temp[j] = players[j+1];
                  temp.add(j, players.get(j+1));
                  }
                  temp.add(numPlayers-1, players.get(0));
                  players = temp;*/
            }
            int gameWinner = 0;
            for (int i = 0; i < winners.length; i++){
                if (winners[i] > winners[gameWinner]){
                    gameWinner = i;
                }
                System.out.println("Bleh " + i + " " + winners[i]);
            }
            gameWinners[gameWinner] ++;
        }
        for (int n = 0; n < numPlayers; n++){
            System.out.println("Game points " + n + " " + gameWinners[n]);
        }
    }
    
}
