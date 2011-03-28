package ai;

import unitTesting.Api;
import unitTesting.UnitTestingAi;

public class Ai implements UnitTestingAi {
	@Override
	public void init() {
		//Do nothing
	}

	@Override
	public void performTest(int testNb) {
		try {
			System.out.println("AI: perform test #"+testNb);
			
			switch(testNb) {
			case 0:
				Api.testNoParameters();
				assert true;
				break;
				
			case 1:
				int result1 = Api.testNoParametersReturnsInt();
				assert (result1 == 42);
				break;
			}
		} catch(AssertionError e) {
			System.out.println("Test #"+testNb+" failed.");
		}
	}

	@Override
	public void stop() {
		//Do nothing
	}
	
}
