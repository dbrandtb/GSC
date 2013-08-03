package mx.com.aon.catbo.web.tests;

import java.util.ArrayList;
import java.util.List;


import mx.com.aon.catbo.model.BackBoneResultVO;
import mx.com.aon.catbo.model.AsignarEncuestaVO;
import mx.com.aon.catbo.model.FormatoDocumentoVO;
import mx.com.aon.catbo.model.MatrizAsignacionVO;
import mx.com.aon.catbo.service.AsignarEncuestaManager;
import mx.com.aon.catbo.service.FormatosDocumentosManager;
import mx.com.aon.portal.service.PagedList;

public class AsignarEncuestaTest extends AbstractTestCases {
	protected AsignarEncuestaManager asignarEncuestaManager;
	
	 public void testObtenerEncuesta() throws Exception {
			try {
				
				PagedList pagedList =  (PagedList)asignarEncuestaManager.obtenerEncuesta("", "", "", "", "", "", 0, 10);
				//pv_dsunieco_i, pv_dsramo_i, pv_dselemento_i, pv_dsproceso_, pv_dscampan_i, pv_dsmodulo_i, pv_dsencuesta_i, start, limit
				//assertNotNull(pagedList);
	            List lista = pagedList.getItemsRangeList();
	            for (int i=0; i<lista.size(); i++){
	            	AsignarEncuestaVO asignarEncuestaVO = (AsignarEncuestaVO)lista.get(i);
	    			System.out.println("dsUnieco:"+ asignarEncuestaVO.getDsUnieco()+ "cdUnieco: "+ asignarEncuestaVO.getCdUnieco()+"cdRamo: "+asignarEncuestaVO.getCdRamo());
	            }
	            System.out.println("sali");           
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

	/*
	public void testFormatosDocumentos () throws Exception {
	      try {
	          //borro el registro ingresado previamente
	    	  formatosDocumentosManager.borrarFormatosDocumentos("4");
	      }catch (Exception e) {
	          fail("Fallo en el borrar del registro de motivos");
	          e.printStackTrace();
	          throw e;
	      }
	  }

	 public void testGetFormatosDocumentos() throws Exception {
			try {
				String CdFormato_b = "1";
				
				FormatoDocumentoVO formatoDocumentoVO = formatosDocumentosManager.getFormatosDocumentos(CdFormato_b);
			
		       assertNotNull(formatoDocumentoVO);
		        assertEquals("El valor de Codigo debe ser ",formatoDocumentoVO.getCdFormato(), "1" );
		        assertEquals("El valor de Nombre debe ser ",formatoDocumentoVO.getDsNomFormato(),"nose" );
		        assertEquals("El valor de Descripcion debe ser ",formatoDocumentoVO.getDsFormato(), "nose" );
				
			}catch (Exception e) {
		        fail("Fallo en la lectura del registro");
		        e.printStackTrace();
		    }
		}

    public void testInsertarFormatosDocumentos() throws Exception {
        try {
			FormatoDocumentoVO formatoDocumentoVO = new FormatoDocumentoVO();
			formatoDocumentoVO.setCdFormato("");
			formatoDocumentoVO.setDsNomFormato("Borrar Despues");
			formatoDocumentoVO.setDsFormato("<html><body>Por favor Borrar Despues</body></html>");
      	  
    		//todo setear los parametros de la agregacion
			formatosDocumentosManager.guardarFormatosDocumentos(formatoDocumentoVO);
			formatoDocumentoVO = formatosDocumentosManager.getFormatosDocumentos("5");

			assertNotNull(formatoDocumentoVO);
	        assertEquals("El valor de Codigo debe ser ",formatoDocumentoVO.getCdFormato(), "5" );
	        assertEquals("El valor de Nombre debe ser ",formatoDocumentoVO.getDsNomFormato(),"Borrar Despues" );
	        assertEquals("El valor de Descripcion debe ser ",formatoDocumentoVO.getDsFormato(), "<html><body>Por favor Borrar Despues</body></html>" );
	        System.out.println("sali");
          
          logger.error("Todo va bien en Insertar formatosDocumentos");
        } catch (Exception e) {
            logger.error("Exception Insertar formatosDocumentos");
            e.printStackTrace();
            throw e;

        }

    }*/
	/*
	public void testGetModel()throws Exception{
		try{
			this.formatosDocumentosManager.getModel("");
			}catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	}
*/
	 
}