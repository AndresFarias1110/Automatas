import java.util.Vector;

import javax.swing.JOptionPane;

class Automata1 {

	private String ESTADOS = "q0";
	private boolean ACEPTADO = false;
	private String ESTADOS_FINALES[];
	private String TABLA_TRANSICION[][];
	private String CADENA;
	private String RECORRIDO = "--> q0";

	public Automata1(String[][] tablaTransicion, String cadena) {
		// this.estadosFinales = estadosFianles;
		this.TABLA_TRANSICION = tablaTransicion;
		this.CADENA = cadena;

		detectarEstadosFinales();
		descomponerCadena(this.CADENA);

	}

	private void detectarEstadosFinales() {
		// TODO Auto-generated method stub
		Vector<String> vest = new Vector<>();
		int cont = 0;
		for (int i = 1; i < TABLA_TRANSICION.length; i++) {
			String estado = String.valueOf(TABLA_TRANSICION[i][0].charAt(0));

			for (int j = 0; j < estado.length(); j++) {
				if (String.valueOf(estado.charAt(0)).equalsIgnoreCase("*")) {
					System.out.println(estado.charAt(0));
					vest.add(String.valueOf(TABLA_TRANSICION[i][0].charAt(1))
							+ String.valueOf(TABLA_TRANSICION[i][0].charAt(2)));
					TABLA_TRANSICION[i][0] = vest.get(cont);
					cont++;
				}
			}
		}
		ESTADOS_FINALES = new String[vest.size()];
		for (int i = 0; i < vest.size(); i++) {
			ESTADOS_FINALES[i] = vest.get(i);
		}
	}

	public String getEstadosFinales() {
		String estadosF = "";
		for (int i = 0; i < this.ESTADOS_FINALES.length; i++) {
			estadosF += ESTADOS_FINALES[i];
		}
		return estadosF;
	}

	public boolean getAceptado() {
		return this.ACEPTADO;
	}

	public String getRecorrido() {
		return RECORRIDO;
	}

	private void descomponerCadena(String cadena) {
		boolean acp = true;
		for (int i = 0; i < cadena.length(); i++) {
			if (TABLA_TRANSICION[0][1].equalsIgnoreCase(String.valueOf(cadena
					.charAt(i)))
					|| TABLA_TRANSICION[0][2].equalsIgnoreCase(String
							.valueOf(cadena.charAt(i)))) {
				algoritmo(String.valueOf(cadena.charAt(i)));
				
			} else {
				JOptionPane.showMessageDialog(null,
						"El caracter '" + String.valueOf(cadena.charAt(i))
								+ "' no esta dentro del lenguaje");
				ACEPTADO = false;
				acp = false;
				break;
			}
		}
		for (int i = 0; i < ESTADOS_FINALES.length; i++) {
			if (ESTADOS_FINALES[i].equals(ESTADOS) && acp) {
				ACEPTADO = true;
			}
		}
	}

	private void algoritmo(String caracter) {
		int fila = 0;
		for (int i = 1; i < TABLA_TRANSICION.length; i++) {
			if (ESTADOS.equalsIgnoreCase(TABLA_TRANSICION[i][0])) {
				fila = i;
				break;
			}
		}
		for (int columna = 1; columna < TABLA_TRANSICION[0].length; columna++) {
			if (caracter.equalsIgnoreCase(TABLA_TRANSICION[0][columna])) {
				ESTADOS = TABLA_TRANSICION[fila][columna];
				if (!ESTADOS.equals("-")) {
					RECORRIDO += "-->" + TABLA_TRANSICION[fila][columna] + "\n";
					break;
				}
			}
		}
	}
}
