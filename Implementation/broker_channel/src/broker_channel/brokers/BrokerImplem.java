package broker_channel.brokers;

import broker_channel.channels.Channel;
import broker_channel.channels.ChannelImplem;
import broker_channel.channels.CircularBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BrokerImplem extends Broker {
    
    private String name;
    private int port;
    private final int buffSize = 256;
    private CircularBuffer buffer1;
    private CircularBuffer buffer2;
    private ChannelImplem senderChannel;
    private ChannelImplem receiverChannel;

    public BrokerImplem(String name) {
        super(name);
        this.buffer1 = new CircularBuffer(buffSize);
        this.buffer2 = new CircularBuffer(buffSize);
        this.port = -1;
        this.name = name;
    }
    
    /**
     * Blocking acceptance on a given port
     *
     * @param int port - Broker port number
     * @return Channel - Communication channel 
	 *
     */

    public Channel accept(int port) {
        synchronized(this){
            senderChannel = new ChannelImplem(buffer1, buffer2, buffSize);
            this.port = port;
            System.out.println("TaskA: opening connections on the port");
            while (receiverChannel == null){
                try {
                    System.out.println("TaskA: waiting for connections on the port...");
                    wait();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); 
                    System.out.println("Thread Interrupted");
                }
            }
            System.out.println("TaskA: connection well etablished on the port");
            notify();
        }
        
	return senderChannel;
    }

    /**
     * Blocking connection to broker
     *
     * @param int port - Broker port number
     * @param String name - Broker name
     * @return Channel - Communication channel 
	 *
     */
    
    public Channel connect(String name, int port) {
        synchronized(this){
            receiverChannel = new ChannelImplem(buffer2, buffer1, buffSize); //Les buffers sont croisés
            while(senderChannel == null){
                try {
                    System.out.println("TaskB: waiting the connection acceptance...");
                    wait();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); 
                    System.out.println("Thread Interrupted");
                }
            }
            //Port number or the name are wrong 
            if ((port != this.port) || (name != this.name)){
                System.out.println("TaskB: Invalid connection parameters");
                receiverChannel.disconnect(); //Logging out   
            }
            notify();
        } 
        return receiverChannel;
    }
    
}
