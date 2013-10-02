package mx.com.aon.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.portal.model.AseguradoraVO;
import mx.com.aon.portal.model.ClientesCorpoVO;
import mx.com.aon.portal.model.DetallePlanXClienteVO;
import mx.com.aon.portal.model.ElementoComboBoxVO;
import mx.com.aon.portal.model.EstadoVO;
import mx.com.aon.portal.model.SeccionVO;
import mx.com.aon.portal.model.TiposCoberturasVO;
import mx.com.aon.portal.model.TiposSituacionVO;
import mx.com.aon.portal.service.CombosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.aon.tmp.Endpoint;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;
/**
 * Clase que implementa los servicios de la interface CombosManager
 *
 */
public class CombosManagerImpl extends AbstractManager implements
        CombosManager {

	public static Logger logger = Logger.getLogger(CombosManagerImpl.class);

    public static String TABLA_TIPO_NIVEL = "MNIVEL";

    public static String TABLA_FRECUENCIA = "CFRECUENCI";

    public static String TABLA_LINEA_CAPTURA = "TLINEANEGOCIO";
    
    public static String TABLA_ESTADOS_EJECUTIVO = "EDOEJECT";

    public static String TABLA_LINEAS_ATENCION = "TLINEANEGOCIO";
    	
    public static String TABLA_GRUPO_PERSONAS = "MGRUPOPERSONAS";

    public static String TABLA_TIPOS_AGRUPACION = "TTIPOAGRUPACION";

    public static String TABLA_ESTADOS = "TACTIVO";

    public static String TABLA_INDICADOR_NUMERACION = "CINCSUBI";

    public static String TABLA_INDICADOR_SP = "CSUFPRE";

    public static String TABLA_FORMA_CALCULO_FOLIO = "CCALCFOL";
    
    public static String TABLA_ESTADO_CIVIL = "CESTCIVI";

    public static String TABLA_GRUPO_CORPORATIVO = "MGRUPOPERSONAS";
    
    public static String TABLA_INDICADOR_SINO = "UNIVT001";
    
    public static String TABLA_INDICADOR_FORMA_CALCULO = "CFORMACALCULO";
    
    public static String TABLA_INDICADOR_FORMA_ATRIBUTO = "CTABLACALCULO";
    
    public static String TABLA_TIPO_OBJETO = "TTIPOOBJETO";
    
    public static String TABLA_ESTADOS_EJECUTIVOS_CUENTA = "ESTAAGEN";

    public static String TABLA_INDICADOR_NUMERACION_ENDOSOS = "CINDNUM";
    
    public static String TABLA_OBTENER_CATALOGO_AON = "MEQUVTAB"; 
    
    public static String TABLA_TIPO_POLIZA = "TTIPPOL";
    
    public static String TABLA_INDICADORES_SINO = "TSINO";
    
    //Métodos de lectura de combos desplegables
	@SuppressWarnings("unchecked")
	public PagedList comboTipoSituacion () throws ApplicationException {
		PagedList pagedList = new PagedList();
		List<TiposSituacionVO> lista = null;
		try {
			Endpoint endpoint = endpoints.get("TIPO_SITUACION");
			ArrayList<TiposSituacionVO> invoke = (ArrayList<TiposSituacionVO>)endpoint.invoke(null);
			lista = invoke;
		}catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage(), bae);
			throw new ApplicationException("No se pudo obtener el Listado de Situaciones");
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}

	@SuppressWarnings("unchecked")
	public PagedList comboCoberturas () throws ApplicationException {
		PagedList pagedList = new PagedList();
		List<TiposCoberturasVO> lista = null;
		try {
			Endpoint endpoint = endpoints.get("TIPOS_COBERTURAS");
			ArrayList<TiposCoberturasVO> invoke = (ArrayList<TiposCoberturasVO>)endpoint.invoke(null);
			lista = invoke;
		}catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage(), bae);
			throw new ApplicationException ("No se pudo obtener el Listado de Coberturas");
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}

	@SuppressWarnings("unchecked")
	public PagedList comboAseguradoras() throws ApplicationException {
		PagedList pagedList = new PagedList();
		List<AseguradoraVO> lista = null;
		try {
			Endpoint endpoint = endpoints.get("LISTA_ASEGURADORAS");
			ArrayList<AseguradoraVO> invoke = (ArrayList<AseguradoraVO>)endpoint.invoke(null);
			lista = invoke;
		}catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage(), bae);
			throw new ApplicationException ("No se pudo obtener las Aseguradoras");
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}

	@SuppressWarnings("unchecked")
	public PagedList getDetallePlanesXCliente(int start, int limit,
			String codigoProducto, String codigoPlan,
			String codigoTipoSituacion, String garantia)
			throws ApplicationException {
		PagedList pagedList = new PagedList ();
		List<DetallePlanXClienteVO> lista = null;
		try {
			Endpoint endpoint = endpoints.get("");
			HashMap map = new HashMap();
			map.put("pv_producto_i", codigoProducto);
			map.put("pv_plan_i", codigoPlan);
			map.put("pv_situacion_i", codigoTipoSituacion);
			map.put("pv_garantia_i", garantia);
			ArrayList<DetallePlanXClienteVO> invoke = (ArrayList<DetallePlanXClienteVO>) endpoint.invoke(map);
			lista = invoke;
		}catch (BackboneApplicationException bae) {
			logger.error(bae.getMessage(), bae);
			throw new ApplicationException("No se pudo obtener planes por cliente");
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}


    public ArrayList<ElementoComboBoxVO> obtenerProductos() throws ApplicationException {
        return null;  //Todo borrar despues
    }


	@SuppressWarnings("unchecked")
	public PagedList comboSecciones() throws ApplicationException {
		PagedList pagedList = new PagedList();
		List<SeccionVO> lista = null;
		try {
			Endpoint endpoint = this.endpoints.get("P_SECCION_CHECK");

			ArrayList<SeccionVO> invoke = (ArrayList<SeccionVO>) endpoint.invoke(null);
			lista = invoke;

		} catch (BackboneApplicationException e) {
			logger.error("Se produjo una excepcion al ejecutar obtieneSeccion",
					e);
			throw new ApplicationException(e);
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}

	public PagedList comboEstados() throws ApplicationException {
		PagedList pagedList = new PagedList();
		ArrayList<EstadoVO> lista = null;
		try {
			Endpoint endpoint = this.endpoints.get("P_OBTENE_ESTADO");
			ArrayList<EstadoVO> invoke = (ArrayList<EstadoVO>) endpoint.invoke(null);
			lista = invoke;
		} catch (BackboneApplicationException e) {
			logger.error("Se produjo una excepcion al ejecutar obtieneEstado",
					e);
			throw new ApplicationException(e);
		}
		pagedList.setItemsRangeList(lista);
		return pagedList;
	}

    @SuppressWarnings("unchecked")
    public PagedList obtenerClientesCorp() throws ApplicationException {
        PagedList pagedList = new PagedList();
        List<ClientesCorpoVO> lista = null;
        try {
            Endpoint endpoint = endpoints.get("OBTENER_CLIENTES_CORPO");
            ArrayList<ClientesCorpoVO> invoke = (ArrayList<ClientesCorpoVO>)endpoint.invoke(null);
            lista = invoke;
        }catch (BackboneApplicationException bae) {
            logger.error(bae.getMessage(), bae);
            throw new ApplicationException("No se ejecuto la busqueda de clientes");
        }
        pagedList.setItemsRangeList(lista);
        return pagedList;
    }
    
    @SuppressWarnings("unchecked")
    public PagedList obtenerClientesCorpRen() throws ApplicationException {
        PagedList pagedList = new PagedList();
        List<ClientesCorpoVO> lista = null;
        try {
            Endpoint endpoint = endpoints.get("OBTENER_CLIENTES_CORPO_REN");
            ArrayList<ClientesCorpoVO> invoke = (ArrayList<ClientesCorpoVO>)endpoint.invoke(null);
            lista = invoke;
        }catch (BackboneApplicationException bae) {
            logger.error(bae.getMessage(), bae);
            throw new ApplicationException("No se ejecuto la busqueda de clientes por corporativo para Renovacion");
        }
        pagedList.setItemsRangeList(lista);
        return pagedList;
    }
    
    @SuppressWarnings("unchecked")
    public List comboClientes() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_CLIENTES_2");
	}
    
    @SuppressWarnings("unchecked")
    public List comboPadres() throws ApplicationException {
            return getAllBackBoneInvoke(null,"OBTIENE_PADRE");
    }


    public List comboTiposNivel() throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pi_tabla", TABLA_TIPO_NIVEL);
        return getAllBackBoneInvoke(map,"OBTIENE_TIPO_NIVEL");
    }
    
    public List comboRamos() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_RAMOS");
    }
    
    public List comboRamos2(String cdunieco, String cdelemento, String cdramo) throws ApplicationException {
   	 HashMap map = new HashMap();
        map.put("cdunieco", cdunieco);
        map.put("cdelemento", cdelemento);
        map.put("cdramo", cdramo);
       return getAllBackBoneInvoke(null,"OBTIENE_RAMOS_2");
   } 
  
    
    public List comboSubRamos() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_SUBRAMOS");
    }
    
    public List comboProductos() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_PRODUCTOS_AYUDA");
    }
    
  
    public List comboProductosCliente(String cliente, String aseguradora) throws ApplicationException {
    	
        HashMap map = new HashMap ();
        map.put("pv_cdunieco_i", aseguradora);
        map.put("pv_cdelemento_i", cliente);
       
        return getAllBackBoneInvoke(map, "OBTIENE_PRODUCTOS_AYUDA_CLIENTE");
    }
    
    
    public List comboIdiomas() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_IDIOMAS_AYUDA");
    }
    
    public List comboUsuarios() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_USUARIOS_CIMA");
    }
    
    public List comboProcesos() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_PROCESOS_ALERTA");
    }

    public List comboTiposProcesos () throws ApplicationException {
    	return getAllBackBoneInvoke(null, "OBTIENE_TIPOS_PROCESOS");
    }
    public List comboTemporalidades() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_TEMPORALIDADES");
    }
    
    public List comboRegiones() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_REGIONES");
    }
    
    @SuppressWarnings("unchecked")
    public List comboIdiomasRegion(String codigoRegion) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("codigoRegion", codigoRegion);
        return getAllBackBoneInvoke(map,"OBTIENE_IDIOMAS_REGION");
    }
    
    public List comboEstadosDescuentos() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_ESTADOS_DESCUENTOS");
    }
    
    public List comboProductosDescuentos() throws ApplicationException {
        return getAllBackBoneInvoke(null,"OBTIENE_PRODUCTOS_DESCUENTOS");
    }
    
    @SuppressWarnings("unchecked")
	public List comboPlanes(String cdElemento,String cdUniEco,String cdRamo,String cdTipSit ) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdElemento", cdElemento);
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		map.put("cdTipSit", cdTipSit);
		return getAllBackBoneInvoke(map,"OBTIENE_PLANES_CIMA");
	}
    

	public List comboTiposDscto() throws ApplicationException {
		return getAllBackBoneInvoke(null,"OBTIENE_TIPOS_DSCTO");
	}

	public List comboConfAlertasAutoClientes() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_CONF_ALERTAS_AUTO_CLIENTES");
	}
	public List comboConfAlertasAutoProcesos() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_CONF_ALERTAS_AUTO_PROCESOS");
	}

	public List comboConfAlertasAutoTemporalidad() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_CONF_ALERTAS_AUTO_TEMPORALIDAD");
	}

	public List comboConfAlertasAutoRol(String cdUsuario, String cdElemento) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdelemento_i", cdElemento);
		map.put("pv_cdusuario_i", cdUsuario);		
		
		return getAllBackBoneInvoke(map, "OBTIENE_CONF_ALERTAS_AUTO_ROL");
	}

	public List comboConfAlertasAutoTipoRamo() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_CONF_ALERTAS_AUTO_TIPO_RAMO");
	}


    public List comboSiNo() throws ApplicationException {
        return getAllBackBoneInvoke(null, "OBTIENE_SINO");
    }

    @SuppressWarnings("unchecked")
    public List comboFrecuencias(String cdIdioma, String cdRegion) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pi_tabla", TABLA_FRECUENCIA);
		map.put("pv_cdidioma_i", cdIdioma);
		map.put("pv_cdregion_i", cdRegion);
		
        return getAllBackBoneInvoke(map,"OBTIENE_TIPO_NIVEL");
    }


    public List comboConceptosProducto() throws ApplicationException {
        return getAllBackBoneInvoke(null, "OBTIENE_CONCEPTO_PRODUCTO");
    }
    
    public List comboConceptosPorProducto(String cdRamo ) throws ApplicationException {
    	HashMap map = new HashMap();
    	map.put("pv_cdramo_i",cdRamo);
    	
        return getAllBackBoneInvoke(map, "OBTIENE_CONCEPTO_POR_PRODUCTO");
    }
    
    @SuppressWarnings("unchecked")
	public List comboProductosAyuda(String cdUnieco, String cdRamo, String subRamo) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdunieco_i", cdUnieco);
        map.put("pv_cdtipram_i", cdRamo);
        map.put("pv_cdsubram_i", subRamo);
        return getAllBackBoneInvoke(map,"OBTIENE_PRODUCTOS_C");
    }

    
    @SuppressWarnings("unchecked")
	public List comboProducto(String cdElemento, String cdUnieco, String cdPerson) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdelemento_i", cdElemento);
        map.put("pv_cdunieco_i", cdUnieco);
        map.put("pv_cdperson_i", cdPerson);
        return getAllBackBoneInvoke(map,"OBTIENE_PRODUCTO");
    }
    @SuppressWarnings("unchecked")
    public List comboCoberturasProducto(String cdRamo) throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pv_cdramo_i", cdRamo);
        return getAllBackBoneInvoke(map,"OBTIENE_COBERTURAS_PRODUCTO");
    }

    @SuppressWarnings("unchecked")
    public List comboLineasCaptura() throws ApplicationException {
        HashMap map = new HashMap();
        map.put("pi_tabla", TABLA_LINEA_CAPTURA);
        return getAllBackBoneInvoke(map,"OBTIENE_TIPO_NIVEL");
    }

    @SuppressWarnings("unchecked")
	public List comboGrupoPersonas() throws ApplicationException {
		 HashMap map = new HashMap();
	     map.put("pi_tabla", TABLA_GRUPO_PERSONAS);
	     return getAllBackBoneInvoke(map,"OBTIENE_GRUPO_PERSONAS");
		
	}
    
	public List comboEjecutivosAon() throws ApplicationException {
		 return getAllBackBoneInvoke(null, "OBTIENE_EJECUTIVOS_AON");
	}
	
	public List comboTipoEjecutivosAon() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPO_EJECUTIVOS_AON");
	}

	@SuppressWarnings("unchecked")
	public List comboEstadosEjecutivo(String cdIdioma,String cdRegion) throws ApplicationException {
		 HashMap map = new HashMap();
		    map.put("pi_tabla", TABLA_ESTADOS);
		    map.put("cdIdioma", cdIdioma);
		    map.put("cdRegion", cdRegion);
		    return getAllBackBoneInvoke(map,"OBTIENE_ESTADOS_EJECUTIVO");
	}

	@SuppressWarnings("unchecked")
	public List comboLineasAtencion() throws ApplicationException {
		 HashMap map = new HashMap();
	     map.put("pi_tabla", TABLA_LINEAS_ATENCION);
	     return getAllBackBoneInvoke(map,"OBTIENE_LINEAS_ATENCION");
		
	}

	public List comboObtieneCliente(String cdPerson) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List comboGrupoPersonasCliente(String cdElemento) throws ApplicationException {

		        HashMap map = new HashMap();
		        map.put("po_cdelemto", cdElemento);
		        return getAllBackBoneInvoke(map,"OBTIENE_GRUPO_PERSONAS_CLIENTE");

	}

	@SuppressWarnings("unchecked")
	public List comboPolizasPorAseguradora (String cdUniEco) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("cdUniEco", cdUniEco);
		return getAllBackBoneInvoke(map, "OBTIENE_POLIZA_POR_ASEGURADORA");
	}

	@SuppressWarnings("unchecked")
    public List comboReciboPorPolizaPorAseguradora (String cdUniEco, String cdRamo, String nmPoliza) throws ApplicationException {
    	HashMap map = new HashMap ();
    	map.put("cdUniEco", cdUniEco);
    	map.put("cdRamo", cdRamo);
    	map.put("nmPoliza", nmPoliza);
    	return getAllBackBoneInvoke(map, "OBTIENE_RECIBO_POR_POLIZA_POR_ASEGURADORA");
    }

	@SuppressWarnings("unchecked")
    public List comboVinculosPadrePorEstructura (String codigoEstructura) throws ApplicationException {
    	HashMap map = new HashMap ();
    	map.put("codigoEstructura", codigoEstructura);
    	return getAllBackBoneInvoke(map, "OBTIENE_VINCULOS_PADRES_POR_ESTRUCTURA");
    }

	@SuppressWarnings("unchecked")
	public List comboClientesCorporativo(String cdPerson)throws ApplicationException {
		HashMap map = new HashMap ();
    	map.put("cdPerson", cdPerson);
    	return getAllBackBoneInvoke(map, "OBTIENE_CLIENTES_CORPORATIVO");	
    	}

	@SuppressWarnings("unchecked")
	public List comboProductosEjecutivosCuenta(String cdPerson, String cdTipRam)
			throws ApplicationException {
		HashMap map = new HashMap ();
    	map.put("cdPerson", cdPerson);
    	map.put("cdTipRam", cdTipRam);
    	return getAllBackBoneInvoke(map, "OBTIENE_PRODUCTOS_EJECUTIVOS_CUENTA");	
	}

	@SuppressWarnings("unchecked")
    public List comboAseguradorasCliente(String cliente) throws ApplicationException {
        HashMap map = new HashMap ();
        map.put("pv_cdelemento_i", cliente);
        return getAllBackBoneInvoke(map, "OBTENER_ASEGURADORAS_CLIENTE");        
    }
	
	public List comboAseguradoraXAsegurado(String cdperson) throws ApplicationException {
        HashMap map = new HashMap ();
        map.put("pv_cdperson_i", cdperson);
        return getAllBackBoneInvoke(map, "OBTENER_ASEGURADORAS_X_ASEGURADO");        
    }

	@SuppressWarnings("unchecked")
    public List comboProductosAseguradoraCliente(String cliente, String aseguradora) throws ApplicationException {
        HashMap map = new HashMap ();
        map.put("pv_cdunieco_i", aseguradora);
        map.put("pv_cdelemento_i", cliente);
        return getAllBackBoneInvoke(map, "OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE");
    }
	
	
	@SuppressWarnings("unchecked")
    public List comboProductosAseguradoraClienteRen(String cliente, String aseguradora) throws ApplicationException {
        HashMap map = new HashMap ();
        map.put("pv_cdunieco_i", aseguradora);
        map.put("pv_cdelemento_i", cliente);
        return getAllBackBoneInvoke(map, "OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE_REN");
    }
	
	@SuppressWarnings("unchecked")
    public List comboProductosAseguradoraCliente2(String cliente, String aseguradora, String cdPerson) throws ApplicationException {
        HashMap map = new HashMap ();
        map.put("pv_cdunieco_i", aseguradora);
        map.put("pv_cdelemento_i", cliente);
        map.put("pv_cdperson_i", cdPerson);
        return getAllBackBoneInvoke(map, "OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE2");
    }

	@SuppressWarnings("unchecked")
    public List comboRamosCliente(String cliente) throws ApplicationException {
        HashMap map = new HashMap ();
        map.put("pv_cdelement_i", cliente);
        return getAllBackBoneInvoke(map, "OBTENER_RAMOS_CLIENTE");
    }
	@SuppressWarnings("unchecked")
    public List comboTiposAgrupacion() throws ApplicationException {
         HashMap map = new HashMap();
         map.put("pi_tabla", TABLA_TIPOS_AGRUPACION);
         return getAllBackBoneInvoke(map,"OBTIENE_LINEAS_ATENCION");

    }
	@SuppressWarnings("unchecked")
    public List comboEstadosAgrupacionPoliza() throws ApplicationException {
         HashMap map = new HashMap();
         map.put("pi_tabla", TABLA_ESTADOS);
         return getAllBackBoneInvoke(map,"OBTIENE_LINEAS_ATENCION");

    }
	@SuppressWarnings("unchecked")
	public List comboTiposRamoClienteEjecutivoCuenta(String cdElemento)throws ApplicationException {
		 HashMap map = new HashMap();
         map.put("cdElemento", cdElemento);
         return getAllBackBoneInvoke(map,"OBTIENE_TIPOS_RAMO_CLIENTE_EJEC_CUENTA");
	}
	@SuppressWarnings("unchecked")
	public List comboAseguradoraPorElemento(String cdElemento)
			throws ApplicationException {
		//Usado en Agrupacion Por Papel
		HashMap map = new HashMap();
		map.put("cdElemento", cdElemento);
		return getAllBackBoneInvoke(map, "OBTIENE_ASEGURADORA_POR_CLIENTE");
	}
	
	@SuppressWarnings("unchecked")
	public List comboAseguradoraPorElementoRen(String cdElemento , String cdRamo)
			throws ApplicationException {
		//Usado en Agrupacion Por Papel
		HashMap map = new HashMap();
		map.put("cdElemento", cdElemento);
		map.put("cdRamo", cdRamo);
		return getAllBackBoneInvoke(map, "OBTIENE_ASEGURADORA_POR_CLIENTE_REN");
	}
	
	@SuppressWarnings("unchecked")
	public List comboPapelesPorCliente(String codigoElemento)
			throws ApplicationException {
		//Usado en Agrupacion Por Papel
		HashMap map = new HashMap();
		map.put("codigoElemento", codigoElemento);
		return getAllBackBoneInvoke(map, "OBTIENE_PAPELES_POR_CLIENTE");
	}
	@SuppressWarnings("unchecked")
	public List comboProductosPorAseguradoraYCliente(String codigoAseguradora,
			String codigoElemento, String codigoRamo)
			throws ApplicationException {
		//Usado en Agrupacion Por Papel
		HashMap map = new HashMap();
		map.put("codigoAseguradora", codigoAseguradora);
		map.put("codigoElemento", codigoElemento);
		map.put("codigoRamo", codigoRamo);
		return getAllBackBoneInvoke(map, "OBTIENE_PRODUCTOS_POR_ASEGURADORA_Y_CLIENTE");
	}
	@SuppressWarnings("unchecked")
	public List comboTipoPersonaJ() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPO_PERSONA_J");
	}
    
    public List comboBloques() throws ApplicationException {
        return getAllBackBoneInvoke(null, "OBTIENE_BLOQUES");
    }
    @SuppressWarnings("unchecked")
	public List comboFormaCalculoFolioNroIncisos() throws ApplicationException {
		 HashMap map = new HashMap();
         map.put("pi_tabla", TABLA_FORMA_CALCULO_FOLIO);
         return getAllBackBoneInvoke(map,"OBTENER_COMBO_NUMERO_INCISO");

	}
    @SuppressWarnings("unchecked")
	public List comboIndicadorNumeracionNroIncisos()throws ApplicationException {
		 HashMap map = new HashMap();
         map.put("pi_tabla", TABLA_INDICADOR_NUMERACION);
         return getAllBackBoneInvoke(map,"OBTENER_COMBO_NUMERO_INCISO");

	}
    @SuppressWarnings("unchecked")
	public List comboIndicador_SP_NroIncisos() throws ApplicationException {
		 HashMap map = new HashMap();
         map.put("pi_tabla", TABLA_INDICADOR_SP);
         return getAllBackBoneInvoke(map,"OBTENER_COMBO_NUMERO_INCISO");

	}

	public List comboNacionalidad () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTENER_COMBO_NACIONALIDAD");
	}
	@SuppressWarnings("unchecked")
	public List comboEstadoCivil (String cdIdioma, String cdRegion) throws ApplicationException {
		 HashMap map = new HashMap();
	     map.put("pi_tabla", TABLA_ESTADO_CIVIL);
	     map.put("cdIdioma", cdIdioma);
	     map.put("cdIdioma", cdIdioma);
	     return getAllBackBoneInvoke(map,"OBTENER_ESTADO_CIVIL");
	}
	@SuppressWarnings("unchecked")
	public List comboTipoEmpresa (String tipoPersona) throws ApplicationException {
		 HashMap map = new HashMap();
	     map.put("pv_otfisjur_i", tipoPersona);
		return getAllBackBoneInvoke(map, "OBTIENE_TIPO_EMPRESA");
	}
	@SuppressWarnings("unchecked")
	public List comboTipoIdentificador (String tipoPersona) throws ApplicationException {
		 HashMap map = new HashMap();
	     map.put("pv_otfisjur_i", tipoPersona);
		return getAllBackBoneInvoke(map, "OBTIENE_TIPO_IDENTIFICADOR");
	}

	public List comboTipoDomicilio () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPOS_DOMICILIO");
	}
	public List comboPaises () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_PAISES");
	}
	@SuppressWarnings("unchecked")
	public List comboColonias(String codigoPais, String codigoEstado,
			String codigoMunicipio) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codigoPais", codigoPais);
		map.put("codigoEstado", codigoEstado);
		map.put("codigoMunicipio", codigoMunicipio);

		return getAllBackBoneInvoke(map, "OBTIENE_COLONIAS");
	}
	@SuppressWarnings("unchecked")
	public List comboEstados(String codigoPais) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codigoPais", codigoPais);

		return getAllBackBoneInvoke(map, "OBTIENE_ESTADOS");
	}
	@SuppressWarnings("unchecked")
	public List comboMunicipios(String codigoPais, String codigoEstado)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("codigoPais", codigoPais);
		map.put("codigoEstado", codigoEstado);

		return getAllBackBoneInvoke(map, "OBTIENE_MUNICIPIOS");
	}
	@SuppressWarnings("unchecked")
	public List comboTiposTelefono  () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPOS_TELEFONO");
	}
	@SuppressWarnings("unchecked")
	public List comboGrupoCorporativo (String cdElemento) throws ApplicationException {
		HashMap map = new HashMap ();
		/*map.put("pi_tabla", TABLA_GRUPO_CORPORATIVO);
		map.put("pi_atrib_desc", "DSGRUPO");*/
		map.put("cdElemento", cdElemento);
		return getAllBackBoneInvoke(map, "OBTIENE_GRUPO_CORPORATIVO");
	}

	public List comboEstadosCorporativos () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_ESTADOS_CORPORATIVO");
	}

	public List comboGrupoNivelCorporativo() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_NIVEL_CORPORATIVO");
	}
	public List comboSexo () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_SEXO");
		
	}
	
	public List comboMetodoCancelacion() throws ApplicationException {
		return getAllBackBoneInvoke(null, "METODO_CANCELACION");
	}

	public List comboRazonCancelacion() throws ApplicationException {
		return getAllBackBoneInvoke(null, "RAZON_CANCELACION");
	}
	
	public List comboRazonCancelacionProducto(String cdRamo) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("cdRamo", cdRamo);
		return getAllBackBoneInvoke(map, "RAZON_CANCELACION_PRODUCTO");
	} 
	
	public List comboPeriodosGracia() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_PERIODOS_GRACIA");
	}
	@SuppressWarnings("unchecked")

	public List comboTiposDocumento(String cdElemento, String cdUniEco, String cdRamo) throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("cdElemento", cdElemento);
		map.put("cdUniEco", cdUniEco);
		map.put("cdRamo", cdRamo);
		return getAllBackBoneInvoke(map, "OBTIENE_TIPOS_DOCUMENTO");
	} 
	
	public List comboSeccionesOrden() throws ApplicationException {
		return getAllBackBoneInvoke(null, "SECCIONES_ORDEN");
	}

	public List comboTiposObjetos() throws ApplicationException {
		// TODO Auto-generated method stub
		return getAllBackBoneInvoke(null, "TIPO_OBJETO");
	}

	@SuppressWarnings("unchecked")

	public List comboSeccionFormato(String cdFormato) throws ApplicationException {
			HashMap map = new HashMap ();
			map.put("cdFormato", cdFormato);
			return getAllBackBoneInvoke(map, "OBTIENE_SECCION_FORMATO");
		}

		
	@SuppressWarnings("unchecked")
	public List comboBloquesConfiguraAtributosFormatoOrdenTrabajo()
			throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_BLOQUES_CONF_ATRIBUTO_FORMATO_ORDEN_TRABAJO");
	}


	@SuppressWarnings("unchecked")
	public List comboCampoBloques(String otTipo, String cdBloque)throws ApplicationException {
		HashMap map = new HashMap ();
		map.put("otTipo", otTipo);
		map.put("cdBloque", cdBloque);
		return getAllBackBoneInvoke(map, "OBTIENE_CAMPO_BLOQUE");
	}

	public List comboFormatosCampo() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_FORMATO_CAMPO");
	} 
	
	public List comboTipoError () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPO_ERROR");
	}

	//************* Asociar formato de orden de trabajo *******************
	public List comboFormaCalculoFolio() throws ApplicationException {
		return getAllBackBoneInvoke(null, "FORMAS_CALCULO_FOLIO");
	}

	public List comboFormato() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_FORMATOS");
	}

	public List comboProceso() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_PROCESOS");
	}
	@SuppressWarnings("unchecked")
	public List comboClientesCorpPorUsuario (String cdUsuario) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUsuario", cdUsuario);
		return getAllBackBoneInvoke(map, "OBTIENE_CLIENTES_CORP_POR_USUARIO");
	}
	@SuppressWarnings("unchecked")
	public List comboProductosTipoRamoCliente(String cdElemento, String cdTramo) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdElemento", cdElemento);
		map.put("cdTramo", cdTramo);
		return getAllBackBoneInvoke(map, "OBTIENE_PRODUCTOS_TRAMO_CLIENTE");
	}
	@SuppressWarnings("unchecked")
	public List comboTipoSituacionProducto(String cdRamo)throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdramo_i", cdRamo);
		return getAllBackBoneInvoke(map, "OBTIENE_TIPO_SITUACION_PRODUCTO");		
	}
	
	public List comboBancos() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_BANCOS");
	}

	public List comboInstrumentosPago() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_INSTRUMENTOS_PAGO");
	}

	public List comboTiposTarjeta() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_TIPOS_TARJETA");
	}
	@SuppressWarnings("unchecked")
	public List comboAseguradoraPorClienteYProducto (String cdElemento, String cdRamo) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdElemento", cdElemento);
		map.put("cdRamo", cdRamo);
		return getAllBackBoneInvoke(map, "LISTA_ASEGURADORAS_POR_CLIENTE_Y_PRODUCTO");
	}
	@SuppressWarnings("unchecked")
	public List comboIndicadorSINO () throws ApplicationException{
		 HashMap map = new HashMap();
		map.put("pi_tabla", TABLA_INDICADOR_SINO);
		return getAllBackBoneInvoke(map,"OBTIENE_ESTADOS_EJECUTIVO");
	}
	@SuppressWarnings("unchecked")
	public List comboTipoSituacionPorProductoYPlan (String cdRamo, String cdPlan) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdRamo", cdRamo);
		map.put("cdPlan", cdPlan);

		return getAllBackBoneInvoke(map, "OBTIENE_SITUACION_POR_PRODUCTO_Y_PLAN");
	}
	@SuppressWarnings("unchecked")
	public List comboTipoGarantiaPorProductoYSituacion(String cdRamo, String cdTipSit) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdRamo", cdRamo);
		map.put("cdTipSit", cdTipSit);

		return getAllBackBoneInvoke(map, "OBTIENE_GARANTIA_POR_PRODUCTO_Y_SITUACION");
	}
	@SuppressWarnings("unchecked")
	public List comboPlanesProducto(String cdRamo) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("po_producto", cdRamo);

		return getAllBackBoneInvoke(map, "OBTIENE_PLANES_PRODUCTO");
	}
	
	public List comboPersonas() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_PERSONAS");
	}
	@SuppressWarnings("unchecked")
	public List comboGenerico(String idTablaLogica) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("TablaLogica", idTablaLogica);
		return getAllBackBoneInvoke(map, "COMBO_DATOS_GENERICOS");
	}
	@SuppressWarnings("unchecked")
	public List comboFormaCalculo() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pi_tabla", TABLA_INDICADOR_FORMA_CALCULO);
		return getAllBackBoneInvoke(map,"OBTIENE_ESTADOS_EJECUTIVO");
	}
	@SuppressWarnings("unchecked")
	public List comboFormaAtributo() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pi_tabla", TABLA_INDICADOR_FORMA_ATRIBUTO);
		return getAllBackBoneInvoke(map,"OBTIENE_ESTADOS_EJECUTIVO");
	}
	@SuppressWarnings("unchecked")
	public List comboNroPolizas(String cdUniEco, String cdRamo, String cdPlan, String cdElemento) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_cdplan_i", cdPlan);
		map.put("pv_cdelemento_i", cdElemento);
		return getAllBackBoneInvoke(map,"OBTIENE_NRO_POLIZAS");
	}
	@SuppressWarnings("unchecked")
	public List comboPolizasMaestras(String cdCia, String cdElemento, String cdRamo) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdcia_i", cdCia);
		map.put("pv_cdelemento_i", cdElemento);
		map.put("pv_cdramo_i", cdRamo);
		return getAllBackBoneInvoke(map,"OBTIENE_POLIZAS_MAESTRAS");
	}
	
	public List comboTiposRenovacion() throws ApplicationException{
		return getAllBackBoneInvoke(null, "OBTIENE_TIPOS_RENOVA");
	}
	
	public List comboAccionRenovacionRol(String cdElemento) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdelemento_i", cdElemento);
		return getAllBackBoneInvoke(map, "OBTIENE_ACCION_RENOVACION_ROL");
	}

	public List comboAccionRenovacionPantalla() throws ApplicationException{
		return getAllBackBoneInvoke(null, "OBTIENE_ACCION_RENOVACION_PANTALLA");
	}
	@SuppressWarnings("unchecked")
	public List comboAccionRenovacionCampo(String cdTitulo) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdTitulo", cdTitulo);
		return getAllBackBoneInvoke(map, "OBTIENE_ACCION_RENOVACION_CAMPO");
	}

	public List comboAccionRenovacionAccion() throws ApplicationException{
		return getAllBackBoneInvoke(null, "OBTIENE_ACCION_RENOVACION_ACCION");
	}
	@SuppressWarnings("unchecked")
	public List comboAccionRenovacionRoles(String cdRenova) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("cdRenova", cdRenova);
		return getAllBackBoneInvoke(map, "OBTIENE_ACCION_RENOVACION_ROLES");
		
	}
	
	@SuppressWarnings("unchecked")
	public List obtenerRamosPorCliente(String cdElemento) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdelement_i", cdElemento);
		return getAllBackBoneInvoke(map, "OBTIENE_RAMOS_CLIENTE");
	}
	
	@SuppressWarnings("unchecked")
	public List obtenerRamosPorClienteRen(String cdElemento) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdelement_i", cdElemento);
		return getAllBackBoneInvoke(map, "OBTIENE_RAMOS_CLIENTE_REN");
	}

	@SuppressWarnings("unchecked")
	public List comboTipoObjeto() throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pi_tabla", TABLA_TIPO_OBJETO);
		return getAllBackBoneInvoke(map,"OBTIENE_TIPO_OBJETO");
	}

	@SuppressWarnings("unchecked")
	public List obtenerFormasPago(String otClave1) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("p_cliente", otClave1);
		return getAllBackBoneInvoke(map, "PERXCTE");
	}

	@SuppressWarnings("unchecked")
	public List obtenerInstrumentosPago() throws ApplicationException {
		return getAllBackBoneInvoke(null, "INST_PAGO");
	}

	@SuppressWarnings("unchecked")
	public List obtenerRoles() throws ApplicationException {
		return getAllBackBoneInvoke(null, "ROL");
	}

	@SuppressWarnings("unchecked")
	public List obtenerPersonas(String sesionUsuario)
			throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_usuario_i", sesionUsuario);
		return getAllBackBoneInvoke(map, "OBTIENE_PERSONA");
	}

	@SuppressWarnings("unchecked")
	public List obtenerIncisos(String cdUniEco, String cdRamo, String estado,
			String nmPoliza, String nmSituac) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("p_cliente", estado);
		map.put("p_cliente", nmPoliza);
		map.put("p_cliente", nmSituac);
		
		return getAllBackBoneInvoke(map, "OBTIENE_MPOLISIT");
	}

	@SuppressWarnings("unchecked")
	public List obtenerCoberturasProducto(String cdUniEco, String cdRamo,
			String nmPoliza) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdunieco_i", cdUniEco);
		map.put("pv_cdramo_i", cdRamo);
		map.put("p_cliente", nmPoliza);
		
		return getAllBackBoneInvoke(map, "COBERTURAS_PRODUCTO");
	}

	@SuppressWarnings("unchecked")
	public List obtenerObjetosRamos(String cdRamo, String cdTipSit)
			throws ApplicationException {
		
		HashMap map = new HashMap();
		
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_cdtipsit_i", cdTipSit);
		
		return getAllBackBoneInvoke(map, "OBJETO_RAMO");
	}

	public List obtenerEstadosEjecutivosCuenta() throws ApplicationException {
		HashMap map = new HashMap();
		map.put("tabla", TABLA_ESTADOS_EJECUTIVOS_CUENTA);

		return getAllBackBoneInvoke(map, "OBTENER_ESTADOS_EJECUTIVOS_CUENTA");
	}

	public List obtenerCatalogo(String nombreTabla) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("tabla", nombreTabla);

		return getAllBackBoneInvoke(map, "OBTENER_DATOS_CATALOGO");
	}

	public List obtenerProductoPorAseguradora(String cdUniEco) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdUniEco", cdUniEco);

		return getAllBackBoneInvoke(map, "OBTENER_PRODUCTOS_POR_ASEGURADORA");
	}

    public List comboListaValores (String cdTipo) throws ApplicationException {
    	HashMap map = new HashMap ();
    	map.put("cdTipo", cdTipo);
    	
    	return getAllBackBoneInvoke(map, "ATRIBUTOS_VARIABLES_AGENTE_LISTA_VALORES");
    }
    
    @SuppressWarnings("unchecked")
	public List obtenerAseguradorasPorProducto(String cdRamo) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_producto_i", cdRamo);

		return getAllBackBoneInvoke(map, "OBTENER_ASEGURADORAS_POR_PRODUCTO");
	}

	@SuppressWarnings("unchecked")
	public List obtenerIdiomas() throws ApplicationException {
		return getAllBackBoneInvoke(null, "TIDIOMAS");
	}

	@SuppressWarnings("unchecked")
	public List obtenerCoberturaPlan(String cdRamo, String cdTipSit,
			String cdPlan) throws ApplicationException {		
		HashMap map = new HashMap();
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_cdtipsit_i", cdTipSit);
		map.put("pv_cdplan_i", cdPlan);
		return getAllBackBoneInvoke(map, "COBERTURAS_X_PLAN");
	}

	@SuppressWarnings("unchecked")
	public List obtenerPlanProducto(String cdRamo) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdramo_i", cdRamo);
		return getAllBackBoneInvoke(map, "PLANES");
	}

	@SuppressWarnings("unchecked")
	public List obtenerProductoPlan() throws ApplicationException {
		return getAllBackBoneInvoke(null, "PRODUCTOS_X_PLAN");
	}

	@SuppressWarnings("unchecked")
	public List obtenerSituacionPlan(String cdRamo, String cdPlan)
			throws ApplicationException {		
		HashMap map = new HashMap();
		map.put("pv_cdramo_i", cdRamo);
		map.put("pv_cdplan_i", cdPlan);		
		return getAllBackBoneInvoke(map, "SITUACION_X_PLAN");
	}

	@SuppressWarnings("unchecked")
	public List obtenerCategoriasPersonas() throws ApplicationException {
		return getAllBackBoneInvoke(null, "CATEGORIA_PERSONA");
	}

    public List obtenerComboPolizas (String cdRamo, String cdUniEco) throws ApplicationException {
    	HashMap map = new HashMap();
    	map.put("cdUniEco", cdUniEco);
    	map.put("cdRamo", cdRamo);

    	return getAllBackBoneInvoke(map, "CANCELACION_MANUAL_POLIZAS_COMBO_POLIZAS");
    }

    public List obtenerComboAseguradoras () throws ApplicationException {
    	return getAllBackBoneInvoke(null, "CANCELACION_MANUAL_POLIZAS_COMBO_ASEGURADORAS");
    }

    public List obtenerComboPolizasCanceladas (String cdUniEco, String cdRamo) throws ApplicationException {
    	HashMap map = new HashMap ();
    	map.put("cdUniEco", cdUniEco);
    	map.put("cdRamo", cdRamo);

    	return getAllBackBoneInvoke(map, "REHABILITACION_MANUAL_POLIZAS_COMBO_POLIZAS_CANCELADAS");
    }

    public List obtenerComboCatalogosDinamicos (String cdColumna, String cdClave1, String cdClave2) throws ApplicationException {
    	HashMap map = new HashMap ();
    	map.put("cdColumna", cdColumna);
    	map.put("cdClave1", cdClave1);
    	map.put("cdClave2", cdClave2);

    	return getAllBackBoneInvoke(map, "COMBO_CATALOGOS_DINAMICOS");
    }

    @SuppressWarnings("unchecked")
	public List comboClientesCorpBO() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_CLIENTES_CORP_BO");
	}

	@SuppressWarnings("unchecked")
	public List obtenerRolesUsuarios() throws ApplicationException {	
		return  getAllBackBoneInvoke(null, "OBTIENE_ROL_SEG");
	}

	@SuppressWarnings("unchecked")
	public List obtenerUsuarios(String pv_cdsisrol_i) throws ApplicationException {
		HashMap map = new HashMap();
		
		map.put("pv_cdsisrol_i",pv_cdsisrol_i);

		String endpointName = "OBTENER_USUARIOS";
		return  getAllBackBoneInvoke(map, endpointName);
	}
	
	@SuppressWarnings("unchecked")
	public List obtenerDatosCatalogo(String pv_cdtabla_i) throws ApplicationException{
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", pv_cdtabla_i);
		return getAllBackBoneInvoke(map,"OBTENER_DATOS_CATALOGO_VARIABLE");	

	}
	
	public List obtenerClientesCarrito () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_CLIENTES_CARRITO");
	}
	
	public List obtenerClientesCorpora () throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTIENE_CLIENTES_CORPO");
	}
	
	@SuppressWarnings("unchecked")
	public List obtenerIndNumEndosos() throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pi_tabla", TABLA_INDICADOR_NUMERACION_ENDOSOS);

		return getAllBackBoneInvoke(map, "OBTENER_INDICADOR_NUMERO_ENDOSOS");
	}

	@SuppressWarnings("unchecked")
	public List obtenerProcesoPoliza() throws ApplicationException {
		return getAllBackBoneInvoke(null, "OBTENER_PROCESO_POLIZA");
	}
	
	@SuppressWarnings("unchecked")
	public List obtenerTipoPoliza(String pv_cdidioma_i, String pv_cdregion_i) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("pv_cdtabla_i", TABLA_TIPO_POLIZA);
		map.put("pv_cdidioma_i", pv_cdidioma_i);
		map.put("pv_cdregion_i", pv_cdregion_i);
		return getAllBackBoneInvoke(map, "OBTENER_TIPO_POLIZA");
	}
	
    @SuppressWarnings("unchecked")
	public List obtenerVinculoPadreEditar(String cdEstruc, String cdElemento) throws ApplicationException {
		HashMap map = new HashMap();
		map.put("cdEstruc", cdEstruc);
		map.put("cdElemento", cdElemento);

		return getAllBackBoneInvoke(map,"OBTIENE_VINCULO_PADRE_EDITAR");
	}
    
    
    @SuppressWarnings("unchecked")
	public List comboIndicadoresSINO () throws ApplicationException{
		 HashMap map = new HashMap();
		 map.put("pv_tabla", TABLA_INDICADORES_SINO );
		 return getAllBackBoneInvoke(map,"OBTIENE_P_LISTA_TCATALOG");
	}

}