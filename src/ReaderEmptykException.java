
/**
 * Created by IntelliJ IDEA.
 * User: Roger philp
 * Date: 28-Oct-2005
 * Time: 10:54:26
 * To change this template use File | Settings | File Templates.
 */
public class ReaderEmptykException extends Exception{
    public ReaderEmptykException(){
    }

    public String getError(){
        String msg = "Reader buffer is empty";
        return msg;
    }

}
