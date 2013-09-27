package mx.com.aon.flujos.cotizacion.web;

import java.util.List;
import java.util.TreeMap;

import mx.com.aon.flujos.cotizacion.model.ObjetoCotizacionVO;
import mx.com.aon.flujos.cotizacion.model.ObjetoValorVO;
import mx.com.aon.flujos.cotizacion.service.CotizacionPrincipalManager;
import mx.com.aon.flujos.cotizacion.service.CotizacionService;
import mx.com.aon.portal.model.BaseObjectVO;
import mx.com.aon.utils.Constantes;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.biosnet.ice.ext.elements.data.StoreVO;
import com.biosnet.ice.ext.elements.form.ComboControl;
import com.biosnet.ice.ext.elements.form.ExtElement;
import com.biosnet.ice.ext.elements.form.SimpleCombo;
import com.biosnet.ice.ext.elements.utils.SimpleComboUtils;

/**
 * 
 * @author sergio.ramirez
 * 
 */
public class ListadoEspecialAction extends PrincipalCotizacionAction {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 8708706041032198307L;
	private CotizacionService cotizacionManager;
	private CotizacionPrincipalManager cotizacionManagerJdbcTemplate;
	private boolean success;
	private ObjetoCotizacionVO objetoCotizacionVO;
	private ObjetoCotizacionVO principalObjeto;

	private ObjetoValorVO objetoValorVO;
	private BaseObjectVO labelVo;

	private List<BaseObjectVO> listaTipo;
	private List<ObjetoCotizacionVO> listObjetoCotizacion;
	private List<ExtElement> itemLista;
	private List<ExtElement> itemEdicion;

	private String CDRAMO;
	private String CDTIPSIT;
	private String valor;
	private String montoCotizar;

	private String claveObjeto;
	private String descripcionObjeto;
	private String labelCombo;

	private String cdUnieco;
	private String cdRamo;
	private String estado;
	private String nmPoliza;
	private String nmSituac;
	private String cdTipobj;
	private String nmSuplem;
	private String status;
	private String cdAgrupa;
	private String nmObjeto;
	private String dsObjeto;
	private String ptObjeto;
	private String nmValor;

	private String etiqueta;
	private String descripcionObjetoEdit;
	private String montoObjetoEdit;
	/**
	 *parametro de edicion objeto 
	 */
	private String dsObjetoEdit;
	/**
	 *parametro de edicion monto 
	 */
	private String ptObjetoEdit;

	private int numeroObjeto;

	private TreeMap<String, String> parameters;
//	private GlobalVariableContainerVO containerVO;

	private boolean condicion;
	private boolean valido;

	public String string() throws Exception {

		return INPUT;
	}

	/**
	 * 
	 * @return SUCCESS
	 * @throws Exception
	 *             Metodo que carga la etiqueta.
	 */
	public String obtieneLabelObjetos() throws Exception {
		/*
		if (session.containsKey("GLOBAL_VARIABLE_CONTAINER")) {
			containerVO = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
			if (containerVO != null) {
				CDRAMO = containerVO.getValueVariableGlobal("vg.CdRamo");
				CDTIPSIT = containerVO.getValueVariableGlobal("vg.CdTipSit");
				labelVo = cotizacionManagerJdbcTemplate.getEtiqueta(CDRAMO, CDTIPSIT);
				logger.debug("Etiqueta ::" + labelVo.getLabel());
				etiqueta = labelVo.getLabel();
				success = true;
			}
		} else {
			success = false;
		}
*/
		return SUCCESS;

	}

	/**
	 * 
	 * @return SUCCESS
	 * @throws Exception
	 *             Metodo que carga la lista que llena el grid de Equipos
	 *             Especiales.
	 */
	public String obtieneEquipo() throws Exception {/*
		
		logger.debug("%%%%%%%%ENTRO AL METODO GETEQUIPO%%%%%%%%");
		if (session.containsKey("GLOBAL_VARIABLE_CONTAINER")) {
			containerVO = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
			if (containerVO != null) {
				CDRAMO = containerVO.getValueVariableGlobal("vg.CdRamo");
				CDTIPSIT = containerVO.getValueVariableGlobal("vg.CdTipSit");
				String cdUniecoEq = containerVO.getValueVariableGlobal("vg.CdUnieco");
				String cdRamoEq = containerVO.getValueVariableGlobal("vg.CdRamo");
				String estadoEq = containerVO.getValueVariableGlobal("vg.Estado");
				String nmPolizaEq = containerVO.getValueVariableGlobal("vg.NmPoliza");
				String nmSituacEq = containerVO.getValueVariableGlobal("vg.NmSituac");

				logger.debug("CDRAMO" + CDRAMO);
				logger.debug("CDTIPSIT" + CDTIPSIT);
				logger.debug("cdUniecoEq" + cdUniecoEq);
				logger.debug("cdRamoEq" + cdRamoEq);
				logger.debug("estadoEq" + estadoEq);
				logger.debug("nmPolizaEq" + nmPolizaEq);
				logger.debug("nmSituacEq" + nmSituacEq);

				objetoCotizacionVO = new ObjetoCotizacionVO();

				objetoCotizacionVO.setCdUnieco(cdUniecoEq);
				objetoCotizacionVO.setCdRamo(cdRamoEq);
				objetoCotizacionVO.setEstado(estadoEq);
				objetoCotizacionVO.setNmPoliza(nmPolizaEq);
				objetoCotizacionVO.setNmSituac(nmSituacEq);

				listObjetoCotizacion = cotizacionManagerJdbcTemplate.getListaEquipo(objetoCotizacionVO);
				logger.debug("<---Lista---->" + listObjetoCotizacion);

			}
		} else {
			success = false;
		}
		*/
		return SUCCESS;
	}

	/**
	 * 
	 * @return SUCCESS
	 * @throws Exception
	 *             Metodo que carga la lista para el combo de tipos.
	 * 
	 */
	public String obtieneTipos() throws Exception {
		
		/**TODO
		 * CAMBIAR EL NOMBRE DE ESTE METODO A OBTIENE TIPOS, YA QUE ESTA CAUSANDO CONFLICTOS
		 */
		/*
		logger.debug("Entro al metodo que carga tipos");
		if (session.containsKey("GLOBAL_VARIABLE_CONTAINER")) {
			containerVO = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
			CDRAMO = containerVO.getValueVariableGlobal("vg.CdRamo");
			CDTIPSIT = containerVO.getValueVariableGlobal("vg.CdTipSit");

			logger.debug("CDRAMO###tipos" + CDRAMO);
			logger.debug("CDTIPSIT####tipos" + CDTIPSIT);

			listaTipo = cotizacionManagerJdbcTemplate.getTipos(CDRAMO, CDTIPSIT);

			success = true;
		} else {
			success = false;
		}
		*/
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 * Metodo que carga la lista para mostrar atributos variables.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String obtieneCombos() throws Exception {
		logger.debug("entro al metodo getCombos--->>");
		logger.debug("######codigoObjeto" + claveObjeto);
		logger.debug("######descripcionObj" + descripcionObjeto);
		if (StringUtils.isNotBlank(descripcionObjeto)) {
			labelCombo = descripcionObjeto;
			session.put("DESCRIPCION_OBJETO_ESPECIAL", descripcionObjeto);
		}
		if (StringUtils.isNotBlank(claveObjeto)) {
			session.put("CLAVE_OBJETO_ESPECIAL", claveObjeto);
			logger.debug("########### Entro al metodo getCombos ###########");
			itemLista = cotizacionManager.getItems(claveObjeto);
			List<ComboControl> comboElements = cotizacionManager.getCombos(claveObjeto);
			logger.debug("#######itemLista::#########" + itemLista);
			if (!itemLista.isEmpty()) {
				valido = true;
				if (comboElements != null && !comboElements.isEmpty()) {
					SimpleComboUtils comboUtils = new SimpleComboUtils();
					List<StoreVO> storeElements = comboUtils.getDefaultStoreList(comboElements,
                            ServletActionContext.getRequest().getContextPath() + 
                            Constantes.URL_ACTION_COMBOS,false);
                    //TODO se debe modificar la forma de obtener los stores para permitir los anidados
					logger.debug("##STORE DE LOS COMBOS##" + storeElements);
					if (storeElements != null && !storeElements.isEmpty()) {
						int index = 0;
						SimpleCombo simpleCombo = null;
						for (ExtElement elements : itemLista) {
							if (elements instanceof SimpleCombo) {
								simpleCombo = (SimpleCombo) elements;
								if (index < storeElements.size()) {
									simpleCombo.setStore(modificaStore(storeElements.get(index).toString()));
									index++;
								}
							}
						}
					}
				}
				logger.debug("Lista Ext final" + itemLista);
			} else {
				logger.debug("La session no trae nada");
				valido = false;
			}
			success = true;
		}
		return SUCCESS;
	}
	/**
	 * @return SUCCESS
	 * @throws Exception
	 * Metodo que carga la lista para editar atributos variables. 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String editAtributosVariables() throws Exception{
		/*
		if (session.containsKey("GLOBAL_VARIABLE_CONTAINER")) {
			containerVO = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
			if (containerVO != null) {
				
				objetoCotizacionVO = new ObjetoCotizacionVO();
				String cdUnieco = containerVO.getValueVariableGlobal("vg.CdUnieco");
				String cdRamo = containerVO.getValueVariableGlobal("vg.CdRamo");
				String estado = containerVO.getValueVariableGlobal("vg.Estado");
				String nmPoliza = containerVO.getValueVariableGlobal("vg.NmPoliza");
				String nmSituac = containerVO.getValueVariableGlobal("vg.NmSituac");
				objetoCotizacionVO.setCdUnieco(cdUnieco);
				objetoCotizacionVO.setCdRamo(cdRamo);
				objetoCotizacionVO.setEstado(estado);
				objetoCotizacionVO.setNmPoliza(nmPoliza);
				objetoCotizacionVO.setNmSituac(nmSituac);
				logger.debug("::NUMERO DE OBJETO::"+ nmObjeto);
				logger.debug("::CLAVE TIPO OBJETO::" +cdTipobj);
				logger.debug("::DESCRIPCION OBJETO::"+ dsObjeto);
				logger.debug("::MONTO OBJETO::"+ ptObjeto);
				if(StringUtils.isNotBlank(dsObjeto) && StringUtils.isNotBlank(ptObjeto)){
					descripcionObjetoEdit= dsObjeto;
					montoObjetoEdit=ptObjeto;
				}
				objetoCotizacionVO.setNmObjeto(nmObjeto);
				objetoCotizacionVO.setCdTipobj(cdTipobj);
				
				if(StringUtils.isNotBlank(nmObjeto) && StringUtils.isNotBlank(cdTipobj)){
					if(!nmObjeto.equals("undefined") && !cdTipobj.equals("undefined")){
						session.put("LLAVE_RECARGA_VENTANA_EDICION_OBJETO", nmObjeto);
						session.put("LLAVE_RECARGA_VENTANA_EDICION_CODIGO", cdTipobj);												
					}					
				}
//				if(session.containsKey("LLAVE_RECARGA_VENTANA_EDICION_OBJETO")&& session.containsKey("LLAVE_RECARGA_VENTANA_EDICION_CODIGO")){
//				objetoCotizacionVO.setNmObjeto((String) session.get("LLAVE_RECARGA_VENTANA_EDICION_OBJETO"));
//				objetoCotizacionVO.setCdTipobj((String)session.get("LLAVE_RECARGA_VENTANA_EDICION_CODIGO"));
//				}
				itemEdicion = cotizacionManager.getAtributosEditables(objetoCotizacionVO);
				List<ComboControl> cList = cotizacionManager.getCombosEdicion(objetoCotizacionVO);
				if(itemEdicion!= null && !itemEdicion.isEmpty()){
					if(cList != null && !cList.isEmpty()){						
						SimpleComboUtils comboUtils = new SimpleComboUtils();
						List<StoreVO> storeElements = comboUtils.getDefaultStoreList(cList,"/AON/flujocotizacion/obtenerListaComboOttabval.action",false);
						if (storeElements != null && !storeElements.isEmpty()) {
							int index = 0;
							SimpleCombo simpleCombo = null;
							for (ExtElement elements : itemEdicion) {
								if (elements instanceof SimpleCombo) {
									simpleCombo = (SimpleCombo) elements;
									if (index < storeElements.size()) {
										simpleCombo.setStore(modificaStore(storeElements.get(index).toString()));
										index++;
										success=true;
									}
								}
							}
						}
					}
					logger.debug("Lista Ext final= " + itemEdicion);
				}else{
					success=false;
				}
			}
		}
		*/
		return SUCCESS;
	}
	
	
	
	
	

	/**
	 * 
	 * @return SUCESS;
	 * @throws Exception
	 *             Metodo que borra un equipo, se ejecutan dos procedures.
	 */
	public String serviceEquipo() throws Exception {
		logger.debug("valor:" + valor);
		logger.debug("cdRamo:" + cdRamo);
		logger.debug("cdAgrupa:" + cdAgrupa);
		logger.debug("cdTipobj:" + cdTipobj);
		logger.debug("cdUnieco:" + cdUnieco);
		logger.debug("dsObjeto:" + dsObjeto);
		logger.debug("estado:" + estado);
		logger.debug("nmObjeto:" + nmObjeto);
		logger.debug("nmPoliza:" + nmPoliza);
		logger.debug("nmSituac:" + nmSituac);
		logger.debug("nmSuplem:" + nmSuplem);
		logger.debug("nmValor:" + nmValor);
		logger.debug("ptObjeto:" + ptObjeto);
		logger.debug("status:" + status);

		principalObjeto = new ObjetoCotizacionVO();

		principalObjeto.setAccion(valor);
		principalObjeto.setCdAgrupa(cdAgrupa);
		principalObjeto.setCdRamo(cdRamo);
		principalObjeto.setCdTipobj(cdTipobj);
		principalObjeto.setCdUnieco(cdUnieco);
		principalObjeto.setDsObjeto(dsObjeto);
		principalObjeto.setEstado(estado);
		principalObjeto.setNmObjeto(nmObjeto);
		principalObjeto.setNmPoliza(nmPoliza);
		principalObjeto.setNmSituac(nmSituac);
		principalObjeto.setNmSuplem(nmSuplem);
		principalObjeto.setNmValor(nmValor);
		principalObjeto.setPtObjeto(ptObjeto);
		principalObjeto.setStatus(status);

		cotizacionManager.servicioSatelite(principalObjeto);
		condicion = true;
		objetoValorVO = new ObjetoValorVO();
		objetoValorVO.setCdramoSat(cdRamo);
		objetoValorVO.setCdtipoobjSat(cdTipobj);
		objetoValorVO.setCduniecoSat(cdUnieco);
		objetoValorVO.setEstadoSat(estado);
		objetoValorVO.setNmobjetoSat(nmObjeto);
		objetoValorVO.setNmpolizaSat(nmPoliza);
		objetoValorVO.setNmsituacSat(nmSituac);
		objetoValorVO.setNmsuplemSat(nmSuplem);
		objetoValorVO.setStatusSat(status);
		cotizacionManager.servicioEquipoEspecial(objetoValorVO);
		condicion = true;
		success = true;
		return SUCCESS;
	}

	public String editarEquipo() throws Exception{
		/*
		logger.debug("::ENTRANDO AL METODO QUE EDITA UN EQUIPO::");
		if (session.containsKey("GLOBAL_VARIABLE_CONTAINER")) {
			containerVO = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
			String cdUniecoEd = containerVO.getValueVariableGlobal("vg.CdUnieco");
			String cdRamoEd = containerVO.getValueVariableGlobal("vg.CdRamo");
			String estadoEd = containerVO.getValueVariableGlobal("vg.Estado");
			String nmPolizaEd = containerVO.getValueVariableGlobal("vg.NmPoliza");
			String nmSuplemEd = containerVO.getValueVariableGlobal("vg.NmSuplem");
			String nmSituacEd = containerVO.getValueVariableGlobal("vg.NmSituac");
			
			ObjetoCotizacionVO insertaObjeto = new ObjetoCotizacionVO();
			
			insertaObjeto.setAccion("U");
			insertaObjeto.setCdAgrupa("1");
			insertaObjeto.setCdRamo(cdRamoEd);
			insertaObjeto.setEstado(estadoEd);
			insertaObjeto.setNmPoliza(nmPolizaEd);
			insertaObjeto.setNmSituac(nmSituacEd);
			insertaObjeto.setNmSuplem(nmSuplemEd);
			insertaObjeto.setCdUnieco(cdUniecoEd);
			insertaObjeto.setStatus("V");
			insertaObjeto.setCdTipobj((String) session.get("LLAVE_RECARGA_VENTANA_EDICION_CODIGO"));
			insertaObjeto.setNmObjeto((String) session.get("LLAVE_RECARGA_VENTANA_EDICION_OBJETO"));
			insertaObjeto.setDsObjeto(dsObjetoEdit);
			insertaObjeto.setPtObjeto(ptObjetoEdit);
			logger.debug("CdTipobj:::"+ (String) session.get("LLAVE_RECARGA_VENTANA_EDICION_CODIGO"));
			logger.debug("NmObjeto:::"+ (String) session.get("LLAVE_RECARGA_VENTANA_EDICION_OBJETO"));
			logger.debug("dsObjetoEdit:::"+dsObjetoEdit);
			logger.debug("ptObjetoEdit:::"+ptObjetoEdit);
			
			cotizacionManager.servicioSatelite(insertaObjeto);
			
			ObjetoValorVO objetoValor = new ObjetoValorVO();
			objetoValor.setCdramoSat(cdRamoEd);
			objetoValor.setCdtipoobjSat((String) session.get("LLAVE_RECARGA_VENTANA_EDICION_CODIGO"));
			objetoValor.setNmobjetoSat((String) session.get("LLAVE_RECARGA_VENTANA_EDICION_OBJETO"));
			objetoValor.setCduniecoSat(cdUniecoEd);
			objetoValor.setEstadoSat(estadoEd);
			objetoValor.setNmpolizaSat(nmPolizaEd);
			objetoValor.setNmsituacSat(nmSituacEd);
			objetoValor.setNmsuplemSat(nmSuplemEd);
			objetoValor.setStatusSat("V");
			if (parameters != null && !parameters.isEmpty()) {
				for (String key : parameters.keySet()) {
					if (StringUtils.isNotBlank(key)&& StringUtils.isNotBlank(parameters.get(key))) {
						if (key.contains("C1")) {
							objetoValor.setOtValor01(parameters.get(key));
						}
						if (key.contains("C2")) {
							objetoValor.setOtValor02(parameters.get(key));
						}
						if (key.contains("C3")) {
							objetoValor.setOtValor03(parameters.get(key));
						}
						if (key.contains("C4")) {
							objetoValor.setOtValor04(parameters.get(key));
						}
						if (key.contains("C5")) {
							objetoValor.setOtValor05(parameters.get(key));
						}
						if (key.contains("C6")) {
							objetoValor.setOtValor06(parameters.get(key));
						}
						if (key.contains("C7")) {
							objetoValor.setOtValor07(parameters.get(key));
						}
						if (key.contains("C8")) {
							objetoValor.setOtValor08(parameters.get(key));
						}
						if (key.contains("C9")) {
							objetoValor.setOtValor09(parameters.get(key));
						}
						if (key.contains("C10")) {
							objetoValor.setOtValor10(parameters.get(key));
						}
						if (key.contains("C11")) {
							objetoValor.setOtValor11(parameters.get(key));
						}
						if (key.contains("C12")) {
							objetoValor.setOtValor12(parameters.get(key));
						}
						if (key.contains("C13")) {
							objetoValor.setOtValor13(parameters.get(key));
						}
						if (key.contains("C14")) {
							objetoValor.setOtValor14(parameters.get(key));
						}
						if (key.contains("C15")) {
							objetoValor.setOtValor15(parameters.get(key));
						}
						if (key.contains("C16")) {
							objetoValor.setOtValor16(parameters.get(key));
						}
						if (key.contains("C17")) {
							objetoValor.setOtValor17(parameters.get(key));
						}
						if (key.contains("C18")) {
							objetoValor.setOtValor18(parameters.get(key));
						}
						if (key.contains("C19")) {
							objetoValor.setOtValor19(parameters.get(key));
						}
						if (key.contains("C20")) {
							objetoValor.setOtValor20(parameters.get(key));
						}
						if (key.contains("C21")) {
							objetoValor.setOtValor21(parameters.get(key));
						}
						if (key.contains("C22")) {
							objetoValor.setOtValor22(parameters.get(key));
						}
						if (key.contains("C23")) {
							objetoValor.setOtValor23(parameters.get(key));
						}
						if (key.contains("C24")) {
							objetoValor.setOtValor24(parameters.get(key));
						}
						if (key.contains("C25")) {
							objetoValor.setOtValor25(parameters.get(key));
						}
						if (key.contains("C26")) {
							objetoValor.setOtValor26(parameters.get(key));
						}
						if (key.contains("C27")) {
							objetoValor.setOtValor27(parameters.get(key));
						}
						if (key.contains("C28")) {
							objetoValor.setOtValor28(parameters.get(key));
						}
						if (key.contains("C29")) {
							objetoValor.setOtValor29(parameters.get(key));
						}
						if (key.contains("C30")) {
							objetoValor.setOtValor30(parameters.get(key));
						}
						if (key.contains("C31")) {
							objetoValor.setOtValor31(parameters.get(key));
						}
						if (key.contains("C32")) {
							objetoValor.setOtValor32(parameters.get(key));
						}
						if (key.contains("C33")) {
							objetoValor.setOtValor33(parameters.get(key));
						}
						if (key.contains("C34")) {
							objetoValor.setOtValor34(parameters.get(key));
						}
						if (key.contains("C35")) {
							objetoValor.setOtValor35(parameters.get(key));
						}
						if (key.contains("C36")) {
							objetoValor.setOtValor36(parameters.get(key));
						}
						if (key.contains("C37")) {
							objetoValor.setOtValor37(parameters.get(key));
						}
						if (key.contains("C38")) {
							objetoValor.setOtValor38(parameters.get(key));
						}
						if (key.contains("C39")) {
							objetoValor.setOtValor39(parameters.get(key));
						}
						if (key.contains("C40")) {
							objetoValor.setOtValor40(parameters.get(key));
						}
						if (key.contains("C41")) {
							objetoValor.setOtValor41(parameters.get(key));
						}
						if (key.contains("C42")) {
							objetoValor.setOtValor42(parameters.get(key));
						}
						if (key.contains("C43")) {
							objetoValor.setOtValor43(parameters.get(key));
						}
						if (key.contains("C44")) {
							objetoValor.setOtValor44(parameters.get(key));
						}
						if (key.contains("C45")) {
							objetoValor.setOtValor45(parameters.get(key));
						}
						if (key.contains("C46")) {
							objetoValor.setOtValor46(parameters.get(key));
						}
						if (key.contains("C47")) {
							objetoValor.setOtValor47(parameters.get(key));
						}
						if (key.contains("C48")) {
							objetoValor.setOtValor48(parameters.get(key));
						}
						if (key.contains("C49")) {
							objetoValor.setOtValor49(parameters.get(key));
						}
						if (key.contains("C50")) {
							objetoValor.setOtValor50(parameters.get(key));
						}
					}						
				}
				cotizacionManager.servicioEquipoEspecial(objetoValor);
				success = true;					
			}
		}
		*/
		return SUCCESS;
	}
	
	
	/**
	 * 
	 * @return SUCCESS
	 * @throws Exception
	 *             Metodo que guarda un equipo especial.
	 */
	public String guardaEquipo() throws Exception {
		/*
		logger.debug("Entrando al metodo guardaEquipo");
		if (session.containsKey("GLOBAL_VARIABLE_CONTAINER")) {
			containerVO = (GlobalVariableContainerVO) session.get("GLOBAL_VARIABLE_CONTAINER");
			String cdUniecoGv = containerVO.getValueVariableGlobal("vg.CdUnieco");
			String cdRamoGv = containerVO.getValueVariableGlobal("vg.CdRamo");
			String estadoGv = containerVO.getValueVariableGlobal("vg.Estado");
			String nmPolizaGv = containerVO.getValueVariableGlobal("vg.NmPoliza");
			String nmSuplemGv = containerVO.getValueVariableGlobal("vg.NmSuplem");
			String nmSituacGv = containerVO.getValueVariableGlobal("vg.NmSituac");

			logger.debug("cdUniecoGv" + cdUniecoGv);
			logger.debug("cdRamoGv" + cdRamoGv);
			logger.debug("estadoGv" + estadoGv);
			logger.debug("nmPolizaGv" + nmPolizaGv);
			logger.debug("nmSuplemGv" + nmSuplemGv);
			logger.debug("nmSituacGv" + nmSituacGv);

			logger.debug("##montoCotizar##" + montoCotizar);
			ObjetoCotizacionVO insertaObjeto = new ObjetoCotizacionVO();
			insertaObjeto.setAccion("I");
			insertaObjeto.setCdAgrupa("1");
			insertaObjeto.setCdRamo(cdRamoGv);
			insertaObjeto.setCdTipobj((String) session.get("CLAVE_OBJETO_ESPECIAL"));
			insertaObjeto.setCdUnieco(cdUniecoGv);
			insertaObjeto.setDsObjeto((String) session.get("DESCRIPCION_OBJETO_ESPECIAL"));
			insertaObjeto.setEstado(estadoGv);
			insertaObjeto.setNmObjeto(null);
			insertaObjeto.setNmPoliza(nmPolizaGv);
			insertaObjeto.setNmSituac(nmSituacGv);
			insertaObjeto.setNmSuplem(nmSuplemGv);
			if (StringUtils.isNotBlank(montoCotizar)) {
				insertaObjeto.setPtObjeto(montoCotizar);
			}
			insertaObjeto.setStatus("V");


			numeroObjeto = cotizacionManager.getNumeroInsertaServicio(insertaObjeto);

			logger.debug("NUMERO DE OBJETO" + numeroObjeto);
			if (StringUtils.isNotBlank(Integer.toString(numeroObjeto))) {
				ObjetoValorVO objetoValor = new ObjetoValorVO();
				objetoValor.setCdramoSat(cdRamoGv);
				objetoValor.setCdtipoobjSat((String) session.get("CLAVE_OBJETO_ESPECIAL"));
				objetoValor.setCduniecoSat(cdUniecoGv);
				objetoValor.setEstadoSat(estadoGv);
				objetoValor.setNmobjetoSat(Integer.toString(numeroObjeto));
				objetoValor.setNmpolizaSat(nmPolizaGv);
				objetoValor.setNmsituacSat(nmSituacGv);
				objetoValor.setNmsuplemSat(nmSuplemGv);
				objetoValor.setStatusSat("V");
                logger.debug("##parameters##" + parameters);
				if (parameters != null && !parameters.isEmpty()) {
					for (String key : parameters.keySet()) {
						if (StringUtils.isNotBlank(key)
								&& StringUtils.isNotBlank(parameters.get(key))) {
							if (key.contains("C1")) {
								objetoValor.setOtValor01(parameters.get(key));
							}
							if (key.contains("C2")) {
								objetoValor.setOtValor02(parameters.get(key));
							}
							if (key.contains("C3")) {
								objetoValor.setOtValor03(parameters.get(key));
							}
							if (key.contains("C4")) {
								objetoValor.setOtValor04(parameters.get(key));
							}
							if (key.contains("C5")) {
								objetoValor.setOtValor05(parameters.get(key));
							}
							if (key.contains("C6")) {
								objetoValor.setOtValor06(parameters.get(key));
							}
							if (key.contains("C7")) {
								objetoValor.setOtValor07(parameters.get(key));
							}
							if (key.contains("C8")) {
								objetoValor.setOtValor08(parameters.get(key));
							}
							if (key.contains("C9")) {
								objetoValor.setOtValor09(parameters.get(key));
							}
							if (key.contains("C10")) {
								objetoValor.setOtValor10(parameters.get(key));
							}
							if (key.contains("C11")) {
								objetoValor.setOtValor11(parameters.get(key));
							}
							if (key.contains("C12")) {
								objetoValor.setOtValor12(parameters.get(key));
							}
							if (key.contains("C13")) {
								objetoValor.setOtValor13(parameters.get(key));
							}
							if (key.contains("C14")) {
								objetoValor.setOtValor14(parameters.get(key));
							}
							if (key.contains("C15")) {
								objetoValor.setOtValor15(parameters.get(key));
							}
							if (key.contains("C16")) {
								objetoValor.setOtValor16(parameters.get(key));
							}
							if (key.contains("C17")) {
								objetoValor.setOtValor17(parameters.get(key));
							}
							if (key.contains("C18")) {
								objetoValor.setOtValor18(parameters.get(key));
							}
							if (key.contains("C19")) {
								objetoValor.setOtValor19(parameters.get(key));
							}
							if (key.contains("C20")) {
								objetoValor.setOtValor20(parameters.get(key));
							}
							if (key.contains("C21")) {
								objetoValor.setOtValor21(parameters.get(key));
							}
							if (key.contains("C22")) {
								objetoValor.setOtValor22(parameters.get(key));
							}
							if (key.contains("C23")) {
								objetoValor.setOtValor23(parameters.get(key));
							}
							if (key.contains("C24")) {
								objetoValor.setOtValor24(parameters.get(key));
							}
							if (key.contains("C25")) {
								objetoValor.setOtValor25(parameters.get(key));
							}
							if (key.contains("C26")) {
								objetoValor.setOtValor26(parameters.get(key));
							}
							if (key.contains("C27")) {
								objetoValor.setOtValor27(parameters.get(key));
							}
							if (key.contains("C28")) {
								objetoValor.setOtValor28(parameters.get(key));
							}
							if (key.contains("C29")) {
								objetoValor.setOtValor29(parameters.get(key));
							}
							if (key.contains("C30")) {
								objetoValor.setOtValor30(parameters.get(key));
							}
							if (key.contains("C31")) {
								objetoValor.setOtValor31(parameters.get(key));
							}
							if (key.contains("C32")) {
								objetoValor.setOtValor32(parameters.get(key));
							}
							if (key.contains("C33")) {
								objetoValor.setOtValor33(parameters.get(key));
							}
							if (key.contains("C34")) {
								objetoValor.setOtValor34(parameters.get(key));
							}
							if (key.contains("C35")) {
								objetoValor.setOtValor35(parameters.get(key));
							}
							if (key.contains("C36")) {
								objetoValor.setOtValor36(parameters.get(key));
							}
							if (key.contains("C37")) {
								objetoValor.setOtValor37(parameters.get(key));
							}
							if (key.contains("C38")) {
								objetoValor.setOtValor38(parameters.get(key));
							}
							if (key.contains("C39")) {
								objetoValor.setOtValor39(parameters.get(key));
							}
							if (key.contains("C40")) {
								objetoValor.setOtValor40(parameters.get(key));
							}
							if (key.contains("C41")) {
								objetoValor.setOtValor41(parameters.get(key));
							}
							if (key.contains("C42")) {
								objetoValor.setOtValor42(parameters.get(key));
							}
							if (key.contains("C43")) {
								objetoValor.setOtValor43(parameters.get(key));
							}
							if (key.contains("C44")) {
								objetoValor.setOtValor44(parameters.get(key));
							}
							if (key.contains("C45")) {
								objetoValor.setOtValor45(parameters.get(key));
							}
							if (key.contains("C46")) {
								objetoValor.setOtValor46(parameters.get(key));
							}
							if (key.contains("C47")) {
								objetoValor.setOtValor47(parameters.get(key));
							}
							if (key.contains("C48")) {
								objetoValor.setOtValor48(parameters.get(key));
							}
							if (key.contains("C49")) {
								objetoValor.setOtValor49(parameters.get(key));
							}
							if (key.contains("C50")) {
								objetoValor.setOtValor50(parameters.get(key));
							}
						}						
					}
					cotizacionManager.servicioEquipoEspecial(objetoValor);
					success = true;					
				} else {
					success = false;
				}
				
			} else {
				success = false;
			}
		} else {
			success = false;
		}
		*/
		return SUCCESS;
	}	

	/**
	 * 
	 * @return success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 
	 * @return objetoCotizacionVO
	 */
	public ObjetoCotizacionVO getObjetoCotizacionVO() {
		return objetoCotizacionVO;
	}

	/**
	 * 
	 * @param objetoCotizacionVO
	 */
	public void setObjetoCotizacionVO(ObjetoCotizacionVO objetoCotizacionVO) {
		this.objetoCotizacionVO = objetoCotizacionVO;
	}

	/**
	 * 
	 * @return principalObjeto
	 */
	public ObjetoCotizacionVO getPrincipalObjeto() {
		return principalObjeto;
	}

	/**
	 * 
	 * @param principalObjeto
	 */
	public void setPrincipalObjeto(ObjetoCotizacionVO principalObjeto) {
		this.principalObjeto = principalObjeto;
	}

	/**
	 * 
	 * @return objetoValorVO
	 */
	public ObjetoValorVO getObjetoValorVO() {
		return objetoValorVO;
	}

	/**
	 * 
	 * @param objetoValorVO
	 */
	public void setObjetoValorVO(ObjetoValorVO objetoValorVO) {
		this.objetoValorVO = objetoValorVO;
	}

	/**
	 * 
	 * @return labelVo
	 */
	public BaseObjectVO getLabelVo() {
		return labelVo;
	}

	/**
	 * 
	 * @param labelVo
	 */
	public void setLabelVo(BaseObjectVO labelVo) {
		this.labelVo = labelVo;
	}

	/**
	 * 
	 * @return listaTipo
	 */
	public List<BaseObjectVO> getListaTipo() {
		return listaTipo;
	}

	/**
	 * 
	 * @param listaTipo
	 */
	public void setListaTipo(List<BaseObjectVO> listaTipo) {
		this.listaTipo = listaTipo;
	}

	/**
	 * 
	 * @return listObjetoCotizacion
	 */
	public List<ObjetoCotizacionVO> getListObjetoCotizacion() {
		return listObjetoCotizacion;
	}

	/**
	 * 
	 * @param listObjetoCotizacion
	 */
	public void setListObjetoCotizacion(
			List<ObjetoCotizacionVO> listObjetoCotizacion) {
		this.listObjetoCotizacion = listObjetoCotizacion;
	}

	/**
	 * 
	 * @return itemLista
	 */
	public List<ExtElement> getItemLista() {
		return itemLista;
	}

	/**
	 * 
	 * @param itemLista
	 */
	public void setItemLista(List<ExtElement> itemLista) {
		this.itemLista = itemLista;
	}

	/**
	 * 
	 * @return CDRAMO
	 */
	public String getCDRAMO() {
		return CDRAMO;
	}

	/**
	 * 
	 * @param cdramo
	 */
	public void setCDRAMO(String cdramo) {
		CDRAMO = cdramo;
	}

	/**
	 * 
	 * @return CDTIPSIT
	 */
	public String getCDTIPSIT() {
		return CDTIPSIT;
	}

	/**
	 * 
	 * @param cdtipsit
	 */
	public void setCDTIPSIT(String cdtipsit) {
		CDTIPSIT = cdtipsit;
	}

	/**
	 * 
	 * @return valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * 
	 * @param valor
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * 
	 * @return cdUnieco
	 */
	public String getCdUnieco() {
		return cdUnieco;
	}

	/**
	 * 
	 * @param cdUnieco
	 */
	public void setCdUnieco(String cdUnieco) {
		this.cdUnieco = cdUnieco;
	}

	/**
	 * 
	 * @return cdRamo
	 */
	public String getCdRamo() {
		return cdRamo;
	}

	/**
	 * 
	 * @param cdRamo
	 */
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}

	/**
	 * 
	 * @return estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * 
	 * @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * 
	 * @return nmPoliza
	 */
	public String getNmPoliza() {
		return nmPoliza;
	}

	/**
	 * 
	 * @param nmPoliza
	 */
	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}

	/**
	 * 
	 * @return nmSituac
	 */
	public String getNmSituac() {
		return nmSituac;
	}

	/**
	 * 
	 * @param nmSituac
	 */
	public void setNmSituac(String nmSituac) {
		this.nmSituac = nmSituac;
	}

	/**
	 * 
	 * @return cdTipobj
	 */
	public String getCdTipobj() {
		return cdTipobj;
	}

	/**
	 * 
	 * @param cdTipobj
	 */
	public void setCdTipobj(String cdTipobj) {
		this.cdTipobj = cdTipobj;
	}

	/**
	 * 
	 * @return nmSuplem
	 */
	public String getNmSuplem() {
		return nmSuplem;
	}

	/**
	 * 
	 * @param nmSuplem
	 */
	public void setNmSuplem(String nmSuplem) {
		this.nmSuplem = nmSuplem;
	}

	/**
	 * 
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return cdAgrupa
	 */
	public String getCdAgrupa() {
		return cdAgrupa;
	}

	/**
	 * 
	 * @param cdAgrupa
	 */
	public void setCdAgrupa(String cdAgrupa) {
		this.cdAgrupa = cdAgrupa;
	}

	/**
	 * 
	 * @return nmObjeto
	 */
	public String getNmObjeto() {
		return nmObjeto;
	}

	/**
	 * 
	 * @param nmObjeto
	 */
	public void setNmObjeto(String nmObjeto) {
		this.nmObjeto = nmObjeto;
	}

	/**
	 * 
	 * @return dsObjeto
	 */
	public String getDsObjeto() {
		return dsObjeto;
	}

	/**
	 * 
	 * @param dsObjeto
	 */
	public void setDsObjeto(String dsObjeto) {
		this.dsObjeto = dsObjeto;
	}

	/**
	 * 
	 * @return ptObjeto
	 */
	public String getPtObjeto() {
		return ptObjeto;
	}

	/**
	 * 
	 * @param ptObjeto
	 */
	public void setPtObjeto(String ptObjeto) {
		this.ptObjeto = ptObjeto;
	}

	/**
	 * 
	 * @return nmValor
	 */
	public String getNmValor() {
		return nmValor;
	}

	/**
	 * 
	 * @param nmValor
	 */
	public void setNmValor(String nmValor) {
		this.nmValor = nmValor;
	}

	/**
	 * 
	 * @return condicion
	 */
	public boolean isCondicion() {
		return condicion;
	}

	/**
	 * 
	 * @param condicion
	 */
	public void setCondicion(boolean condicion) {
		this.condicion = condicion;
	}

	/**
	 * 
	 * @return valido
	 */
	public boolean isValido() {
		return valido;
	}

	/**
	 * 
	 * @param valido
	 */
	public void setValido(boolean valido) {
		this.valido = valido;
	}

	/**
	 * 
	 * @param cotizacionManager
	 */
	public void setCotizacionManager(CotizacionService cotizacionManager) {
		this.cotizacionManager = cotizacionManager;
	}

	/**
	 * 
	 * @return parameters
	 */
	public TreeMap<String, String> getParameters() {
		return parameters;
	}

	/**
	 * 
	 * @param parameters
	 */
	public void setParameters(TreeMap<String, String> parameters) {
		this.parameters = parameters;
	}

	/**
	 * 
	 * @return montoCotizar
	 */
	public String getMontoCotizar() {
		return montoCotizar;
	}

	/**
	 * 
	 * @param montoCotizar
	 */
	public void setMontoCotizar(String montoCotizar) {
		this.montoCotizar = montoCotizar;
	}

	/**
	 * 
	 * @return claveObjeto
	 */
	public String getClaveObjeto() {
		return claveObjeto;
	}

	/**
	 * 
	 * @param claveObjeto
	 */
	public void setClaveObjeto(String claveObjeto) {
		this.claveObjeto = claveObjeto;
	}

	/**
	 * 
	 * @return descripcionObjeto
	 */
	public String getDescripcionObjeto() {
		return descripcionObjeto;
	}

	/**
	 * 
	 * @param descripcionObjeto
	 */
	public void setDescripcionObjeto(String descripcionObjeto) {
		this.descripcionObjeto = descripcionObjeto;
	}


	/**
	 * 
	 * @return etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * 
	 * @param etiqueta
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * 
	 * @return labelCombo
	 */
	public String getLabelCombo() {
		return labelCombo;
	}

	/**
	 * 
	 * @param labelCombo
	 */
	public void setLabelCombo(String labelCombo) {
		this.labelCombo = labelCombo;
	}

	/**
	 * 
	 * @return numeroObjeto
	 */
	public int getNumeroObjeto() {
		return numeroObjeto;
	}

	/**
	 * 
	 * @param numeroObjeto
	 */
	public void setNumeroObjeto(int numeroObjeto) {
		this.numeroObjeto = numeroObjeto;
	}
	/**
	 * 
	 * @return itemEdicion
	 */
	public List<ExtElement> getItemEdicion() {
		return itemEdicion;
	}
	/**
	 * 
	 * @param itemEdicion
	 */
	public void setItemEdicion(List<ExtElement> itemEdicion) {
		this.itemEdicion = itemEdicion;
	}
	/**
	 * 
	 * @return
	 */
	public String getDescripcionObjetoEdit() {
		return descripcionObjetoEdit;
	}
	/**
	 * 
	 * @param descripcionObjetoEdit
	 */
	public void setDescripcionObjetoEdit(String descripcionObjetoEdit) {
		this.descripcionObjetoEdit = descripcionObjetoEdit;
	}
	/**
	 * 
	 * @return
	 */
	public String getMontoObjetoEdit() {
		return montoObjetoEdit;
	}
	/**
	 * 
	 * @param montoObjetoEdit
	 */
	public void setMontoObjetoEdit(String montoObjetoEdit) {
		this.montoObjetoEdit = montoObjetoEdit;
	}
	/**
	 * 
	 * @return dsObjetoEdit
	 */
	public String getDsObjetoEdit() {
		return dsObjetoEdit;
	}
	/**
	 * 
	 * @param dsObjetoEdit
	 */
	public void setDsObjetoEdit(String dsObjetoEdit) {
		this.dsObjetoEdit = dsObjetoEdit;
	}
	/**
	 * 
	 * @return ptObjetoEdit
	 */
	public String getPtObjetoEdit() {
		return ptObjetoEdit;
	}
	/**
	 * 
	 * @param ptObjetoEdit
	 */
	public void setPtObjetoEdit(String ptObjetoEdit) {
		this.ptObjetoEdit = ptObjetoEdit;
	}

	public void setCotizacionManagerJdbcTemplate(
			CotizacionPrincipalManager cotizacionManagerJdbcTemplate) {
		this.cotizacionManagerJdbcTemplate = cotizacionManagerJdbcTemplate;
	}
}
