package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.MecanismoDeTooltipVO;
import mx.com.aon.portal.service.MecanismoDeTooltipManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

public class MecanismoDeTooltipManagerJdbcTemplateImpl extends
		AbstractManagerJdbcTemplateInvoke implements MecanismoDeTooltipManager {

	@SuppressWarnings("unchecked")
	public String agregarGuardarMecanismoDeTooltip(
			MecanismoDeTooltipVO mecanismoDeTooltipVO)
			throws ApplicationException {

		HashMap map = new HashMap();
		map.put("pv_idtitulo_i",mecanismoDeTooltipVO.getIdTitulo() );
		map.put("pv_idobjeto_i",mecanismoDeTooltipVO.getIdObjeto());
		map.put("pv_nbobjeto_i",mecanismoDeTooltipVO.getNbObjeto());
		map.put("pv_tipoobj_i",mecanismoDeTooltipVO.getFgTipoObjeto());
		map.put("pv_etiqueta_i",mecanismoDeTooltipVO.getNbEtiqueta());
		map.put("pv_tooltp_i",mecanismoDeTooltipVO.getDsTooltip());
		map.put("pv_gayuda_i",mecanismoDeTooltipVO.getFgAyuda());
		map.put("pv_dayuda_i",mecanismoDeTooltipVO.getDsAyuda());
		map.put("pv_lang_code_i",mecanismoDeTooltipVO.getLang_Code());
		
        WrapperResultados res =  returnBackBoneInvoke(map,"AGREGAR_GUARDAR_MECANISMO_TOOLTIP");
        return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public String borrarMecanismoDeTooltip(String idObjeto, String lang_Code)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_idobjeto_i",idObjeto);
		map.put("pv_lang_code_i",lang_Code);
		
        WrapperResultados res =  returnBackBoneInvoke(map,"BORRAR_MECANISMO_TOOLTIP");
        return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public PagedList buscarMecanismoDeTooltip(String nbObjeto,
			String lang_Code, String dsTitulo, int start, int limit)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_nbobjeto",nbObjeto);
		map.put("pv_lang_code",lang_Code);
		map.put("pv_dstitulo_i", dsTitulo);
		map.put("start",start);
		map.put("limit",limit);
		
        return pagedBackBoneInvoke(map, "BUSCAR_MECANISMO_TOOLTIP", start, limit);
	}

	@SuppressWarnings("unchecked")
	public String copiarMecanismoDeTooltipVO(
			MecanismoDeTooltipVO mecanismoDeTooltipVO)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_idobjeto_i",mecanismoDeTooltipVO.getIdObjeto());
		map.put("pv_lang_code_i",mecanismoDeTooltipVO.getLang_Code());

		/*
			map.put("idTitulo",mecanismoDeTooltipVO.getIdTitulo() );
			map.put("nbObjeto",mecanismoDeTooltipVO.getNbObjeto());
			map.put("fgTipoObjeto",mecanismoDeTooltipVO.getFgTipoObjeto());
			map.put("nbEtiqueta",mecanismoDeTooltipVO.getNbEtiqueta());
			map.put("dsTooltip",mecanismoDeTooltipVO.getDsTooltip());
			map.put("fgAyuda",mecanismoDeTooltipVO.getFgAyuda());
			map.put("dsAyuda",mecanismoDeTooltipVO.getDsAyuda());
			map.put("nbCajaIzquieda",mecanismoDeTooltipVO.getNbCajaIzquieda());
			map.put("nbCajaDerecha",mecanismoDeTooltipVO.getNbCajaDerecha());
		*/

            WrapperResultados res =  returnBackBoneInvoke(map,"COPIAR_MECANISMO_TOOLTIP");
            return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public MecanismoDeTooltipVO getMecanismoDeTooltipVO(String idObjedo,
			String lang_Code) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_idobjeto_i",idObjedo);
		map.put("pv_lang_code_i", lang_Code);
		
        return (MecanismoDeTooltipVO)getBackBoneInvoke(map,"OBTIENE_MECANISMO_TOOLTIP");
	}

	@SuppressWarnings("unchecked")
	public TableModelExport getModel(String nbObjeto, String lang_Code,
			String dsTitulo) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		
		List lista = null;
		HashMap map = new HashMap();
		map.put("nbObjeto",nbObjeto);
		map.put("lang_Code",lang_Code);
		map.put("dsTitulo", dsTitulo);
		
		lista = (ArrayList) super.getExporterAllBackBoneInvoke(map, "OBTIENE_MECANISMO_TOOLTIP_EXPORT");
		model.setInformation(lista);
		model.setColumnName(new String[]{"Etiqueta","Objeto","Pantalla"});
		//nbObjeto
		return model;
	}

	@SuppressWarnings("unchecked")
	public List getToolTipsForPage(String cdTitulo, String langCode)
			throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_lang_code",langCode);
        map.put("pv_dstitulo_i",cdTitulo );

        return getAllBackBoneInvoke(map,"BUSCAR_MECANISMO_TOOLTIP_PAGE");
	}

	public List getGB_Messages(String cdTitle, String langCode)
			throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
