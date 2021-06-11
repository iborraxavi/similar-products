package com.xiborra.similarproducts.constants;

public class HttpConnectionPoolConstants {

	// Número máximo de peticiones desde una misma ruta, como esto es para llamadas
	// internas siempre son desde las mismas rutas por lo que lo igualamos al maximo
	// total
	public static final Integer MAX_PER_ROUTE = 2000;
	// Maximo total de peticiones
	public static final Integer MAX_TOTAL = 2000;
	// DOC APACHE: Determines the timeout in milliseconds until a connection is
	// established. A timeout value of zero is interpreted as an infinite timeout.
	public static final Integer CONNECTION_TIMEOUT = 30000;
	// DOC APACHE: Defines the socket timeout (SO_TIMEOUT) in milliseconds,
	// which is the timeout for waiting for data or, put differently,
	// a maximum period inactivity between two consecutive data packets).
	// * A timeout value of zero is interpreted as an infinite timeout.
	// * A negative value is interpreted as undefined (system default).
	public static final Integer SOCKET_TIMEOUT = 0;

	private HttpConnectionPoolConstants() {

	}
	
}
