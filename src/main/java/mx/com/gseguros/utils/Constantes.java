package mx.com.gseguros.utils;

public interface Constantes {
	
	public static final String USER = "USUARIO";
	public static final String URLLDAP ="ldap://192.168.1.190:389";
	public static final String ContextoLDAP = "com.sun.jndi.ldap.LdapCtxFactory";
	public static final String TipoAuthLDAP = "simple";
	public static final String UsuarioLDAP = "cn=orcladmin";
	public static final String PasswordLDAP = "config98";
	public static final String SearchBaseLDAP = "cn=Users,dc=biosnettcs,dc=com";
}
