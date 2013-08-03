/**
 * 
 */


/**
 * @author CIMA_USR
 *
 */

package mx.com.aon.portal.web.tests;

//import mx.com.aon.portal.service.CombosManager;
import java.util.List;

import mx.com.aon.portal.model.AgrupacionGrupoPersonaVO;
import mx.com.aon.portal.model.ElementoComboBoxVO;
import mx.com.aon.portal.model.GrupoPersonaVO;
import mx.com.aon.portal.service.AgrupacionGrupoPersonasManager;
import mx.com.aon.portal.service.PagedList;



public class AgrupacionGrupoPersonasTest extends AbstractTestCases {


	protected AgrupacionGrupoPersonasManager agrupacionGrupoPersonasManager;
	//protected CombosManager combosManager;

/*
  public void testActualizarAgrupacionGrupoPersonasManager() throws Exception {
    	try {
    		GrupoPersonaVO grupoPersonaVO = new GrupoPersonaVO();
    		grupoPersonaVO.setCdGrupo("2");
    		grupoPersonaVO.setCdAgrGrupo("1");
    		grupoPersonaVO.setCdAgrupa("1");
    		grupoPersonaVO.setCdGrupoPer("2");    	
    		
    		//todo setear los parametros de la agregacion
    		agrupacionGrupoPersonasManager.guardarAgrupacionGrupoPersona(grupoPersonaVO);
    		PagedList pagedList = agrupacionGrupoPersonasManager.getGruposPersonas("2",1,2);
			
			assertNotNull(pagedList);
			List lista = pagedList.getItemsRangeList();
            for (int i=0; i<lista.size(); i++){
    			GrupoPersonaVO grupoPersonaVOS = (GrupoPersonaVO)lista.get(i);
    			System.out.println("Grupo ID :"+grupoPersonaVOS.getCdGrupo()+"  AgrGrupo ID: " +grupoPersonaVOS.getCdAgrGrupo()+" CDAGRUPA : "+grupoPersonaVOS.getCdAgrupa()+" CDGRUPOPER : "+grupoPersonaVOS.getCdGrupoPer());
            }
            System.out.println("sali");

			logger.error("Todo va bien en Actualizar AgrupacionGrupoPersonas");
		} catch (Exception e) {
			logger.error("Exception en Actualizar AgrupacionGrupoPersonas");
			e.printStackTrace();		
			throw e;
		}
 
    }


    public void testInsertarAgrupacionGrupoPersonasManager() throws Exception {
          try {
        	  GrupoPersonaVO grupoPersonaVO = new GrupoPersonaVO();
      		  grupoPersonaVO.setCdGrupo("2");
    		  grupoPersonaVO.setCdAgrGrupo(null);
    		  grupoPersonaVO.setCdAgrupa("1");
    		  grupoPersonaVO.setCdGrupoPer("2"); 

    		  //todo setear los parametros de la agregacion
      		agrupacionGrupoPersonasManager.guardarAgrupacionGrupoPersona(grupoPersonaVO);
      		
      		PagedList pagedList =  agrupacionGrupoPersonasManager.getGruposPersonas("2", 1, 2);
            assertNotNull(pagedList);
            List lista = pagedList.getItemsRangeList();
            for (int i=0; i<lista.size(); i++){
    			GrupoPersonaVO grupoPersonaVOS = (GrupoPersonaVO)lista.get(i);
    			System.out.println("Grupo ID :"+grupoPersonaVOS.getCdGrupo()+"  AgrGrupo ID: " +grupoPersonaVOS.getCdAgrGrupo()+" CDAGRUPA : "+grupoPersonaVOS.getCdAgrupa()+" CDGRUPOPER : "+grupoPersonaVOS.getCdGrupoPer());
            }
            System.out.println("sali");
            
            logger.error("Todo va bien en Agregar AgrupacionGrupoPersonas");
          } catch (Exception e) {
              logger.error("Exception en Agregar AgrupacionGrupoPersonas");
              e.printStackTrace();
              throw e;

          }

      }



 public void testGetAgrupacionGrupoPersonasAgpManager() throws Exception {
	try {
		String cdGrupo = "653";
		AgrupacionGrupoPersonaVO agrupacionGrupoPersonaVO = agrupacionGrupoPersonasManager.getAgrupacionGrupoPersonas(cdGrupo);
        assertNotNull(agrupacionGrupoPersonaVO);
        assertEquals("El valor de CDGRUPO debe ser ",agrupacionGrupoPersonaVO.getCdGrupo(), "653" );
        assertEquals("El valor de CDUNIECO debe ser ",agrupacionGrupoPersonaVO.getCdUnieco(), "44" );
        assertEquals("El valor de DSUNIECO debe ser ",agrupacionGrupoPersonaVO.getDsUnieco(), "GNP" );
        assertEquals("El valor de CDTIPRAM debe ser ",agrupacionGrupoPersonaVO.getCdTipram(), "7" );
        assertEquals("El valor de DSTIPRAM debe ser ",agrupacionGrupoPersonaVO.getDsTipram(), "MARITIMO Y TRANSPORTES" );
        assertEquals("El valor de CDRAMO debe ser ",agrupacionGrupoPersonaVO.getCdRamo(), "777" );
        assertEquals("El valor de DSRAMO debe ser ",agrupacionGrupoPersonaVO.getDsRamo(), "OTRO TEST" );
        assertEquals("El valor de CDTIPO debe ser ",agrupacionGrupoPersonaVO.getCdTipo(), "1" );
        assertEquals("El valor de DSAGRUPA debe ser ",agrupacionGrupoPersonaVO.getDsAgrupa(), "Papel" );
        assertEquals("El valor de CDELEMENTO debe ser ",agrupacionGrupoPersonaVO.getCdElemento(), "2006" );
        assertEquals("El valor de DSELEMEN debe ser ",agrupacionGrupoPersonaVO.getDsElemen(), "Empleados" );
		
	}catch (Exception e) {
        fail("Fallo en la lectura del registro");
        e.printStackTrace();
    }
}*/


 public void testGetAgrupacionGruposPersonasManager() throws Exception {
		try {
			
			PagedList pagedList =  (PagedList)agrupacionGrupoPersonasManager.getGruposPersonas("2", 0, 10);
            assertNotNull(pagedList);
            List lista = pagedList.getItemsRangeList();
            for (int i=0; i<lista.size(); i++){
    			GrupoPersonaVO grupoPersonaVO = (GrupoPersonaVO)lista.get(i);
    			System.out.println("Grupo ID :"+grupoPersonaVO.getCdGrupo()+"  AgrGrupo ID: " +grupoPersonaVO.getCdAgrGrupo()+" CDAGRUPA : "+grupoPersonaVO.getCdAgrupa()+" CDGRUPOPER : "+grupoPersonaVO.getCdGrupoPer());
            }
            System.out.println("sali");
            
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
}