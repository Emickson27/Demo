package src.test.java.TicketMinerSystem;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    PricingCalculatorTest.class,
    CustomerTest.class
})
public class AllTestsSuite {
    // Runs all selected test classes together
}
