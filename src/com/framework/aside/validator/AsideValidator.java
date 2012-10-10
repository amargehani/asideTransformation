package com.framework.aside.validator;

import java.util.Date;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;

public class AsideValidator {

	public IObservableValue intValue = new WritableValue(null, Integer.class);
	public IObservableValue dateValue = new WritableValue(null, Date.class);
}
