package mx.com.aon.portal.web.tests;

import mx.com.aon.core.ApplicationException;
import mx.com.aon.portal.service.CombosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.model.ClienteCorpoDsctoVO;
import mx.com.aon.portal.model.ClientesCorpoVO;
import mx.com.aon.portal.model.ConfAlertasProcesosVO;
import mx.com.aon.portal.model.ConfAlertasTipoRamoVO;
import mx.com.aon.portal.model.ConfAlertasUsuariosVO;
import mx.com.aon.portal.model.ConfiguracionAlertasAutomaticoRolVO;
import mx.com.aon.portal.model.ElementoComboBoxVO;

import java.util.ArrayList;
import java.util.List;

public class CombosManagerTest extends AbstractTestCases {



	protected CombosManager combosManager;

    public void testComboClientes() throws Exception {
    	try {
            List lista = combosManager.comboClientes();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

/*
    public void testComboAseguradorasCliente() throws Exception {
    	try {
            List lista = combosManager.comboAseguradorasCliente("6");
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

    public void testComboProductosAseguradoraCliente() throws Exception {
    	try {
            List lista = combosManager.comboProductosAseguradoraCliente("8","46");
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

    public void testComboRamosCliente() throws Exception {
    	try {
            List lista = combosManager.comboRamosCliente("8");
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

    public void testComboEstadosAgrupacion() throws Exception {
    	try {
            List lista = combosManager.comboEstadosAgrupacionPoliza();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

    public void testComboTiposAgrupacion() throws Exception {
    	try {
            List lista = combosManager.comboTiposAgrupacion();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    @SuppressWarnings("unchecked")
public void testObtenerProductosAyuda() throws Exception {
    	try {
            //List lista = combosManager.comboProductosAyuda("1","9","202");
            @SuppressWarnings("unused")
			List lista = combosManager.comboProductos();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

/*    public void testCoberturasProductoAyuda() throws Exception {
    	try {
            List lista = combosManager.comboCoberturasProducto("777");
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

    public void testObtenerProductos() throws Exception {
    	try {
            List lista = combosManager.comboProductos();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

    public void testObtenerLineasCaptura() throws Exception {
    	try {
            List lista = combosManager.comboLineasCaptura();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }



    public void testObtenerConceptosProducto() throws Exception {
    	try {
            List lista = combosManager.comboConceptosProducto();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }


/*
   public void testComboTipoSituacion() throws Exception {
    	try {
            PagedList lista = combosManager.comboTipoSituacion();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

    public void comboCoberturas() throws Exception {
    	try {
            PagedList  lista = combosManager.comboCoberturas();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

    public void comboAseguradora() throws Exception {
    	try {
            PagedList lista = combosManager.comboAseguradoras();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    
    public void comboSecciones() throws Exception {
    	try {
            PagedList lista = combosManager.comboSecciones();
            System.out.println("sali por el comboSecciones");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    
    */
	/*
	 //FUNCIONA
     public void testComboTipoDescuento() throws Exception {
    	try {
            List lista = combosManager.comboTiposDscto();
            for(int i=0; i < lista.size(); i++){
            	ElementoComboBoxVO elementoComboBoxVO  =  (ElementoComboBoxVO) lista.get(i);
            	System.out.println("Elemento: "+elementoComboBoxVO.getCodigo()+" "+elementoComboBoxVO.getDescripcion());
            }
            System.out.println("sali por el comboTiposDscto");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
    /*
    //FUNCIONA, PERO SE LIMITO A LOS PRIMEROS 20 POR EXCEPCION EN EL 
	//HEAD DE MEMORIA POR TRAER DEMASIADOS REGISTROS
    public void testComboClientesCorpoDscto() throws Exception {
    	try {
            List lista = combosManager.comboClientesCorpoDscto();
            for(int i=0; i < lista.size(); i++){
            	ClienteCorpoDsctoVO clientesCorpoDsctoVO  =  (ClienteCorpoDsctoVO) lista.get(i);
            	System.out.println("Elemento: "+clientesCorpoDsctoVO.getCdPerson()+" "+clientesCorpoDsctoVO.getDsNombre());
            }
            System.out.println("sali por el comboClientesCorpoDscto");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
	/*
    //FUNCIONA
    public void testComboSioNo() throws Exception {
    	try {
            List lista = combosManager.comboEstadosDescuentos();
            for(int i=0; i < lista.size(); i++){
            	ElementoComboBoxVO elementoComboBoxVO  =  (ElementoComboBoxVO) lista.get(i);
            	System.out.println("Elemento: "+elementoComboBoxVO.getCodigo()+" "+elementoComboBoxVO.getDescripcion());
            }
            System.out.println("sali por el comboEstadosDescuentos");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
    /*
    //NO ESTA EL PL PARA PLANES
    public void comboPlanes() throws Exception {
    	try {
            List lista = combosManager.comboTiposDscto();
            for(int i=0; i < lista.size(); i++){
            	ElementoComboBoxVO elementoComboBoxVO  =  (ElementoComboBoxVO) lista.get(i);
            	System.out.println("Elemento: "+elementoComboBoxVO.getCodigo()+" "+elementoComboBoxVO.getDescripcion());
            }
            System.out.println("sali por el comboTiposDscto");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
	/*
	//FUNCIONA
    public void testComboProductosDscto() throws Exception {
    	try {
            List lista = combosManager.comboProductosDescuentos();
            for(int i=0; i < lista.size(); i++){
            	ElementoComboBoxVO elementoComboBoxVO  =  (ElementoComboBoxVO) lista.get(i);
            	System.out.println("Elemento: "+elementoComboBoxVO.getCodigo()+" "+elementoComboBoxVO.getDescripcion());
            }
            System.out.println("sali por el comboProductosDscto");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	*/
	
/*	public void testComboRamos() throws Exception {
    	try {
            List lista = combosManager.comboRamos();
            for(int i=0; i < lista.size(); i++){
            	ElementoComboBoxVO elementoComboBoxVO  =  (ElementoComboBoxVO) lista.get(i);
            	System.out.println("Elemento: "+elementoComboBoxVO.getCodigo()+" "+elementoComboBoxVO.getDescripcion());
            }
            System.out.println("sali por el comboRamosGenerales");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
*/	
/*	public void testComboSubramos() throws Exception {
    	try {
            List lista = combosManager.comboSubRamos();
            for(int i=0; i < lista.size(); i++){
            	ElementoComboBoxVO elementoComboBoxVO  =  (ElementoComboBoxVO) lista.get(i);
            	System.out.println("Elemento: "+elementoComboBoxVO.getCodigo()+" "+elementoComboBoxVO.getDescripcion());
            }
            System.out.println("sali por el comboRamosGenerales");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
*/
	
/*	public void testComboSubramos() throws Exception {
    	try {
            List lista = combosManager.comboProductos();
            for(int i=0; i < lista.size(); i++){
            	ElementoComboBoxVO elementoComboBoxVO  =  (ElementoComboBoxVO) lista.get(i);
            	System.out.println("Elemento: "+elementoComboBoxVO.getCodigo()+" "+elementoComboBoxVO.getDescripcion());
            }
            System.out.println("sali por el comboProductos");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
*/
/*	@SuppressWarnings("unchecked")
	public void testComboUsuarios () throws Exception{
		try {
			List lista = combosManager.comboUsuarios();
			for (int i=0; i<lista.size(); i++){
				ConfAlertasUsuariosVO confAlertasUsuariosVO = (ConfAlertasUsuariosVO)lista.get(i);
				System.out.println("Usuario: " + confAlertasUsuariosVO.getDsUsuario());
			}
			System.out.println("Terminado el combo de usuarios");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testComboConfAlertasAutoClientes () throws Exception {
		try {
			List lista = combosManager.comboConfAlertasAutoClientes();
			for (int i=0; i<lista.size(); i++) {
				ClientesCorpoVO clientesCorpoVO = (ClientesCorpoVO)lista.get(i);
				System.out.println("Cliente: " + clientesCorpoVO.getDsElemen());
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testComboConfAlertasAutoProcesos () throws Exception {
		try {
			List lista = combosManager.comboConfAlertasAutoProcesos();
			for (int i=0; i<lista.size(); i++) {
				ConfAlertasProcesosVO confAlertasProcesosVO = (ConfAlertasProcesosVO)lista.get(i);
				System.out.println("Valor: " + confAlertasProcesosVO.getOtValor());
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testComboConfAlertasAutoTemporalidad () throws Exception {
		try {
			List lista = combosManager.comboConfAlertasAutoTemporalidad();
			for (int i=0; i<lista.size(); i++) {
				ConfAlertasProcesosVO confAlertasProcesosVO = (ConfAlertasProcesosVO)lista.get(i);
				System.out.println("Valor: " + confAlertasProcesosVO.getOtValor());
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testComboConfAlertasAutoRol () throws Exception {
		try {
			List lista = combosManager.comboConfAlertasAutoRol();
			for (int i=0; i<lista.size(); i++) {
				ConfiguracionAlertasAutomaticoRolVO configuracionAlertasAutomaticoRolVO= (ConfiguracionAlertasAutomaticoRolVO)lista.get(i);
				System.out.println("Rol: " + configuracionAlertasAutomaticoRolVO.getDsRol());
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testComboConfAlertasAutoTipoRamo () throws Exception {
		try {
			List lista = combosManager.comboConfAlertasAutoTipoRamo();
			for (int i=0; i<lista.size(); i++) {
				ConfAlertasTipoRamoVO confAlertasTipoRamoVO = (ConfAlertasTipoRamoVO)lista.get(i);
				System.out.println("Tipo Ramo: " + confAlertasTipoRamoVO.getDsTipRam());
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testComboRegiones () throws Exception {
		try {
			List lista = combosManager.comboRegiones();
			for (int i=0; i<lista.size(); i++){
				ElementoComboBoxVO elementoComboBoxVO = (ElementoComboBoxVO)lista.get(i);
				System.out.println("Region: " + elementoComboBoxVO.getDescripcion());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

    public void testComboSiNo () throws Exception {
        try {
            List lista = combosManager.comboSiNo();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void testComboIdiomas () throws Exception {
        try {
            List lista = combosManager.comboIdiomas();
            System.out.println("sali");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    
  
	public void testComboEjecutivosAon () throws Exception {
        try {
            List lista = combosManager.comboEjecutivosAon();
            System.out.println("sali");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
*/	/*

	public void testComboEstadosEjecutivo () throws Exception {
        try {
            List lista = combosManager.comboEstadosEjecutivo();
            System.out.println("sali");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
	

	public void testComboLineasAtencion () throws Exception {
        try {
            List lista = combosManager.comboLineasAtencion();
            System.out.println("sali");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
   
	
	public void testComboGrupoPersonas () throws Exception {
        try {
            List lista = combosManager.comboGrupoPersonas();
            System.out.println("sali");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

	public void testPolizasPorAseguradora () throws Exception {
		try {
			List lista = combosManager.comboPolizasPorAseguradora("1");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void testReciboPorPolizasPorAseguradora () throws Exception {
		try {
			List lista = combosManager.comboReciboPorPolizaPorAseguradora("1", "1", "1");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
public void testPadresPorEstructura () throws Exception {
		try {
			@SuppressWarnings("unused")
			List lista = combosManager.comboVinculosPadrePorEstructura("1");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

public void testComboGrupoPersonas () throws Exception {
    try {
        List lista = combosManager.comboGrupoPersonasCliente("8");
        for (int i=0; i<lista.size(); i++){
			ElementoComboBoxVO elementoComboBoxVO = (ElementoComboBoxVO)lista.get(i);
			System.out.println("ID :"+elementoComboBoxVO.getCodigo()+"  Grupo Personas: " + elementoComboBoxVO.getDescripcion());
        }
        System.out.println("sali");
    } catch (Exception e) {
        e.printStackTrace();
        throw e;
    }
}
*/
	/*
@SuppressWarnings("unchecked")
public void testCombo () throws Exception {
	try {
		@SuppressWarnings("unused")
		List lista = combosManager.comboGrupoCorporativo("5017");
		//List lista = combosManager.comboPapelesPorCliente("1");
		//List lista = combosManager.comboProductosPorAseguradoraYCliente("", "", "");
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}
*/


 /*
    public void testComboFormaCalculoFolioNroIncisos() throws Exception {
    	try {
            List lista = combosManager.comboFormaCalculoFolioNroIncisos();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }*/
	/*
	public void testComboPeriodosGraciaCliente() throws Exception {
    	try {
            List lista = combosManager.comboPeriodosGracia();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	*/
	/*
	public void testComboTiposDocumento() throws Exception {
    	try {
            List lista = combosManager.comboTiposDocumento();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
	/*
	public void testComboSeccionesFormato() throws Exception {
    	try {
            List lista = combosManager.comboSeccionFormato("2");
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	*/
	/*public void testComboBloquesConfiguraAtributosFormatoOrdenTrabajo() throws Exception {
    	try {
            List lista = combosManager.comboBloquesConfiguraAtributosFormatoOrdenTrabajo();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }*/
	/*
	public void testComboFormatosCampo() throws Exception {
    	try {
            List lista = combosManager.comboCampoBloques("P", "B1");
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
	/*
	public void testComboTiposDocumento() throws Exception {
    	try {
            List lista = combosManager.comboTiposDocumento("5017", "44", "777");
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	*/
	/*
	public void testObtenerInstrumentosPago() throws Exception {
    	try {
            List lista = combosManager.comboInstrumentosPago();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	*/
	
	/*public void testObtenerBancos() throws Exception {
    	try {
            List lista = combosManager.comboBancos();
            System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }*/
	
/*	public void testObtenerTariAutom() throws Exception {
		try {
	        List lista = combosManager.comboIndicadorSINO();
	        System.out.println("sali");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
	/*@SuppressWarnings("unchecked")
	public void testDatosGenericos () throws Exception {
		try {
			@SuppressWarnings("unused")
			List lista = combosManager.comboGenerico("115TFOND");
			System.out.println("Saliendo");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/
	
/*	public void testObtenerFormaCalculo() throws Exception {
		try {
	        List lista = combosManager.comboFormaCalculo();
	        System.out.println("sali de comboFormaCalculo ************************");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
*/
/*	public void testObtenerFormaAtributo() throws Exception {
		try {
	        List lista = combosManager.comboFormaAtributo();
	        System.out.println("sali de comboFormaAtributo ************************");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/

/*	public void testObtenerAccionesRenovacionRol() throws Exception {
		try {
	        List lista = combosManager.comboAccionRenovacionRol();
	        System.out.println("sali de comboObtenerAccionesRenovacionRol ************************");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/

/*	public void testObtenerAccionesRenovacionPantalla() throws Exception {
		try {
	        List lista = combosManager.comboAccionRenovacionPantalla();
	        System.out.println("sali de comboObtenerAccionesRenovacionPantalla ************************");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/
	
/*	public void testObtenerAccionesRenovacionCampo() throws Exception {
		try {
	        List lista = combosManager.comboAccionRenovacionCampo("35");
	        System.out.println("sali de comboObtenerAccionesRenovacionCampo ************************");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/

/*	public void testObtenerAccionesRenovacionAccion() throws Exception {
		try {
	        List lista = combosManager.comboAccionRenovacionAccion();
	        System.out.println("sali de comboObtenerAccionesRenovacionAccion ************************");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/
	
	/*public void testObtenerAccionesRenovacionRoles() throws Exception {
		try {
	        List lista = combosManager.comboAccionRenovacionRoles("11", "EJECUTIVOCUENTA");
	        System.out.println("sali de comboObtenerAccionesRenovacionRoles ************************");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/
	
/* prueba para el comboTipoObjeto
	@SuppressWarnings("unchecked")
	public void testObtenerTipoObjeto() throws Exception {
	try {
        @SuppressWarnings("unused")
		List lista = combosManager.comboTipoObjeto();
        System.out.println("sali de comboTipoObjeto ************************");
	} catch (Exception e) {
		 fail("Fallo en la prueba del comboTipoObjeto");
		e.printStackTrace();
		throw e;
	}
	}*/
	
	/*public void testObtenerComboListaValoresTablaApoyo() throws Exception {
		try {
	        List lista = combosManager.comboListaValoresTablaApoyo(""); 
	        System.out.println("sali de ComboListaValoresTablaApoyo************************");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/
	
	public void testObtenerComboListaDescuentoCarritoCompras() throws Exception {
		try {
	        List lista = combosManager.obtenerClientesCarrito(); 
	        System.out.println("sali de ComboListaValoresTablaApoyo************************");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	
	}
	
}
