package mx.com.aon.catbo.web.tests;

import java.util.ArrayList;
import java.util.List;

import mx.com.aon.catbo.model.RolesVO;
import mx.com.aon.catbo.model.UsuarioVO;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.catbo.service.UsuariosManager;

public class UsuariosTest extends AbstractTestCases {
	protected UsuariosManager usuariosManager;
	
	public void testUsuarios () throws Exception {
		try {
			//Obtener Datos
			//UsuarioVO usuarioVO = usuariosManager.getDatos("GGARCIA");
			//System.out.println("Usuario: " + usuarioVO.getNombre());
			
			//Obtener Grupos de Pertenencia
			/*PagedList pagedList = usuariosManager.getRoles("PZARATE", 0, 10);
			List lista = new ArrayList();
			lista = pagedList.getItemsRangeList();
			for (int i=0; i<lista.size(); i++) {
				RolesVO rolesVO = (RolesVO)lista.get(i);
				System.out.println("Rol: " + rolesVO.getNombreRol());
			}*/
			
			//Validacion Usuario en Grupo
			String valida = usuariosManager.isUserInGroup("PZARATE", "EJEC_CTA2");
			System.out.println("Validacion: " + valida);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
