package we.software.mastermind;

/**
 * Created by ralph on 3/24/17.
 */
public class NormalPeg extends Peg {

    @Override
    int getXPos() {
        return 0;
    }

    @Override
    int getYPos() {
        return 0;
    }

    @Override
    String getColor() {
        return null;
    }

	@Override
	boolean selected() {
		// TODO Auto-generated method stub
		return false;
	}
}
