package juegocartas;


/**
 * Un objeta de tipo carta representa una carta de una baraja de pocker,
 * incluyendo comodines. La carta tiene un palo,que puede ser espadas,
 * corazones, diamantes, bastos, o comodín. Una espada, corazón, diamante, o
 * basto tiene uno de los siguietes 13 valores: as, 2, 3, 4, 5, 6, 7, 8, 9, 10,
 * sota, reina, o rey. Notar que "as" se considera el valor más pequeño. Un
 * comodín puede tener también un valor asociado, que puede ser cualquiera.
 */

public class Carta {

	// Códigos para los palos de las cartas.

	public final static int PICAS = 0;
	public final static int CORAZONES = 1;
	public final static int DIAMANTES = 2;
	public final static int TREBOL = 3;
	public final static int COMODIN = 4;

	// Códigos para las cuatro figuras y el as.
	// Los códigos para las cartes del 2 a la 10 son
	// sus propios valores numéricos.

	public final static int AS = 1;
	public final static int SOTA = 11;
	public final static int REINA = 12;
	public final static int REY = 13;

	// Las variables privadas que continen el palo y valor concreto de cada carta

	private final int palo;
	private final int valor;

	// Constructores
	/**
	 * Crea un comodín, con 1 como valor. (Observar que "new Carta()" es equivalente
	 * a "new Carta(1,Carta.COMODIN)")
	 */

	public Carta() {
		// palo = COMODIN;
		// valor = 1;

		this(1, COMODIN);
	}

	/**
	 * Crea la carta con su palo y valor.
	 * 
	 * @param valor el valor de la nueva carta. Para una carta normal (no comodín),
	 *              el valor debe estar en el rango 1 a 13, donde 1 representa un
	 *              As. Puedes usar las constantes Carta.AS, Carta.SOTA,
	 *              Carta.REINA, y Carta.REY para el as y las figuras. Para un
	 *              comodión el valor puede ser cualquiera.
	 * @param palo  el palo de la nueva carta. Debe ser un de los valores
	 *              Carta.ESPADAS, Carta.CORAZONES, Carta.DIAMANTES, Carta.BASTOS, o
	 *              Carta.COMODIN.
	 * @throws IllegalArgumentException si el parámetro valor ne esta en el rango
	 *                                  permitido.
	 */

	public Carta(int valor, int palo) {

		if (palo != PICAS && palo != CORAZONES && palo != DIAMANTES && palo != TREBOL && palo != COMODIN)
			throw new IllegalArgumentException("Palo de carta ilegal");
		if (palo != COMODIN && (valor < 1 || valor > 13))
			throw new IllegalArgumentException("Valor de carta ilegal");

		this.valor = valor;
		this.palo = palo;

	}

	public int getPalo() {
		return palo;
	}

	public int getValor() {
		return valor;
	}

	public String damePaloComoString() {
		switch (palo) {
		case PICAS: 
			return "espadas";
		case CORAZONES:
			return "corazones";
		case DIAMANTES:
			return "Diamantes";
		case TREBOL:
			return "Bastos";
		default:
			return "Comodín";
		}
		
	}
	
	public String dameValorComoString() {
		if (palo == COMODIN)
			return "" + valor; // String.copyValueOf(valor);
		else if (valor >= 2 && valor <= 10) {
			return "" + valor;
		} else {
			switch (valor) {
			case AS:
				return "As";
			case SOTA:
				return "Sota";
			case REINA:
				return "Reina";
			default:
				return "Rey";

			}
		}
	}
	
	public String toString() {
		if(palo == COMODIN) {
			if (valor == 1)
				return "Comodin";
			else 
				return "Comodin #" + valor;
		} else {
			return dameValorComoString() + " de " + damePaloComoString();
		}
		
	}
}  
