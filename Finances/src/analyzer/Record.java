package analyzer;

import java.util.Date;

public class Record {
	final Date date;
	final String name;
	final Float value;
	
	public Record(Date date, String name, Float value) {
		this.date = date;
		this.name = name;
		this.value = value;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getName() {
		return name;
	}
	
	public Float getValue() {
		return value;
	}
}
