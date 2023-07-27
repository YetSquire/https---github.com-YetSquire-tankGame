package src;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Game game = new Game();
		Thread t = new Thread(game);
		while (true)
		{
			if (Game.exit)
			{
				t = new Thread(game);
			}
			if (!Game.exit && !t.isAlive()) t.start();
		}
	}
	

}
