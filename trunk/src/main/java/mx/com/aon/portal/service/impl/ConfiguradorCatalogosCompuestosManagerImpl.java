package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.export.model.TableModelExport;
import mx.com.aon.portal.model.CampoCatalogoVO;
import mx.com.aon.portal.model.ExtJSFieldVO;
import mx.com.aon.portal.model.ValoresCamposCatalogosCompuestosVO;
import mx.com.aon.portal.model.ValoresCamposCatalogosSimplesVO;
import mx.com.aon.portal.service.ConfiguradorCatalogosCompuestosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;

public class ConfiguradorCatalogosCompuestosManagerImpl extends AbstractManager implements
		ConfiguradorCatalogosCompuestosManager {

	@SuppressWarnings("unchecked")
	public String borrarRegistro (String cdPantalla, String valorLlave1, String valorLlave2, String valorLlave3, String valorLlave4) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdPantalla", cdPantalla);
		map.put("valorLlave1", valorLlave1);
		map.put("valorLlave2", valorLlave2);
		map.put("valorLlave3", valorLlave3);
		map.put("valorLlave4", valorLlave4);

		WrapperResultados res = returnBackBoneInvoke(map, "CONFIGURAR_CATALOGOS_COMPUESTOS_BORRAR");

		return res.getMsgText();
	}

	@SuppressWarnings("unchecked")
	public PagedList obtenerCamposBusqueda (String cdPantalla)
			throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("cdPantalla", cdPantalla);

		return pagedBackBoneInvoke(map, "CONFIGURAR_CATALOGOS_COMPUESTOS_OBTENER_CAMPOS", 0, 15);
	}

	@SuppressWarnings("unchecked")
	public PagedList obtenerValoresBusqueda(String cdPantalla, String Clave, String Descripcion,
			String Valor1, String Valor2, String Valor3, int start, int limit)
			throws ApplicationException {

		HashMap map = new HashMap ();
		map.put("cdPantalla", cdPantalla);
		map.put("Clave", Clave);
		map.put("Descripcion", Descripcion);
		map.put("Valor1", Valor1);
		map.put("Valor2", Valor2);
		map.put("Valor3", Valor3);

		//return pagedBackBoneInvoke(map, "CONFIGURAR_CATALOGOS_SIMPLES_OBTENER_REGISTROS", start, limit);
		return pagedBackBoneInvoke(map, "CONFIGURAR_CATALOGOS_COMPUESTOS_OBTENER_COLUMNAS", start, limit);
	}

	public String guardarRegistro(String cdPantalla, List<ValoresCamposCatalogosCompuestosVO>listaValores) throws ApplicationException {
		WrapperResultados res = new WrapperResultados();
		for (int i=0; i<listaValores.size(); i++) {
			HashMap map = new HashMap();
			map.put("cdPantalla", cdPantalla);
			ValoresCamposCatalogosCompuestosVO valoresCamposCatalogosCompuestosVO = listaValores.get(i);
			String Campo = valoresCamposCatalogosCompuestosVO.getValor1();
			map.put("valor1", valoresCamposCatalogosCompuestosVO.getValor1());
			map.put("valor2", valoresCamposCatalogosCompuestosVO.getValor2());
			map.put("valor3", valoresCamposCatalogosCompuestosVO.getValor3());
			map.put("valor4", valoresCamposCatalogosCompuestosVO.getValor4());
			map.put("valor5", valoresCamposCatalogosCompuestosVO.getValor5());
			map.put("valor6", valoresCamposCatalogosCompuestosVO.getValor6());
			res = returnBackBoneInvoke(map, "CONFIGURAR_CATALOGOS_COMPUESTOS_GUARDAR_REGISTRO");
		}
		return res.getMsgText();
	}

	public String actualizarRegistro (String cdPantalla, List<ValoresCamposCatalogosCompuestosVO>listaValores) throws ApplicationException {
		WrapperResultados res = new WrapperResultados();
		for (int i=0; i<listaValores.size(); i++) {
			HashMap map = new HashMap();
			map.put("cdPantalla", cdPantalla);
			ValoresCamposCatalogosCompuestosVO valoresCamposCatalogosCompuestosVO = listaValores.get(i);
			String Campo = valoresCamposCatalogosCompuestosVO.getValor1();
			map.put("valor1", valoresCamposCatalogosCompuestosVO.getValor1());
			map.put("valor2", valoresCamposCatalogosCompuestosVO.getValor2());
			map.put("valor3", valoresCamposCatalogosCompuestosVO.getValor3());
			map.put("valor4", valoresCamposCatalogosCompuestosVO.getValor4());
			map.put("valor5", valoresCamposCatalogosCompuestosVO.getValor5());
			map.put("valor6", valoresCamposCatalogosCompuestosVO.getValor6());
			res = returnBackBoneInvoke(map, "CONFIGURAR_CATALOGOS_COMPUESTOS_ACTUALIZAR_REGISTRO");
		}
		return res.getMsgText();
	}

	public ExtJSFieldVO obtenerRegistro(String cdTabla, String cdClave)
			throws ApplicationException {

		HashMap map = new HashMap ();
		map.put("cdTabla", cdTabla);
		map.put("cdClave", cdClave);

		return (ExtJSFieldVO)getBackBoneInvoke(map, "CONFIGURAR_CATALOGOS_COMPUESTOS_OBTENER_REGISTRO");
	}

	public PagedList obtenerColumnasGrilla(String cdPantalla, List<ValoresCamposCatalogosCompuestosVO>listaValores, int start, int limit)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdPantalla", cdPantalla);
		if (listaValores != null) {
			map.put("valor1", listaValores.get(0).getValor1());
			map.put("valor2", listaValores.get(0).getValor2());
			map.put("valor3", listaValores.get(0).getValor3());
			map.put("valor4", listaValores.get(0).getValor4());
			map.put("valor5", listaValores.get(0).getValor5());
			map.put("valor6", listaValores.get(0).getValor6());
			map.put("valor7", listaValores.get(0).getValor7());
			map.put("valor8", listaValores.get(0).getValor8());
			map.put("valor9", listaValores.get(0).getValor9());
		}

		return pagedBackBoneInvoke(map, "CONFIGURAR_CATALOGOS_COMPUESTOS_OBTENER_COLUMNAS", start, limit);
	}

	public TableModelExport getModel(String cdPantalla, List<ValoresCamposCatalogosCompuestosVO> listaValores) throws ApplicationException {
		TableModelExport model = new TableModelExport();
		List lista = null;
		
		PagedList pagedList = obtenerCamposBusqueda (cdPantalla);
		lista = pagedList.getItemsRangeList();
		List listaColumnas = new ArrayList(); 

		for (int i=0; i<lista.size(); i++) {
			CampoCatalogoVO campoCatalogoVO = (CampoCatalogoVO)lista.get(i);
			//listaColumnas.add(campoCatalogoVO.getCdColumn());
			listaColumnas.add(campoCatalogoVO.getDsColumn());
		}
		
		HashMap map = new HashMap();
		map.put("cdPantalla", cdPantalla);
		if (listaValores != null) {
			map.put("valor1", listaValores.get(0).getValor1());
			map.put("valor2", listaValores.get(0).getValor2());
			map.put("valor3", listaValores.get(0).getValor3());
			map.put("valor4", listaValores.get(0).getValor4());
			map.put("valor5", listaValores.get(0).getValor5());
			map.put("valor6", listaValores.get(0).getValor6());
			map.put("valor7", listaValores.get(0).getValor7());
			map.put("valor8", listaValores.get(0).getValor8());
			map.put("valor9", listaValores.get(0).getValor9());
		}

		lista = (ArrayList)getExporterAllBackBoneInvoke(map, "CONFIGURAR_CATALOGOS_COMPUESTOS_EXPORTAR");
		model.setInformation(lista);
		model.setColumnName((String[])listaColumnas.toArray(new String[listaColumnas.size()]));

		return model;
	}

	
	public String obtenerTituloPantalla(String cdPantalla) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdPantalla", cdPantalla);
		
		WrapperResultados res = returnBackBoneInvoke(map, "CONFIGURAR_CATALOGOS_COMPUESTOS_OBTENER_TITULO");
		return res.getResultado();
	}

}