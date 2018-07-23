/**
 *
 * @author Michael Albert
 */
public class RollPlayer implements Player {

    @Override
    public Action takeTurn(State s, int[] dice) {
        return Action.ROLL;
    }

    @Override
    public Action actAtShowdown(State s, int[] dice) {
        return Action.STAY;
    }

}
