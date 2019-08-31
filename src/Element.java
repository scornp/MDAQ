/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 18-Nov-2005
 * Time: 11:13:28
 * This class creates the basic data storage unit
 */

class Element{
    private String myString;
    private Element nextElement = null;
    private Element previousElement = null;
    private float []  myData;
    private byte [] myByteData;
    private boolean hasBeenDrawn = false;

    public byte[] getByteData() {
        return myByteData;
    }

    public void setByteData(byte[] myByteData) {
        this.myByteData = myByteData;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    private String startTime;
    private String endTime;

    public Element(){}

    public void setHasBeenDrawn(){
           hasBeenDrawn = true;
    }

    public boolean getHasBeenDrawn(){
     return  hasBeenDrawn;  
    }

    public Element(String tmp){
        myString = tmp;
    }

    public void setData(float [] floatArray){
        myData = floatArray;
    }

    public float []  getData(){
        return myData;
    }

    public void displayData(){
        System.out.println(myData);
    }

    public  void setNextElement(Element tmp) {
        nextElement = tmp;
    }

    public  void setPreviousElement(Element tmp){
        previousElement = tmp;
    }

    public Element getNextElement(){
        return (nextElement);
    }

    public Element getPreviousElement(){
        return (previousElement);
    }

    public  void displayContents(){
        System.out.println(myString);
    }

    String getString(){
        return (myString);
    }

}
