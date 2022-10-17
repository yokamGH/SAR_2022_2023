package broker_channel.tasks;

public class Task extends Thread {

	protected String name;

	public Task (String name) {
		this.name = name;
	}
}
