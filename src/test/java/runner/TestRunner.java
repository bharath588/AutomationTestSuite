package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by 50021824 on 18-07-2017.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "features",glue = {"stepDefination"},  format = {"pretty", "html:Destination"}      )

public class TestRunner {
}
