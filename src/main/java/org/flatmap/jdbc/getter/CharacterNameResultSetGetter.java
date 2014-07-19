package org.flatmap.jdbc.getter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.flatmap.reflect.Getter;
import org.flatmap.reflect.primitive.CharacterGetter;

public class CharacterNameResultSetGetter implements CharacterGetter<ResultSet>, Getter<ResultSet, Character> {

	private final String column;
	
	public CharacterNameResultSetGetter(String column) {
		this.column = column;
	}

	@Override
	public char getCharacter(ResultSet target) throws SQLException {
		return (char)target.getInt(column);
	}
	
	@Override
	public Character get(ResultSet target) throws SQLException {
		return getCharacter(target);
	}
}
