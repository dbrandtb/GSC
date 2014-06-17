package mx.com.gseguros.portal.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import mx.com.gseguros.utils.Utilerias;

import org.springframework.jdbc.core.RowMapper;

public class GenericMapper implements RowMapper 
{
	
	private String[] columnas;
	
    public GenericMapper(String[] columnas)
    {
    	this.columnas=columnas;
    }

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		Map<String,String> map=new HashMap<String,String>(0);
		for(String col:columnas)
		{
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