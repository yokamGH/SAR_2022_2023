package broker_channel.manager;

import broker_channel.brokers.Broker;
import broker_channel.brokers.BrokerImplem;
import broker_channel.tasks.TaskA;
import broker_channel.tasks.TaskB;

/**
 *
 * @author SIMO YOKAM Georges Harrisson | INFO5
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Broker broker = new BrokerImplem("SAR_PC");
        TaskA taskA = new TaskA(2035, broker); 
        TaskB taskB = new TaskB(20, broker, "SAR_PC");
        // TaskA send the message "Hello World !" to TaskB
        taskA.start();
        // TaskB read and print the sent message
        taskB.start();
    }
}
