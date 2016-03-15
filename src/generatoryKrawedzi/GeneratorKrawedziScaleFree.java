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
			m = (int) Math.round( (double)liczbaKrawedzi / liczbaWezlow );	
			
		
			liczbaWezlowWPodgrafie = (int)(liczbaWezlow * CZESC_DO_INICJALIZACJI);
			liczbaPozostalychWezlow = (int)(liczbaWezlow * (1 - CZESC_DO_INICJALIZACJI));
			inicjalizujMalyPodGrafLosowy( (int)(liczbaKrawedzi * CZESC_DO_INICJALIZACJI) );
			//dodajPozostaleKrawedzie( (int)(liczbaKrawedzi * (1-CZESC_DO_INICJALIZACJI)) );
			dodajPozostaleKrawedzie();
		}
		
		private void inicjalizujMalyPodGrafLosowy(int liczbaKrawedzi){
			Random rand = new Random();
			int dodaneKrawedzie = 0;
			while( dodaneKrawedzie < liczbaKrawedzi ){
				int wezel1 = rand.nextInt(liczbaWezlowWPodgrafie);
				int wezel2 = rand.nextInt(liczbaWezlowWPodgrafie);
				if( !graf.czyPolaczone(wezel1, wezel2) ){
					graf.dodajKrawedz(wezel1, wezel2);
					dodaneKrawedzie++;
				}
			}
		}
		
		private void dodajPozostaleKrawedzie(){	
			// na razie najprosciej 
			/*ppb ze nowy jest polaczony z itym
			 * = stopien tego wierzcholka
			 * przez sume wszystkich stopni wierzcholkow
			 */
			for(int nowy = liczbaWezlowWPodgrafie; nowy < liczbaWezlow; nowy++){
				int krawedzieDodaneDoNowegoWierzcholka = 0;
				while( krawedzieDodaneDoNowegoWierzcholka < m ){
					int gdzieSieDolaczyc = losujGdzieSiePrzylaczyc();
					if( !graf.czyPolaczone(nowy, gdzieSieDolaczyc) ){
						graf.dodajKrawedz(nowy, gdzieSieDolaczyc);
						krawedzieDodaneDoNowegoWierzcholka++;
					}
				}
			}
		}
		
		private int losujGdzieSiePrzylaczyc(){
			Random rand = new Random();
			double[] dystrybuanta = getDystrybuanta();
			double losowa = rand.nextDouble();
			for(int i = 0; i < dystrybuanta.length; i++){
				if( dystrybuanta[i] > losowa ){
					return i;
				}
			}
			return dystrybuanta.length - 1;
		}
		
		private double[] getDystrybuanta(){
			double[] rozklad = new double[graf.getLiczbaWezlow()]; 
			int sumaStopniWierzcholkow = obliczSumeStopniWierzcholkow();
			for( int i = 0; i < graf.getLiczbaWezlow(); i++ ){
				rozklad[i] = (double)graf.getStopienWierzcholka(i) / sumaStopniWierzcholkow;
			}
			double[] dystrybuanta = new double[graf.getLiczbaWezlow()]; 
			dystrybuanta[0] = rozklad[0];
			for( int i = 1; i < graf.getLiczbaWezlow(); i++ ){
				dystrybuanta[i] = dystrybuanta[i-1] + rozklad[i];
			}
			return dystrybuanta;
		}
		
		private int obliczSumeStopniWierzcholkow(){
			int suma = 0;
			for(int i = 0; i < graf.getLiczbaWezlow(); i++){
				suma += graf.getStopienWierzcholka(i);
			}
			return suma;
		}


}
