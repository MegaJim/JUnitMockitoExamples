package com.jemasters;

import com.jemasters.annotations.Injected;
import com.jemasters.annotations.RequestScope;
import com.jemasters.interfaces.DataGrid;
import com.jemasters.untestable.NastySuperClass;

@RequestScope
public class Example5Spies extends NastySuperClass {

	@Injected
	private DataGrid dataGrid;

	public void handleUpdateUserDetails() {
		if (validateFields() == false)
			return;

		dataGrid.store(getUserId() + ":firstName", getFirstName());
		dataGrid.store(getUserId() + ":lastName", getLastName());

		log("Updated user " + getUserId());
	}

	protected void log(String logMessage) {
		getLogQueue().add("Form Handler: " + logMessage);
	}

	public void setDataGrid(DataGrid dataGrid) {
		this.dataGrid = dataGrid;
	}

}
