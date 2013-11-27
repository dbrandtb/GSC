package mx.com.gseguros.portal.endosos.dao;

import java.util.List;
import java.util.Map;

public interface EndososDAO
{
    public List<Map<String,String>> obtenerEndosos(Map<String,String>params) throws Exception;
}