package thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

import tests.AI;

public class MTAI extends Thread implements AI {
	private int nb;
	private boolean started;
	private PipedWriter callPipeOut;
	private PipedReader returnPipeIn;
	
	public MTAI(PipedWriter callPipeOut, PipedReader returnPipeIn) {
		super("AI Thread");
		this.nb = 1;
		this.callPipeOut = callPipeOut;
		this.returnPipeIn = returnPipeIn;
		
		start();
	}
	
	@Override
	public void execute() {
		System.out.println("AI:Execute");
		synchronized(this) {
			this.notify();
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		started = true;
		
		while(started) {
			synchronized(this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
				try {
					System.out.println("AI:write "+nb);
					callPipeOut.write(nb);
					System.out.println("AI:wait");
					//sleep(1);
					nb = returnPipeIn.read();
					System.out.println("AI:read "+nb);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				this.notify();
			}
		}
	}

	@Override
	public void delete() {
		started = false;
	}
}
