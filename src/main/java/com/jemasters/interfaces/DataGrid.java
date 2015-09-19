package com.jemasters.interfaces;

public interface DataGrid {

	public void store(String key, Object value);

	public void increment(String key);

	public boolean prepare(Object customer);

	public boolean update(Object customer);

	public void commit(Object customer);

	public Object retrieve(String key);

	public boolean ping();

	public void remove(String id);
}
