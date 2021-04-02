package juegocartas;
/**
 * Un objeto de tipo Mano representa una mano de cartas.  Las
 * cartas pertenecen a la clase Carta. 
 * Una mano está vacía cuando se crea,y con posterioridad 
 * se le puede añadir un número cartas.
 */

import java.util.ArrayList;


public class Mano {
	private ArrayList<Carta> mano;
	
	public Mano() {
		mano = new ArrayList<Carta>();
	}
	
	public void limpia() {
		mano.clear();
	}
	
	public void cogeCarta(Carta c) {
		if(c == null)
			throw new NullPointerException("No puedes añadir una carta null");
		
		mano.add(c);
	}
	
	public void quitarCarta(Carta c) {
		mano.remove(c);
	}
	
	public void quitarCarta(int posicion) {
		if(posicion <0 || posicion >= mano.size())
			throw new IllegalArgumentException("Posición no existe en la mano: " + posicion);
		mano.remove(posicion);
	}
	
	public int dameNumCartas() {
		return mano.size();
	}
	
	public Carta dameCarta(int posicion) {
		if(posicion <0 || posicion >= mano.size())
			throw new IllegalArgumentException("Posición no existe en la mano: " + posicion);
		
		return mano.get(posicion);
	}
	
	public void ordenaPorPalo() {
		
		 ArrayList<Carta> nuevaMano = new ArrayList<Carta>(mano.size());
	        while (mano.size() > 0) {
	            int pos = 0;  // Posicion de la carta menor.
	            Carta c = mano.get(0);  // Carta menor.
	            for (int i = 1; i < mano.size(); i++) {
	                Carta c1 = mano.get(i);
	                if ( c1.getPalo() < c.getPalo() ||
	                        (c1.getPalo() == c.getPalo() && c1.getValor() < c.getValor())){
	                    pos = i;
	                    c = c1;
	                }
	            }
	            mano.remove(pos);
	            nuevaMano.add(c);
	        }
	        mano = nuevaMano;
		
	}
	
	 public void ordenaPorValor() {
	        ArrayList<Carta> nuevaMano = new ArrayList<>(mano.size());
	        while (mano.size() > 0) {
	            int pos = 0;  // Posición de la carta mínima.
	            Carta c = mano.get(0);  // Carta mínima.
	            for (int i = 1; i < mano.size(); i++) {
	                Carta c1 = mano.get(i);
	                if ( c1.getValor() < c.getValor() ||
	                        (c1.getValor() == c.getValor() && c1.getPalo() < c.getPalo())) {
	                    pos = i;
	                    c = c1;
	                }
	            }
	            mano.remove(pos);
	            nuevaMano.add(c);
	        }
	        mano = nuevaMano;
	    }


}
