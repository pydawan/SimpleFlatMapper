package org.sfm.jdbc;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.sfm.beans.DbObject;
import org.sfm.beans.DbFinalObject;
import org.sfm.reflect.ReflectionService;
import org.sfm.utils.RowHandler;
import org.sfm.utils.ListHandler;

public class JdbcMapperDbObjectTest {
	
	@Test
	public void testColumn() throws Exception {
		JdbcMapperBuilder<DbObject> builder = new JdbcMapperBuilder<DbObject>(DbObject.class);
		
		addColumn(builder);
		
		final JdbcMapper<DbObject> mapper = builder.mapper();
		
		testDbObjectMapper(mapper);
	}

	public static <T> JdbcMapperBuilder<T> addColumn(JdbcMapperBuilder<T> builder) {
		builder.addMapping("id");
		builder.addMapping("name");
		builder.addMapping("email");
		builder.addMapping("creation_time");
		builder.addMapping("type_ordinal");
		builder.addMapping("type_name");
		return builder;
	}
	
	@Test
	public void testColumnFinalProperty() throws Exception {
		JdbcMapperBuilder<DbFinalObject> builder = new JdbcMapperBuilder<DbFinalObject>(DbFinalObject.class);
		
		addColumn(builder);
		
		final JdbcMapper<DbFinalObject> mapper = builder.mapper();
		
		DbHelper.testDbObjectFromDb(new RowHandler<PreparedStatement>() {
			@Override
			public void handle(PreparedStatement ps) throws Exception {
				List<DbFinalObject> objects = mapper.forEach(ps.executeQuery(), new ListHandler<DbFinalObject>()).getList();
				assertEquals(1, objects.size());
				DbHelper.assertDbObjectMapping(objects.get(0));
			}
		});
	}
	private void testDbObjectMapper(final JdbcMapper<DbObject> mapper)
			throws SQLException, Exception, ParseException {
		DbHelper.testDbObjectFromDb(new RowHandler<PreparedStatement>() {
			@Override
			public void handle(PreparedStatement ps) throws Exception {
				List<DbObject> objects = mapper.forEach(ps.executeQuery(), new ListHandler<DbObject>()).getList();
				assertEquals(1, objects.size());
				DbHelper.assertDbObjectMapping(objects.get(0));
			}
		});
	}
	
	@Test
	public void testDbObjectMapperWithIterator()
			throws SQLException, Exception, ParseException {
		JdbcMapperBuilder<DbObject> builder = new JdbcMapperBuilder<DbObject>(DbObject.class);

		testMapperBuilderWithIterator(builder);
	}
	@Test
	public void testDbObjectMapperWithIteratorNoAsm()
			throws SQLException, Exception, ParseException {
		JdbcMapperBuilder<DbObject> builder = new JdbcMapperBuilder<DbObject>(DbObject.class, new ReflectionService(false, false));

		testMapperBuilderWithIterator(builder);
	}
	private void testMapperBuilderWithIterator(JdbcMapperBuilder<DbObject> builder) throws Exception {
		addColumn(builder);

		final JdbcMapper<DbObject> mapper = builder.mapper();

		DbHelper.testDbObjectFromDb(new RowHandler<PreparedStatement>() {
			@Override
			public void handle(PreparedStatement ps) throws Exception {
				Iterator<DbObject> objects = mapper.iterate(ps.executeQuery());
				assertTrue(objects.hasNext());
				DbHelper.assertDbObjectMapping(objects.next());
				assertFalse(objects.hasNext());
			}
		});
	}

	//IFJAVA8_START
	@Test
	public void testDbObjectMapperWithStream()
			throws SQLException, Exception, ParseException {
		
		JdbcMapperBuilder<DbObject> builder = new JdbcMapperBuilder<DbObject>(DbObject.class);
		testMapperBuilderWithStream(builder);
	}

	@Test
	public void testDbObjectMapperWithStreamNoAsm()
			throws SQLException, Exception, ParseException {
		JdbcMapperBuilder<DbObject> builder = new JdbcMapperBuilder<DbObject>(DbObject.class, new ReflectionService(false, false));
		testMapperBuilderWithStream(builder);
	}
	private void testMapperBuilderWithStream(JdbcMapperBuilder<DbObject> builder) throws Exception {
		addColumn(builder);
		final JdbcMapper<DbObject> mapper = builder.mapper();

		DbHelper.testDbObjectFromDb(new RowHandler<PreparedStatement>() {
			@Override
			public void handle(PreparedStatement ps) throws Exception {
				mapper.stream(ps.executeQuery()).forEach(
						(o) -> {
							try {
								DbHelper.assertDbObjectMapping(o);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						}
				);
			}
		});
	}
	//IFJAVA8_END

}