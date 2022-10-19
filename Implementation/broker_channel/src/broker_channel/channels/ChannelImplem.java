package broker_channel.channels;

 
import java.io.IOException;


public class ChannelImplem extends Channel{
    
    private CircularBuffer outputBuffer; //Output Buffer
    private CircularBuffer inputBuffer; //Input Buffer
    private int size; //Size of buffers
    private boolean isConnected = true;
    
    public ChannelImplem(CircularBuffer inputBuffer, CircularBuffer outputBuffer, int size){
        this.inputBuffer = inputBuffer;
        this.outputBuffer = outputBuffer;
        this.size = size;
    }
    
    public int write(byte[] bytes, int offset, int length) throws IOException {
        if (bytes == null){
            throw new NullPointerException("Task A: the message to write is null");
        }
        if (length > size){
            System.out.println("Error: the message length is greater than the buffer size");
            return -1;
        }
        synchronized(outputBuffer){
            int i =0;
            while(i < length){
                while(outputBuffer.full()){
                    try {
                        System.out.println("TaskA: the buffer length is full.....");
                        outputBuffer.wait();
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt(); 
                        System.out.println("Thread Interrupted");
                    }
                }
                outputBuffer.push(bytes[offset+i]);
                i++;
            }
            outputBuffer.notify();
            System.out.println("TaskA: message of length "+i+" is written");
            return i;
        }
    }
    
    
    public int read(byte[] bytes, int offset, int length) throws IOException {
	synchronized(inputBuffer){
            int i = 0;
            while(i < length){
                while(inputBuffer.empty()){
                    try {
                        System.out.println("TaskB: the buffer is empty...");
                        inputBuffer.wait();
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt(); 
                        System.out.println("Thread Interrupted");
                    }
                }
                bytes[offset+i] = inputBuffer.pull();
                i++;
            }
            inputBuffer.notify();
            System.out.println("TaskB: reading completed");
            return i;
        }
    }
    
    public void disconnect() {
        isConnected = false;
    }

    public boolean disconnected() {
        return !isConnected;
    }
}
