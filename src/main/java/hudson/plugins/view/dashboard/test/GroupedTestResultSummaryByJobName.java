package hudson.plugins.view.dashboard.test;


public class GroupedTestResultSummaryByJobName extends GroupedTestResultSummary<String> {

	private JobNameCategorizationType categorization;

	public GroupedTestResultSummaryByJobName(JobNameCategorizationType categorization) {
		this.categorization = categorization;
	}

	protected String buildKey(TestResult testResult) {
		if (testResult.getJob() == null || testResult.getJob().getName() == null) {
			return "";
		}
		String key = testResult.getJob().getName().replaceAll(categorization.getGroupRegex(), categorization.getNamingRule());
		return key;
	}

}
