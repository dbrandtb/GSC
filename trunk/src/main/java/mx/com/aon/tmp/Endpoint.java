package mx.com.aon.tmp;

public abstract interface Endpoint {
	public abstract Object invoke(Object paramObject)
			throws BackboneApplicationException;

	public abstract void setService(Service paramService);

	public abstract Service getService();
}