package hudson.plugins.view.dashboard.test;

import hudson.plugins.view.dashboard.test.CategorizationType.TestStatisticCategorization;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

public class CategorizationTypeBuilder {

	public CategorizationType build(StaplerRequest request, JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}
		TestStatisticCategorization type = TestStatisticCategorization.valueOf(jsonObject.getString("value"));
        return request.bindJSON(type.getTypeClass(), jsonObject);
	}

}
