package hudson.plugins.view.dashboard.test;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

public class JobNameCategorizationType extends CategorizationType {

	private final String namingRule;
	private final String groupRegex;

	@DataBoundConstructor
	public JobNameCategorizationType(String value, String groupRegex, String namingRule) {
		super(value);
		if (StringUtils.isBlank(groupRegex)) {
			this.groupRegex = "^.*-(.*)$";
		} else {
			this.groupRegex = groupRegex;
		}
		if (StringUtils.isBlank(namingRule)) {
			this.namingRule = "$1";
		} else {
			this.namingRule = namingRule;
		}
	}

	public String getGroupRegex() {
		return groupRegex;
	}

	public String getNamingRule() {
		return namingRule;
	}

}
