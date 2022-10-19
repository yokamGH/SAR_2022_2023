# DM : Message Queus Homework

## Classes 
- Queue definition 

```Java
abstract class QueueBroker {
    QueueBroker(String name);
    MessageQueue accept(int port);
    MessageQueue connect(String name, int port);
}
abstract class MessageQueue {
    void send(byte[] bytes, int offset, int length);
    byte[] receive();
    void close();
    boolean closed();
}
abstract class Task extends Thread {
...
}
```
- Executor

```Java
abstract class Event {
    void react();
}
abstract class Executor extends Thread {
    List<Event> queue;
    Executor() {
        queue = new LinkedList<Event>();
    }
    void run() {
        Event e;
        e = queue.remove(0);
        while (e!=null) {
            e.react();
            e = queue.remove(0);
        }
    }
    void post(Event e) {
    queue.add(e);
    }
}
```

## 1. Specification

- L'envoie et la réception des messages sont des évènements 
- Une instance de la classe **Executor** sera utilisée, elle recevra des évènements dans une liste chainée.
- La liste des évènements doit être FIFO
- la méthode **post** permet d'ajouter un évènement à traiter dans la liste des évènements de l'objet **Executor** 
- La réception d'un évènement déclenche une réaction qui sera spécifiée dans la méthode **react()** de la classe Event. 
- La communication met en exergue deux ou un nombre supérieur d'instances de la classe **Task**
- la classe Task possède un attribut **QueueBroker** permettant d'autoriser les connexions et de se connecter 
- Les méthodes **accept** et **connect** de la classe **QueueBroker** sont bloquantes, elles renvoient des **MessageQueue**
- l'environment est multi-threadé, un seul accept() et plusieurs connect() : plusieurs RDVs 


## 2. Design

- Un RDV correspondant à une demande accept() et connect
- Le manager possède la liste des RDVs et une HasMap des Brokers avec leurs noms
- Le premier thread crée le RDV et attend le deuxième
- Les RDVs seront stockés dans une table de hash avec l'unicité : nom, port
- Les Brokers sont stockés dans une table de hash avec les noms correspondants


## 3. Implementation

