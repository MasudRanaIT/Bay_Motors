package baymotorscom.baymotors;

import org.junit.jupiter.api.Test;

import com.baymotors.Customer;
import com.baymotors.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

class CustomerAndVehicleTest {

    @Test
    void testAddVehicleToCustomer() {
        Customer customer = new Customer(1, "John Doe", "john@example.com", "1234567890", false);
        Vehicle vehicle = new Vehicle(1, "AB123CD", "Toyota", "Camry", customer);
        customer.addVehicle(vehicle);

        assertEquals(1, customer.getVehicles().size());
        assertEquals("Toyota", customer.getVehicles().get(0).getMake());
    }
}