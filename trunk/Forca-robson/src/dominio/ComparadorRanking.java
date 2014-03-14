package dominio;

import java.util.Comparator;

public class ComparadorRanking implements Comparator<UsuarioPontos> {

	@Override
	public int compare(UsuarioPontos o1, UsuarioPontos o2) {
		 	if (o1.getPontos() > o2.getPontos()) return -1;  
	        else if (o1.getPontos() < o2.getPontos()) return +1; 
		return 0;
	}

}
