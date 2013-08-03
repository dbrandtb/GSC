package mx.com.aon.catbo.service.impl;

import java.util.HashMap;

import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.service.AdministradorGuardarCasoManager;
import mx.com.aon.portal.service.impl.AbstractManager;
import mx.com.aon.portal.util.WrapperResultados;

public class AdministradorGuardarCasoManagerImpl extends AbstractManager implements AdministradorGuardarCasoManager{
	
	@SuppressWarnings("unchecked")
	public String guardarNuevoCaso(CasoVO casoVO,FormatoOrdenVO formatoOrdenVO)
			throws Exception {
		
		WrapperResultados res = new WrapperResultados();
		HashMap map = new HashMap();
		for(int i=0; i<formatoOrdenVO.getListaEmisionVO().size();i++){
			
			map.put("pv_cdordentrabajo_i", formatoOrdenVO.getListaEmisionVO().get(i).getCdordentrabajo());
			map.put("pv_cdformatoorden_i", formatoOrdenVO.getListaEmisionVO().get(i).getCdformatoorden());
			map.put("pv_cdseccion_i", formatoOrdenVO.getListaEmisionVO().get(i).getCdSeccion());
			map.put("pv_cdatribu_i", formatoOrdenVO.getListaEmisionVO().get(i).getCdAtribu());
			map.put("pv_otvalor_i", formatoOrdenVO.getListaEmisionVO().get(i).getOtValor());
			
			//Aca va el llamado al proceso bpel para el casoVO
			
			res =  returnBackBoneInvoke(map,"GUARDAR_FORMATO_ORDEN");
		}		
		return res.getMsgText();
	}

}
