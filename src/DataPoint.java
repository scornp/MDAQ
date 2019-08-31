/**
 * Created by IntelliJ IDEA.
 * User: scornp
 * Date: 21-Nov-2006
 * Time: 16:48:15
 * To change this template use File | Settings | File Templates.
 */
public class DataPoint {
    public static int length = 0;
    DataPoint nextDataPoint;
    float data;

    DataPoint(float data){
       this.data = data;
       length++;
    }

    public float getData() {
        return data;
    }

    public void setNextDataPoint(DataPoint nextDataPoint) {
        this.nextDataPoint = nextDataPoint;
    }


    public DataPoint getNextDataPoint() {
        return nextDataPoint;
    }
}
