package broker_channel.channels;

import java.io.IOException;

/**
 * Represente un canal de communication FIFO lossless entre 2 taches, cree par
 * un Broker
 **/
public abstract class Channel {

	/**
	 * Lecture bloquante dans le canal
	 * 
	 * @param byte[] bytes - tableau contenant les octets lus
	 * @param int    offset - offset indicant le décalage par lequel les octets vont
	 *               être lus
	 * @param int    length - taille maximum d'octets lus
	 * @throws IOException - Lève une exception lorsqu'il y a une erreur dans la
	 *                     communication
	 * @return int - nombre d'octets effectivement lus
	 **/
	public int read(byte[] bytes, int offset, int length) throws IOException {
		return 0;
	}

	/**
	 * écriture bloquante dans le canal
	 * 
	 * @param byte[] bytes - tableau contenant les octets à ecrire
	 * @param int    offset - offset indicant le decalage par lequel les octets vont
	 *               être écrits
	 * @param int    length - taille maximum d'octets à ecrire
	 * @throws IOException - Lève une exception lorsqu'il y a une erreur dans la
	 *                     communication
	 * @return int - nombre d'octets effectivement ecrits
	 **/
	public int write(byte[] bytes, int offset, int length) throws IOException {
		return 0;
	}

	/**
	 * deconnecte le canal de communication
	 * 
	 * @return void
	 **/
	public void disconnect() {
	}

	/**
	 * rretourne l'etat du canal
	 * 
	 * @return boolean - true si il est actif, false sinon
	 **/
	public boolean disconnected() {
		return false;
	}
}
