# DM 1 : Designing complex systems 

Individually, in your own words, write down the specification for the following
communication framework, as seen in the lecture. 

In this case, the specification is what a developer needs to know in order to be
able to use this framework.

Also write a sample code that illustrate the basics: create a channel and send 
and receive something, like a string "Hello World" for example.

Once it is done, before the next lecture (21/09/2022),
do a git-add/git-commit, with the commit message "Channel Specification"

The given classes, written in Java:

```Java
  public class Broker {
    public Broker(String name) {}
    public Channel accept(int port) { return null; }
    public Channel connect(String name, int port) { return null; }
  }

  public class Channel {
    public int read(byte[] bytes, int offset, int length) { return 0; }
    public int write(byte[] bytes, int offset, int length) { return 0; }
    public void disconnect() {}
    public boolean disconnected() { return false; }
}
```

## Spécifications 

- Le channel est bidirectionnel, il permet de lire et d'écrire des octets 
- La méthode `read()` permet de lire les octects du buffer **bytes**. Cette méthode est bloquante quand il n'y a aucun octet à lire, elle devient non bloquante quand des octets à lire sont disponibles. 
- La méthode `read()` renvoie le nombre d'octets lus et renvoie -1 si la lecture a échoué
- La méthode `write()` écrit les octets dans le buffer et lève une exceptin en cas d'échec
- La méthode `write()` envoie les octets en flots, elle est FIFO et loseless
- Il faut lever l'exception chez un utilisateur qui essaye de lire les octets, si le second utilisateur auquel il est connecté interompt la connexion de façon inopinée 
- La méthode `accept()` autorise les connexions sur un port donné en générant un nouveau channel. Elle est bloquante en attente d'éventuelles connexions. 
- La méthode `connect()` permet de se connecter à un Broker dont le nom et le port sont connus en générant un nouveau channel. C'est une méthode bloquante



## Conception 

- La communication est réalisée en utilisant deux objets de la classe Task. Chaque instance Task possède un objet Broker. 
- Nous avons deux instances de la classe Broker, un par tâche. 
- Le premier broker autorise des connexions sur un port donné en utilisant la méthode accept(port) et attend jusqu'à la prochaine connexion
- Le deuxième Broker se connecte en utilisant le nom et le numéro de port ouvert du premier Broker 
- Un objet Channel est associé à chaque instance Broker, soit deux instances Channel au total. 
- Les deux instances de la classe Channel se partagent deux buffers correspondant à des instances de la classe CircularBuffer


## Implémentation



 
