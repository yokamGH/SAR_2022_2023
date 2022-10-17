/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package broker_channel.brokers;

import broker_channel.channels.Channel;
import broker_channel.channels.ChannelImplem;
import broker_channel.channels.CircularBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author georg
 */
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
    }
   

    public Channel accept(int port) {
        synchronized(this){
            senderChannel = new ChannelImplem(buffer1, buffer2, buffSize);
            this.port = port;
            System.out.println("Ouverture des connexions sur le port");
            while (receiverChannel == null){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); 
                    System.out.println("Thread Interrupted");
                }
            }
            System.out.println("Connexion acceptée sur le port");
            notify();
        }
        
	return senderChannel;
    }

	/**
	 * Connexion bloquante à un broker d'un nom ainsi que d'un port donnes
	 * 
	 * @param int    port - numero de port sur lequel se connecter
	 * @param String name - nom de la tache
	 * @return Channel - canal de communication entre les taches
	 **/
    
    public Channel connect(String name, int port) {
        synchronized(this){
            receiverChannel = new ChannelImplem(buffer2, buffer1, buffSize); //Les buffers sont croisés
            while(senderChannel == null){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); 
                    System.out.println("Thread Interrupted");
                }
            }
            notify();
        }
        
        return receiverChannel;
    }
    
}
