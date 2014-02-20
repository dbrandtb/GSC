package mx.com.gseguros.portal.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import mx.com.gseguros.utils.Utilerias;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

public class DinamicMapper implements RowMapper
{
	
	private static Logger log=Logger.getLogger(DinamicMapper.class);

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		Map<String,String> map=new LinkedHashMap<String,String>(0);
		ResultSetMetaData metaData = rs.getMetaData();
		int numCols=metaData.getColumnCount();
		for (int i=1;i<=numCols;i++)
		{
			String col=metaData.getColumnName(i);
			//log.debug("columna: "+col);
			if(col!=null&&col.substring(0,2).equalsIgnoreCase("fe"))
			{
				map.put(col,Utilerias.formateaFecha(rs.getString(col)));
			}
			else
			{
				map.put(col,rs.getString(col));
			}			
		}
		return map;
	}
	
}