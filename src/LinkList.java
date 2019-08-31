/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 18-Nov-2005
 * Time: 11:12:28
 * This class creates a doubly connected link list that is written to by DataCapture
 * rountines and purged by the DataWriter
 */
public class LinkList {
    /**
     * initialise the tail to null for new instance
     */
    public Element tail = null;
    /**
     * initialise the head to null for new instance
     */
    public Element head = null;
    /**
     * intialise the element count to 0 for new instance - don't remember why its static!
     */
    public static int counter = 0;
    private Element readerElement = null;
    /**
     *   does the link list still have data
     */
    public boolean stillHaveData = true;
    /**
     * is the data capture mechanism still producing data
     */
    public boolean stillProducing = true;
    private int sampleSizeInBits;
    private float [] floatArray;
    private int [] intArray;
    private int sizeOfIntArray;
    private int tempByteBufferSize;
    private int numBitsInByte = 8;
    private byte [][] newBuffer;
    private int numberOfBuffers;
    private String startTime;
    private String endTime;
    private int currentBuffer;

    /**
     * Initialise the link list. It should be created every time a new run is undertaken
     * @param newBuffer a two dimensional array for buffer swapping
     * @param numberOfBuffers number of buffers used
     */
    public LinkList(byte [][] newBuffer, int numberOfBuffers) {
        this.newBuffer = newBuffer;
        this.numberOfBuffers = numberOfBuffers;
    }

    /**
     * Constucts the appropriate sized array to store the converted byte array
     * @param sampleSizeInBits  number of bits used in sampling
     * @param tempByteBufferSize  size of byte buffer used
     */
    public void setBitSize(int sampleSizeInBits, int tempByteBufferSize) {
        this.sampleSizeInBits = sampleSizeInBits;
        this.tempByteBufferSize = tempByteBufferSize;
        sizeOfIntArray = (numBitsInByte * tempByteBufferSize) / sampleSizeInBits;
        intArray = new int[sizeOfIntArray];
        floatArray = new float[sizeOfIntArray];
    }

    /**
     * Sets the internal buffer label for addition to the linked list
     * and adds it to the link list
     * @param currentBuffer
     * @param startTime
     * @param endTime
     */
    public void setBuffer(int currentBuffer, String startTime, String endTime){
        this.currentBuffer = currentBuffer;
        this.startTime = startTime;
        this.endTime = endTime;
        pushElement(newBuffer[currentBuffer]);

       System.out.println("Linklist using buffet " + currentBuffer);
    }

    /**
     * pushes the new buffer onto the link list and increments the link list internal counter
     * @param tempBuffer
     */
    public synchronized void pushElement(byte [] tempBuffer) {


        // I don't think I should be doing this instead I should simply be storing
        // the byte array.
        // The conversion should simply go into a static conversion class

        float [] floatArray;
        floatArray = convertByteToReal(tempBuffer);
        // modified 7/3/07 so that the data write can simply write a block of byte data
        // in one go

        byte [] newByteArray = new byte[tempBuffer.length];
        for(int i = 0; i < tempBuffer.length; i++) newByteArray[i] = tempBuffer[i];

        if (tail == null) {
            tail = new Element();
            tail.setData(floatArray);
            tail.setByteData(newByteArray);
            tail.setEndTime(endTime);
            tail.setStartTime(startTime);
            tail.setPreviousElement(null);
            tail.setNextElement(null);
            head = tail;
            counter++;
        } else {
            Element newElement;
            newElement = new Element();
            newElement.setData(floatArray);
            newElement.setByteData(newByteArray);
            newElement.setEndTime(endTime);
            newElement.setStartTime(startTime);
            head.setNextElement(newElement);
            newElement.setPreviousElement(head);
            head = newElement;
            head.setNextElement(null);
            counter++;
        }

        System.out.println("LinkList has " + counter + " elements");
        stillHaveData = true;
        notifyAll();
    }

    /**
     * pops an element from the tail of the link list after it has been writeen to disk
     * and decrements the element counter
     * @throws ReaderEmptykException
     */
    public synchronized void popElement() throws ReaderEmptykException {

        if (tail == null) {
            throw new ReaderEmptykException();
        }

        tail = tail.getNextElement();
        if (tail != null) tail.setPreviousElement(null);
        counter--;
        if (counter == 0) stillHaveData = false;

        notifyAll();
    }

    /**
     * returns a boolean depending on whether data is still being aquired or the
     * link list still has data.
     * @return boolean if no more data is available
     */
    public synchronized boolean carryOn() {
        notifyAll();
        if (stillProducing) {
            return true;
        } else if (stillHaveData) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the linklist element count
     * @return int
     */
    public int getElementCount() {
        return counter;
    }

    /**
     * Return the element at the head of the linklist
     * @return Element
     * @throws ReaderEmptykException if the list is empty
     */
    public Element getElement() throws ReaderEmptykException {
        if (tail == null) throw new ReaderEmptykException();
          return tail;
    }

    /**
     * Search the list for a string in the list
     * @param tmp string to be searched for
     * @throws ReaderEmptykException  if list is empty
     */
    public void search(String tmp) throws ReaderEmptykException {
        Element element = tail;
        if (element == null) throw new ReaderEmptykException();

        for (int index = 0; index < counter; index++) {
            if (element.getString().compareTo(tmp) == 0) {
                System.out.println(" found match in " + index +
                        " iterations " + tmp + " " +
                        element.getString());
                element = element.getNextElement();
            }
            return;
        }
    }

    /**
     * Convert the byte buffer into floats
     * @param tempBuffer
     * @return float []
     */
    private float [] convertByteToReal(byte [] tempBuffer) {
        float sample = 0;
        int j = 0;
        System.out.println("Size of buffers " + tempBuffer.length + " " +
                sizeOfIntArray);

        for (int i = 0; i < sizeOfIntArray; i++) {
            intArray[i] = 0;

            if (sampleSizeInBits == 16) {
                intArray[i] = (tempBuffer[j + 0] & 0xFF)
                        | (tempBuffer[j + 1] << 8);
                sample = intArray[i] / 32768.0F;
                j = j + 2;
            } else if (sampleSizeInBits == 24) {
                intArray[i] = (tempBuffer[j + 0] & 0xFF)
                        | ((tempBuffer[j + 1] & 0xFF) << 8)
                        | (tempBuffer[j + 2] << 16);
                sample = intArray[i] / 8388606.0F;
                j = j + 3;
            }

            floatArray[i] = sample;
        }
        return floatArray;
    }

    /**
     * Print bits of an integer number in ascii - diagnostic
     * @param number
     * @param startBit
     * @param numBits
     */
    public void printBits(int number, int startBit, int numBits) {
        int bitmask;
        bitmask = 0x80000000 >>> startBit;
        numBits += startBit;
        for (int i = startBit; i < numBits; ++i) {
            if ((number & bitmask) == 0)
                System.out.print('0');
            else
                System.out.print('1');
            bitmask >>>= 1;
        }
        System.out.println();
    }



}
