/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 27-Dec-2005
 * Time: 01:03:48
 * To change this template use File | Settings | File Templates.
 */
public class SystemState {

    private boolean isRunning = false;

    public SystemState(boolean running) {
        isRunning = running;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

}
