package mx.com.gseguros.portal.endosos.service;

import java.util.List;
import java.util.Map;

public interface EndososManager
{
    public List<Map<String,String>> obtenerEndosos(Map<String,String>params) throws Exception;
}