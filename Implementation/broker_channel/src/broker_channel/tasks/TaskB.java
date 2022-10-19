/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package broker_channel.tasks;

import broker_channel.brokers.Broker;
import broker_channel.channels.Channel;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class TaskB extends Thread {
    
    private Broker broker;
    private final int port;
    private final String name;
    
    public TaskB(int port, Broker broker, String name){
        this.port = port;
        this.broker = broker;
        this.name = name;
    }
    
    @Override
    public void run(){
        Channel channel = broker.connect(name, port); // Connection with TaskA
        if (channel == null){
            System.out.println("TaskB: Connection with TaskA failed");
        }
        else {
            byte[] bytesRecus = new byte[12];
            try {
                channel.read(bytesRecus, 0, 12);
            } catch (IOException e) {
                System.out.println("Offline"); 
            }
            String msgString = new String(bytesRecus, StandardCharsets.UTF_8);
            System.out.println("Message reçu : " + msgString);
        }          
    }
}
