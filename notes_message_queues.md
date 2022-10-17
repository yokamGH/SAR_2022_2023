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

## 1. Specifications
- Java, POO 

## 2. Design 
- Créer un RDV : accept() et connect()
- Un Manager pour gérer tous les RDVs, c'est quoi un Manager ? 
- Le premier thread crée le RDV et le deuxième thread le trouve 
- Créer une liste ou une table de hash de RDV avec l'unicité : nom, port 
- **Ajouter un RDV accept(nom, port)** dans la liste, sans problème
- **Ajouter un RDV connecct(nom, port)** le thread attend s'il existe déjà un RDV connect() avec le même nom et le même port. Le thread ajoute le RDV s'il est le premier thread à demander le connecct() avec le même nom et le même port . 
- Un seul accept() et plusieurs connect() : plusieurs RDVs 
- l'environment est multi-threadé 
- La liste des RDVs est chez le manager 
- Une tâche correspond un à thread 
- Le manager a une liste de RDVs 
- Le manager a une HashMap de Brokers associés avec leurs noms 