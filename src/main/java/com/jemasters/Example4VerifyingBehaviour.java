package com.jemasters;

import java.util.Date;

import com.jemasters.annotations.Injected;
import com.jemasters.interfaces.DataGrid;

public class Example4VerifyingBehaviour {

	@Injected
	private Example3IntroductionToMockito ageCalculator;

	@Injected
	private DataGrid dataGrid;

	public void calculateAgeAndStore(String name, Date birthday) {
		try {
			Long age = ageCalculator.calculateMyAge(birthday);
			dataGrid.store(name + ":birthday", age);
		} catch (Exception ex) {
//			dataGrid.store(name + ":birthday", 0L);
		}

	}

	public void setAgeCalculator(Example3IntroductionToMockito ageCalculator) {
		this.ageCalculator = ageCalculator;
	}

	public void setDataGrid(DataGrid dataGrid) {
		this.dataGrid = dataGrid;
	}

}
