package hudson.plugins.view.dashboard.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Template pattern
 *
 * @param <K> the type of the key
 */
public abstract class GroupedTestResultSummary<K> extends TestResultSummary {

	public Map<K, TestResultSubtotal<K>> getGrouping() {
		Map<K, TestResultSubtotal<K>> result = new HashMap<K, TestResultSubtotal<K>>();
		for (TestResult testResult : testResults) {
			if (testResult.getJob() != null) {
				K key = buildKey(testResult);
				TestResultSubtotal<K> subtotal = result.get(key);
				if (subtotal == null) {
					subtotal = new TestResultSubtotal<K>(key);
					result.put(key, subtotal);
				}
				subtotal.add(testResult);
			}
		}
		return result;
	}
	
	protected abstract K buildKey(TestResult testResult);
}
