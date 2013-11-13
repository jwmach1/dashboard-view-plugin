package hudson.plugins.view.dashboard.test;

import java.util.ArrayList;
import java.util.List;

import hudson.model.BallColor;

public class TestResultSubTotal extends TestResult {

	private final BallColor ballColor;
	private final List<TestResult> testResults;


	public TestResultSubTotal(BallColor ballColor) {
		super(null, 0, 0, 0);
		this.ballColor = ballColor;
		this.testResults = new ArrayList<TestResult>();
	}
	
	public TestResultSubTotal add(TestResult testResult) {
		testResults.add(testResult);

		tests += testResult.getTests();
		success += testResult.getSuccess();
		failed += testResult.getFailed();
		skipped += testResult.getSkipped();

		return this;
	}
	
	public List<TestResult> getTestResults() {
		return testResults;
	}
	
	public BallColor getBallColor() {
		return ballColor;
	}

}
