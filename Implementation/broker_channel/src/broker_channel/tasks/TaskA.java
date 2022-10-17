/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package broker_channel.tasks;

import broker_channel.brokers.Broker;
import broker_channel.channels.Channel;
import java.io.IOException;

/**
 *
 * @author georg
 */
public class TaskA extends Thread {
    
    private Broker broker;
    private final int port;
    
    public TaskA(int port, Broker broker){
       this.port = port; 
       this.broker = broker;
    }
    
    @Override
    public void run(){
        //Accepter les connexions 
        Channel channel = broker.accept(port);
        try {
            channel.write("Hello World!".getBytes(), 0, 12);
	} catch (IOException e) {
            System.out.println("je suis déconnecté");
	}        
    }
    
}
