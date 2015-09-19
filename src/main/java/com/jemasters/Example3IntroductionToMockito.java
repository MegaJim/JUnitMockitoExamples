package com.jemasters;

import java.util.Date;

import com.jemasters.annotations.Injected;
import com.jemasters.interfaces.DateProvider;

public class Example3IntroductionToMockito {

	@Injected
	private DateProvider dateProvider;

	public Long calculateMyAge(Date birthday) {
		return (dateProvider.todaysDate().getTime() - birthday.getTime()) / (1000L * 60 * 60 * 24 * 365);
	}

	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}
}
