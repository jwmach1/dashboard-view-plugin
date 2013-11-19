package hudson.plugins.view.dashboard.test;

import java.util.ArrayList;
import java.util.List;

public class TestResultSubtotal<K> extends TestResult {

	private final K key;
	private final List<TestResult> testResults;


	public TestResultSubtotal(K key) {
		super(null, 0, 0, 0);
		this.key = key;
		this.testResults = new ArrayList<TestResult>();
	}
	
	public TestResultSubtotal<K> add(TestResult testResult) {
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
	
	public K getKey() {
		return key;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj instanceof TestResultSubtotal) {
			TestResultSubtotal<?> other = (TestResultSubtotal<?>) obj;
			return this.key.equals(other.key) && this.testResults.equals(other.testResults);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return key.hashCode() & testResults.hashCode();
	}
}
