package hudson.plugins.view.dashboard.test;

import java.util.ArrayList;
import java.util.List;

public class TestResultSummary extends TestResult {

	protected List<TestResult> testResults = new ArrayList<TestResult>();

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

}
