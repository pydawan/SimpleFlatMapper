package org.flatmap.jdbc.getter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.flatmap.reflect.Getter;
import org.flatmap.reflect.primitive.BooleanGetter;

public class BooleanNameResultSetGetter implements BooleanGetter<ResultSet>, Getter<ResultSet, Boolean> {

	private final String column;
	
	public BooleanNameResultSetGetter(String column) {
		this.column = column;
	}

	@Override
	public boolean getBoolean(ResultSet target) throws SQLException {
		return target.getBoolean(column);
	}

	@Override
	public Boolean get(ResultSet target) throws Exception {
		return getBoolean(target);
	}
}
