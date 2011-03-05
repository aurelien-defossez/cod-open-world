package tests;

import java.util.Date;

import thread.MultiThreadModel;

import direct.DirectCallModel;

public class PerformanceTests {
	public static void main(String[] args) {
		new PerformanceTests();
	}
	
	public PerformanceTests() {
		Model model = new MultiThreadModel();
		int nbTests = 10;
		
		Date before = new Date();
		
		for(int i = 0; i < nbTests; i++) {
			model.execute();
		}

		Date after = new Date();
		
		model.stop();
		
		System.out.println("Duration: "+(after.getTime()-before.getTime())+"ms.");
	}
}
