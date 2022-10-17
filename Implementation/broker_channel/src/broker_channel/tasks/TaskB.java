/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package broker_channel.tasks;

import broker_channel.brokers.Broker;
import broker_channel.channels.Channel;
import broker_channel.channels.ChannelImplem;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author georg
 */
public class TaskB extends Thread {
    
    private Broker broker;
    private final int port;
    private final String name;
    
    public TaskB(int port, Broker broker, String name){
        this.port = port;
        this.broker = broker;
        this.name = name;
    }
    
    public void run(){
        //Accepter les connexions 
        Channel channel = broker.connect(name, port);
        byte[] bytesRecus = new byte[12];
	try {
            channel.read(bytesRecus, 0, 12);
	} catch (IOException e) {
            System.out.println("je suis déconnecté"); //WHY ??
	}
	String msgString = new String(bytesRecus, StandardCharsets.UTF_8);
	System.out.println("message reçu : " + msgString);       
    }
}
