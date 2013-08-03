package mx.com.aon.portal.service;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.FuncionalidadVO;

import java.util.List;

public interface FuncionalidadManager {

    public PagedList buscarFuncionalidades (String pv_nivel_i,String pv_sisrol_i,String pv_usuario_i,String pv_funciona_i, int start, int limit) throws ApplicationException;

    public String agregarFuncionalidad () throws ApplicationException;

    public PagedList getFuncionalidad (String pv_nivel_i, String pv_sisrol_i, String pv_usuario_i, String pv_funciona_i,int start,int limit) throws ApplicationException;

    public String guardarFuncionalidades (List<FuncionalidadVO> funcionalidades) throws ApplicationException;

    public PagedList buscarConfiguracionesFuncionalidad (int start, int limit) throws ApplicationException;
    
    public String borrarFuncionalidades (String pv_cdelemento_i,String pv_cdsisrol_i, String pv_cdusuario_i, String pv_cdfunciona_i, String pv_cdopera_i) throws ApplicationException;
    
    public TableModelExport getModelFuncionalidades(String pv_nivel_i,String pv_sisrol_i,String pv_usuario_i,String pv_funciona_i) throws ApplicationException;

	public List comboFuncionalidades()throws ApplicationException;

}
