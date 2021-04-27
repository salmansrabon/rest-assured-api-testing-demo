import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;

import java.io.IOException;

public class RunTest {
    @Test()
    public void login() throws IOException, ConfigurationException {
        Customer customer=new Customer();
        customer.callingLoginAPI();
    }
    @Test()
    public void customerList() throws IOException {
        Customer customer=new Customer();
        customer.customerList();
    }
}
