package generatoryKrawedzi;
import java.util.Random;

import glowny.Graf;



public class GeneratorKrawedziScaleFree implements GeneratorKrawedzi{
		// czesc do nicjalizacji odpowiada parametrowi m0 algorytmu Barabasi Alberta
		//tam to jest jako liczba a nie procent i musi zachodzic m <= m0
		private final double CZESC_DO_INICJALIZACJI = 0.1;
		int liczbaWezlow;
		int liczbaWezlowWPodgrafie;
		int liczbaPozostalychWezlow;
		private Graf graf;
		//parametr algorytmu Barabasi Alberta
		// m to sredni stopien wierzcholka w grafie
		private int m;
		
		@Override
		public void generujKrawedzie(Graf graf, int liczbaKrawedzi){
			this.graf = graf;
			liczbaWezlow = graf.getLiczbaWezlow();
			//na podstawie oczekiwanej liczby krawedzi wyliczam wartosc m 
			// to jest liczba krawedzi przypadajaca na 1 wierzcholek srednio
			//suma stopni wierzcholkow = l.wierzcholkow*2
			//sredni stopien = suma stopni / liczba wierzch
			
			liczbaWezlowWPodgrafie = (int)(liczbaWezlow * CZESC_DO_INICJALIZACJI);
			m = (int) Math.round( (double)liczbaKrawedzi / (liczbaWezlow - liczbaWezlowWPodgrafie) );	
			
			
			for(int aktualnieDodawany = liczbaWezlowWPodgrafie; aktualnieDodawany < liczbaWezlow; aktualnieDodawany++){
				int krawedzieDodaneDoNowegoWierzcholka = 0;
				while( krawedzieDodaneDoNowegoWierzcholka < m ){
					int gdzieSieDolaczyc = losujGdzieSiePrzylaczyc(aktualnieDodawany);
					if( !graf.czyPolaczone(aktualnieDodawany, gdzieSieDolaczyc) ){
						graf.dodajKrawedz(aktualnieDodawany, gdzieSieDolaczyc);
						krawedzieDodaneDoNowegoWierzcholka++;
					}
				}
			}

		}
		
		private int losujGdzieSiePrzylaczyc(int aktualnieDodawany){
			Random rand = new Random();
			double[] dystrybuanta = getDystrybuanta(aktualnieDodawany);
			double losowa = rand.nextDouble();
			for(int i = 0; i < dystrybuanta.length; i++){
				if( dystrybuanta[i] > losowa ){
					return i;
				}
			}
			return dystrybuanta.length - 1;
		}
		
		private double[] getDystrybuanta(int aktualnieDodawany){
			int liczbaWezlowWDotychczasWygenerowanymGrafie = aktualnieDodawany - 1;
			double[] rozklad = new double[liczbaWezlowWDotychczasWygenerowanymGrafie]; 
			int sumaStopniWierzcholkow = obliczSumeStopniWierzcholkow(liczbaWezlowWDotychczasWygenerowanymGrafie);
			for( int i = 0; i < liczbaWezlowWDotychczasWygenerowanymGrafie; i++ ){
				rozklad[i] = ((double)graf.getStopienWierzcholka(i) + 1) / (sumaStopniWierzcholkow + liczbaWezlowWDotychczasWygenerowanymGrafie);
			}
			double[] dystrybuanta = new double[liczbaWezlowWDotychczasWygenerowanymGrafie]; 
			dystrybuanta[0] = rozklad[0];
			for( int i = 1; i < liczbaWezlowWDotychczasWygenerowanymGrafie; i++ ){
				dystrybuanta[i] = dystrybuanta[i-1] + rozklad[i];
			}
			return dystrybuanta;
		}
		
		private int obliczSumeStopniWierzcholkow(int liczbaWierzcholkow){
			int suma = 0;
			for(int i = 0; i < liczbaWierzcholkow; i++){
				suma += graf.getStopienWierzcholka(i);
			}
			return suma;
		}


}
