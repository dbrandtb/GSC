package mx.com.gseguros.portal.endosos.dao;

import java.util.List;
import java.util.Map;

public interface EndososDAO
{
    public List<Map<String,String>> obtenerEndosos(Map<String,String>params) throws Exception;
    public Map<String, String>      guardarEndosoNombres(Map<String, Object> params) throws Exception;
    public Map<String, String>      confirmarEndosoB(Map<String, String> params) throws Exception;
    public Map<String, String>      guardarEndosoDomicilio(Map<String, Object> params) throws Exception;
}