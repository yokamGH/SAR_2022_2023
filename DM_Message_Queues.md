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

- L'envoie d'un message est un évènement 
- La réception d'un message est un évènement
- Une instance de la classe **Executor** sera utilisée, elle recevra des évènements dans une liste chainée.
- La liste des évènements doit être FIFO
- la méthode **post** permet d'ajouter un évènement à traiter dans la liste des évènements de l'objet **Executor** 
- La réception d'un évènement déclenche une réaction qui sera spécifiée dans la méthode **react()** de la classe Event. 
- La communication met en exergue deux ou un nombre supérieur d'instances d'une classe héritée de **Task**
- la classe Task possède un attribut **QueueBroker** permettant d'autoriser les connexions et de se connecter à d'autres **QueueBroker**
- Les méthodes **accept** et **connect** de la classe **QueueBroker** sont bloquantes, elles renvoient des **MessageQueue**
- Un **MessageQueue** permet l'envoie et la réception de messages


## 2. Design

## 3. Implementation

