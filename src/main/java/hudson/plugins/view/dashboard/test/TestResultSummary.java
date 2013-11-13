package hudson.plugins.view.dashboard.test;

import hudson.model.BallColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestResultSummary extends TestResult {

	private List<TestResult> testResults = new ArrayList<TestResult>();

	public TestResultSummary() {
		super(null, 0, 0, 0);
	}

	public TestResultSummary addTestResult(TestResult testResult) {
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

	public List<TestResult> getTestResults(BallColor ballColor) {
		if (ballColor == null) {
			return Collections.<TestResult>emptyList();
		}
		List<TestResult> filteredTestResults = new ArrayList<TestResult>();
		for (TestResult testResult : testResults) {
			if (testResult.getJob() != null && ballColor.equals(testResult.getJob().getIconColor())) {
				filteredTestResults.add(testResult);
			}
		}
		return filteredTestResults;
	}
	
	public TestResultSubTotal getSubTotal(BallColor ballColor) {
		if (ballColor == null) {
			return null;
		}
		TestResultSubTotal testResultSubTotal = new TestResultSubTotal(ballColor);
		for (TestResult testResult : testResults) {
			if (testResult.getJob() != null && ballColor.equals(testResult.getJob().getIconColor())) {
				testResultSubTotal.add(testResult);
			}
		}
		return testResultSubTotal;
	}

	public Set<BallColor> getJobBallColors() {
		Set<BallColor> ballColors = new HashSet<BallColor>();
		for (TestResult testResult : testResults) {
			ballColors.add(testResult.getJob().getIconColor());
		}
		return ballColors;
	}

}
