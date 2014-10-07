package mx.com.gseguros.portal.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.utils.Utilerias;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

public class GenericMapper implements RowMapper 
{
	
	private String[] columnas;
	private List<String> columnas2;
	private final static Logger logger = Logger.getLogger(GenericMapper.class);
	
    public GenericMapper(String[] columnas)
    {
    	this.columnas=columnas;
    }
    
    public GenericMapper(List<String> columnas2)
    {
    	this.columnas2=columnas2;
    }

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		Map<String,String> map=new HashMap<String,String>(0);
		if(columnas!=null)
		{
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
		}
		else if(columnas2!=null)
		{
			for(String col:columnas2)
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
		}
		else
		{
			logger.error("No hay columnas para el mapper");
		}
		return map;
	}
    
}