package thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

import tests.Model;	

public class MultiThreadModel extends Model {
	public MultiThreadModel() {
		PipedWriter callPipeOut = new PipedWriter();
		PipedWriter returnPipeOut = new PipedWriter();
		PipedReader callPipeIn = null;
		PipedReader returnPipeIn = null;
		
		try {
			callPipeIn = new PipedReader(callPipeOut);
			returnPipeIn = new PipedReader(returnPipeOut);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		ai = new MTAI(callPipeOut, returnPipeIn);
		game = new MTGame(callPipeIn, returnPipeOut);
	}

	@Override
	public void execute() {
		ai.execute();
	}

	@Override
	public void stop() {
		ai.delete();
	}

}
