package juegocartas;

/**
 *  Un objeto Baraja representa una baraja de cartas.  La baraja
 *  es de pocker y contiene 52 cartas normales y puede opcionalmente
 *  incluir también dos comodines.
 */
public class Baraja {

    /**
     * Un array de 52 o 54 cartas.  Una baraja de 54 cartas contine 2 comodines,
     * adicionales a las 52 cartas normales en una baraja de pocker.
     */
    private Carta[] baraja;

    /**
     * Guarda el númeor de cartas que ya han sido repartidas.
     */
    private int cartasUsadas;

    /**
     * Construye una baraja normal de 52 cartas, sin comodines.
     * Inicialmente, las cartas están ordenadas. 
     * El método barajar() puede ser llamado para hacer el orden
     * aleatorio (Observar que "new Baraja()" es equivalente a new Baraja(false)
     */
    
    public Baraja() {
        this(false);  // Llama al otro constructor de esta misma clase.
    }

    /**
     * Construye una baraja de cartas de pocker. La baraja contiene
     * las usuales 52 cartas y opcionalmente puede contener dos comodines adicionales,
     * para un total de 54 cartas. Inicialmente las cartas están ordenadas
     * El método barajar() puede ser llamado para hacer el orden aleatorio.
     * @param incluyeComodines si true, se añaden dos comodines a la baraja; si false,
     * no hay comodines en la baraja.
     */
    public Baraja(boolean incluyeComodines) {
        if (incluyeComodines)
            baraja = new Carta[54];
        else
            baraja = new Carta[52];
       
        int contCar = 0; // Número de cartas creadas.
        
        for ( int palo = Carta.PICAS; palo <= Carta.TREBOL; palo++ ) {
            for ( int valor = 1; valor <= 13; valor++ ) {
                baraja[contCar] = new Carta(valor,palo);
                contCar++;
            }
        }
        
        if (incluyeComodines) {
            baraja[52] = new Carta(1,Carta.COMODIN);
            baraja[53] = new Carta(2,Carta.COMODIN);
        }
        
        // Baraja recien creada,luego ninguna carta repartida aun.
        cartasUsadas = 0;
    }

    /**
     * Devuelve todas las cartas usadas a la baraja (si las hay), y
     * baraja la baraja.
     */
    public void barajar() {
    	
        for ( int i = baraja.length-1; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            Carta temp = baraja[i];
            baraja[i] = baraja[rand];
            baraja[rand] = temp;
        }
        
        cartasUsadas = 0;
    }

    /**
     * Como las cartas son sacadas de la baraja, el numero
     * en la baraja va decreciendo. Este método devuelve el número de cartas
     * que aún quedan en la baraja. El valor devuelto puede ser 52 o 54 
     * dependiendo de si hay o no comodines, cuando la baraja es creada 
     * por primera vez o está recien barajada.
     * Este se decrementa una unidad cada vez que el método extraeCarta() es llamado.
     */
    public int cartasRestantes() {
        return baraja.length - cartasUsadas;
    }

    /**
     * Extrae la proxima carta de la barja y la decuelve.  Es ilegal llamar a este
     * método si no quedan cartas en la baraja. Puedes consultar el número de cartas
     * que quedan en la baraja llmando al método cartasRestantes().
     * @return la carta que es extraida de la baraja.
     * @throws IllegalStateException si no hay cartas en la baraja
     */
    public Carta extraeCarta() {
        if (cartasUsadas == baraja.length)
            throw new IllegalStateException("No quedan cartas en la baraja.");
        
        cartasUsadas++;
        
        return baraja[cartasUsadas - 1];
        
        // Nota:  Las cartas no son literalmente borradas del array que representa
        // la baraja. Usamos la varible cartasUsadas como indice para determinar 
        // cuales aun no han sido extraidas.
    }

    /**
     * Chequea si la baraja tiene comodines
     * @return true, si es una baraja de 54 cartas conteniendo comodines, o false si
     * es una baraja de 52 cartas que no contiene comodines.
     */
    public boolean tieneComodines() {
        return (baraja.length == 54);
    }

} // fin clase Baraja
