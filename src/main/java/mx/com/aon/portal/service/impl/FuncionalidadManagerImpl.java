package mx.com.aon.portal.service.impl;

import mx.com.aon.portal.service.CotizacionManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.service.FuncionalidadManager;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.aon.portal.model.FuncionalidadVO;
import mx.com.aon.core.ApplicationException;
import mx.com.aon.export.model.TableModelExport;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FuncionalidadManagerImpl extends AbstractManagerJdbcTemplateInvoke implements FuncionalidadManager {

    private static Logger logger = Logger.getLogger(FuncionalidadManagerImpl.class);


    @SuppressWarnings("unchecked")
	public PagedList buscarFuncionalidades(String pv_nivel_i,String pv_sisrol_i,String pv_usuario_i,String pv_funciona_i,int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        //setear los parametros de entrada
        map.put("pv_nivel_i", pv_nivel_i);
        map.put("pv_sisrol_i", pv_sisrol_i);
        map.put("pv_usuario_i", pv_usuario_i);
        map.put("pv_funciona_i", pv_funciona_i);
        String endpointName = "BUSCAR_FUNCIONALIDADES";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }

    public String agregarFuncionalidad() throws ApplicationException {
        HashMap map = new HashMap();
        //setear los parametros de entrada
        //map.put("pv_dsestruc_i", descripcion);
        String endpointName = "AGREGAR_FUNCIONALIDAD";
        WrapperResultados res =  returnBackBoneInvoke(map,endpointName);
        return res.getMsgText();
    }

    @SuppressWarnings("unchecked")
	public PagedList getFuncionalidad(String pv_nivel_i, String pv_sisrol_i, String pv_usuario_i, String pv_funciona_i, int start,int limit) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_nivel_i", pv_nivel_i);
        map.put("pv_sisrol_i", pv_sisrol_i);
        map.put("pv_usuario_i", pv_usuario_i);
        map.put("pv_funciona_i", pv_funciona_i);
        
        String endpointName = "GET_FUNCIONALIDAD";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }

    @SuppressWarnings("unchecked")
	public String guardarFuncionalidades(List<FuncionalidadVO> funcionalidades) throws ApplicationException {
        String resultado = "";
        for (int i = 0; i < funcionalidades.size(); i++) {
            FuncionalidadVO funcionalidadVO =  funcionalidades.get(i);
            HashMap map = new HashMap();
            String cdSisrol = funcionalidadVO.getCdSisRol();
            String cdUsuario = funcionalidadVO.getCdUsuario();
            String cdFunciona = funcionalidadVO.getCdFunciona();
            String cdOpera = funcionalidadVO.getCdOpera();
            String swEstado = funcionalidadVO.getSwEstado();
            String cdElemento = funcionalidadVO.getCdElemento();
            
            if(cdSisrol == null) cdSisrol= "";
            if(cdUsuario == null) cdUsuario= "";
            if(cdFunciona == null) cdFunciona= "";
            if(cdOpera == null) cdOpera= "";
            if(swEstado == null) swEstado= "";
            if(cdElemento == null) cdElemento= "";
            
            map.put("pv_cdsisrol_i", cdSisrol);
            map.put("pv_cdusuario_i", cdUsuario);
            map.put("pv_cdfunciona_i", cdFunciona);
            map.put("pv_cdopera_i", cdOpera);
            map.put("pv_swestado_i", swEstado);
            map.put("pv_cdelemento_i", cdElemento);
            
            logger.debug("HHHH parametros para guardarFuncionalidades: "+map);
            String endpointName = "GUARDAR_FUNCIONALIDAD";
            WrapperResultados res =  returnBackBoneInvoke(map,endpointName);
            resultado = res.getMsgText();
        }
        return resultado;
    }

    public PagedList buscarConfiguracionesFuncionalidad(int start, int limit) throws ApplicationException {
        HashMap map = new HashMap();
        //setear los parametros de entrada
        //map.put("pv_dsestruc_i", descripcion);
        String endpointName = "BUSCAR_CONFIGURACIONES_FUNCIONALIDAD";
        return pagedBackBoneInvoke(map, endpointName, start, limit);
    }
    
    @SuppressWarnings("unchecked")
	public String borrarFuncionalidades (String pv_cdelemento_i,String pv_cdsisrol_i, String pv_cdusuario_i, String pv_cdfunciona_i, String pv_cdopera_i) throws ApplicationException{
    	HashMap map = new HashMap();
		map.put("pv_cdelemento_i", pv_cdelemento_i);
		map.put("pv_cdsisrol_i", pv_cdsisrol_i);
		map.put("pv_cdusuario_i", pv_cdusuario_i);
		map.put("pv_cdfunciona_i", pv_cdfunciona_i);
		map.put("pv_cdopera_i", pv_cdopera_i);
		WrapperResultados res = returnBackBoneInvoke(map, "BORRAR_FUNCIONALIDAD");
		return res.getMsgText();
    	
    }

	@SuppressWarnings("unchecked")
	public TableModelExport getModelFuncionalidades(String pv_nivel_i,String pv_sisrol_i, String pv_usuario_i, String pv_funciona_i)throws ApplicationException {
		TableModelExport model = new TableModelExport();

		List lista = null;
		HashMap map = new HashMap();

		map.put("pv_nivel_i", pv_nivel_i);
	    map.put("pv_sisrol_i", pv_sisrol_i);
	    map.put("pv_usuario_i", pv_usuario_i);
	    map.put("pv_funciona_i", pv_funciona_i);
        
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_FUNCIONALIDADES_EXPORT");
        model.setInformation(lista);
		model.setColumnName(new String[]{"NIVEL","ROL","USUARIO","FUNCIONALIDAD","OPERACION","ESTADO"});

		return model;
	}
	
	 public List  comboFuncionalidades()throws ApplicationException {
		 HashMap map = new HashMap();
		 String endpointName = "OBTIENE_P_FUNCIONALIDADES";
	     return  getAllBackBoneInvoke( map,endpointName );
		// return pagedBackBoneInvoke(map, endpointName, start, limit);
	 }     
}
