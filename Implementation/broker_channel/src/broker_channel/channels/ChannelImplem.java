/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package broker_channel.channels;

 
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author georg
 */
public class ChannelImplem extends Channel{
    
    private CircularBuffer outputBuffer; //Output Buffer
    private CircularBuffer inputBuffer; //Input Buffer
    private int size;
    private boolean isConnected = true;
    
    public ChannelImplem(CircularBuffer inputBuffer, CircularBuffer outputBuffer, int size){
        this.inputBuffer = inputBuffer;
        this.outputBuffer = outputBuffer;
        this.size = size;
    }
    
    
    public int write(byte[] bytes, int offset, int length) throws IOException {
        if (bytes == null){
            throw new NullPointerException("The message to write is null");
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
                        System.out.println("The buffer length is full.....");
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
            System.out.println("Message send "+i);
            return i;
        }
    }
    
    
    public int read(byte[] bytes, int offset, int length) throws IOException {
	synchronized(inputBuffer){
            int i = 0;
            while(i < length){
                while(inputBuffer.empty()){
                    try {
                        System.out.println("The buffer is empty....");
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
            System.out.println("Message read");
            return i;
        }
    }
    
    public void disconnect() {
        isConnected = false;
    }

    public boolean disconnected() {
        return !isConnected;
    }
      
    
    /*private final int size = 256; //Size of the buffer
    private CircularBuffer messages = new CircularBuffer(size); //Output buffer
    private CircularBuffer inputBuffer = new CircularBuffer(size); //Input Buffer
    private Semaphore messageAvailable = new Semaphore(0);
    private Semaphore slotAvailable = new Semaphore(size);
    private Semaphore senderMutex = new Semaphore(1);
    private Semaphore receiverMutex = new Semaphore(1);
    
    private int in=0, out=0;
    
    public int write(byte[] bytes, int offset, int length) throws IOException {
        try {
            if (bytes == null){
                throw new NullPointerException("The message to send is null");
            }
            slotAvailable.acquire(length); 
            senderMutex.acquire();
            
            // Writing the message in the CircularBuffer
            for (int i=0; i<length; i++){
                messages.push(bytes[offset+i]);
            }
            
            senderMutex.release();
            messageAvailable.release();
            
            return length; //The size of the written message
            
        } catch (InterruptedException ex) {
            Logger.getLogger(ChannelImplem.class.getName()).log(Level.SEVERE, null, ex);
            return -1; //When some errors occured
        }
    
    }
    
    public int read(byte[] bytes, int offset, int length) throws IOException {
		return 0;
    }*/
    
    
}
