package thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

import tests.Game;

public class MTGame extends Thread implements Game {
	private boolean started;
	private PipedReader callPipeIn;
	private PipedWriter returnPipeOut;

	public MTGame(PipedReader callPipeIn, PipedWriter returnPipeOut) {
		super("Game Thread");
		this.callPipeIn = callPipeIn;
		this.returnPipeOut = returnPipeOut;
		
		start();
	}
	
	@Override
	public void run() {
		started = true;
		
		while(started) {
			try {
				System.out.println("Game:Wait");
				//sleep(1);
				int parameter = callPipeIn.read();
				System.out.println("Game:Read "+parameter);
				returnPipeOut.write(callAPI(parameter));
				System.out.println("Game:Write");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int callAPI(int parameter) {
		return parameter * 2;
	}

	@Override
	public void delete() {
		started = false;
	}
}
