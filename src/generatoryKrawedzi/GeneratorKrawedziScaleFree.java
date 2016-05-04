package generatoryKrawedzi;
import java.util.Random;

import glowny.Graf;



public class GeneratorKrawedziScaleFree implements GeneratorKrawedzi{
		// czesc do nicjalizacji odpowiada parametrowi m0 algorytmu Barabasi Alberta
		//tam to jest jako liczba a nie procent i musi zachodzic m <= m0
		private final double CZESC_DO_INICJALIZACJI = 0.05;
		int liczbaWezlow;
		private int liczbaKrawedzi;
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
			this.liczbaKrawedzi = liczbaKrawedzi;
			//na podstawie oczekiwanej liczby krawedzi wyliczam wartosc m 
			// to jest liczba krawedzi przypadajaca na 1 wierzcholek srednio
			//suma stopni wierzcholkow = l.wierzcholkow*2
			//sredni stopien = suma stopni / liczba wierzch
			//liczba krawedzi a wiec i suma stopni wierzcholkow bedzie inna ni
			//ta podana jako parametr w panelu sterowania
			/*wynika to z tego ze oryginalny algorytm wymaga parametru m
			 * wyliczam go na podstawie oczekiwanej liczby krawedzi
			 * ale pojawiaja sie bledy zaokraglen
			 * ale to juz kwestia zgodnosci z oryginalnym algorytmem
			 * poprawiam to robiac sufit i pozniej usuwajac losowo nadmiarowe krawedzie
			 * 
			 */
			
			liczbaWezlowWPodgrafie = (int)(liczbaWezlow * CZESC_DO_INICJALIZACJI);
			// zrobie sufit z m zeby krawedzi byl zawsze nadmiar po czym bede mogl usunac losowo nadmiarowe krawedzie
			//dodawanie brakujacych wydaje mi sie zbyt ryzykowne, mogloby popsuc wlasnosc scale free sieci
			m = (int)Math.ceil( (double)liczbaKrawedzi / (liczbaWezlow - liczbaWezlowWPodgrafie) );
			//m = (int)Math.round( (double)liczbaKrawedzi / (liczbaWezlow - liczbaWezlowWPodgrafie) );
			//System.out.println("MMMMMMMMMMMMMMMMMMMM========"+m+"  liczbaWezlow - liczbaWezlowWPodgrafie = "+(liczbaWezlow - liczbaWezlowWPodgrafie));
			
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

			usunNadmiaroweKrawedzie();
			
			printujStopnieWierzcholkow();
		}
		
		private void usunNadmiaroweKrawedzie(){
			//usuwam losowo krawedzie az bedzie ich tyle ile podal uzytkownik
			//bez tego etapu
			//przy 10 tys wierzcholkow i 50 ty krawedzi krawedzi w rzeczywistosci bylo 
			// az o 4 tysiace za duzo 
			//liczbaKrawedzi to tutaj oczywiscie ZADANA liczba krawedzi (żądana, podana przez uzytkownika)
			int ileKrawedziNalezyUsunac = graf.getRzeczywistaLiczbaKrawedzi() - liczbaKrawedzi;
			Random random = new Random();
			int w1, w2;
			int licznik = 0;
			while(licznik < ileKrawedziNalezyUsunac){
				w1 = random.nextInt(graf.getLiczbaWezlow());
				w2 = random.nextInt(graf.getLiczbaWezlow());
				if(graf.czyPolaczone(w1, w2)){
					graf.usunKrawedz(w1, w2);
					licznik++;
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
		
		private void printujStopnieWierzcholkow(){
			System.out.println("/n/nStopnie wierzchołków grafu:\n");
			System.out.println("Oryginalny algorytm scale free, parametr m = " + m);
			for(int i=0; i<graf.getLiczbaWezlow(); i++){
				System.out.print(graf.getStopienWierzcholka(i) + ", ");
			}
			System.out.println("\n");
		}
}
