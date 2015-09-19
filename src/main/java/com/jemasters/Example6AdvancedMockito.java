package com.jemasters;

import java.util.List;
import java.util.Map;

import com.jemasters.annotations.Injected;
import com.jemasters.interfaces.DataGrid;
import com.jemasters.model.Customer;
import com.jemasters.untestable.CustomerContainer;
import com.jemasters.untestable.CustomerDeleter;

public class Example6AdvancedMockito {

	@Injected
	private DataGrid dataGrid;
	@Injected
	private DataGrid backupDataGrid;
	@Injected
	private DataGrid reportingDataGrid;
	@Injected
	private DataGrid productDataGrid;
	@Injected
	private CustomerDeleter customerDeleter;

	public void updateBasketSizeToDataGrid(Map<String, Integer> basketProductToQuantity) {
		for (Integer quantity : basketProductToQuantity.values()) {
			while (quantity > 0) {
				dataGrid.increment("basketQuantity");
				quantity--;
			}
		}
	}

	public void transactionalUpdateUser(Customer customer) {
		if (dataGrid.prepare(customer)) {
			if (dataGrid.update(customer)) {
				dataGrid.commit(customer);
			}
		}
	}

	public void storeCustomerProperties(Map<String, Object> customerProps) {
		Customer customer = new Customer();
		customer.id = (String) customerProps.get("id");
		customer.firstName = (String) customerProps.get("firstName");
		customer.lastName = (String) customerProps.get("lastName");

		dataGrid.store("customer:cust123", customer);
	}

	public void deleteUsers(List<String> customers, CustomerContainer customerContainer) {
		for (Customer customer : customerContainer.getCustomers()) {
			customerDeleter.deleteCustomer(customer, customerContainer);
			dataGrid.remove(customer.id);
		}
	}

	public boolean healthCheckGrids() {
		return dataGrid.ping() && backupDataGrid.ping() && reportingDataGrid.ping() && productDataGrid.ping();
	}

	public void setDataGrid(DataGrid dataGrid) {
		this.dataGrid = dataGrid;
	}

	public void setBackupDataGrid(DataGrid backupDataGrid) {
		this.backupDataGrid = backupDataGrid;
	}

	public void setReportingDataGrid(DataGrid reportingDataGrid) {
		this.reportingDataGrid = reportingDataGrid;
	}

	public void setProductDataGrid(DataGrid productDataGrid) {
		this.productDataGrid = productDataGrid;
	}

}
