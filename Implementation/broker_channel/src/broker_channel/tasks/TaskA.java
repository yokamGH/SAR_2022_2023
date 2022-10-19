package broker_channel.tasks;

import broker_channel.brokers.Broker;
import broker_channel.channels.Channel;
import java.io.IOException;


public class TaskA extends Thread {
    
    private Broker broker;
    private final int port;
    
    public TaskA(int port, Broker broker){
       this.port = port; 
       this.broker = broker;
    }
    
    @Override
    public void run(){
        //Accepting connections 
        Channel channel = broker.accept(port);
        try {
            channel.write("Hello World!".getBytes(), 0, 12);
            System.out.println("Message envoyé : Hello World!");
	} catch (IOException e) {
            System.out.println("Offline");
	}        
    }
    
}
