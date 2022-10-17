/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package broker_channel.manager;

import broker_channel.brokers.Broker;
import broker_channel.brokers.BrokerImplem;
import broker_channel.tasks.TaskA;
import broker_channel.tasks.TaskB;

/**
 *
 * @author georg
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Hello World !!!!!");
        
        Broker broker = new BrokerImplem("Simal_PC");
        TaskA taskA = new TaskA(2035, broker);
        TaskB taskB = new TaskB(2035, broker, "Simal_PC");
        taskA.start();
        taskB.start();
    }
    
}
