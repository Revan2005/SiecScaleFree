package generatoryKrawedzi;

import java.util.Arrays;
import java.util.Random;

import glowny.Graf;
import glowny.GrafListowy;
import glowny.TypSieci;

public class GeneratorKrawedziAutorskiScaleFree implements GeneratorKrawedzi {
	private Graf graf;
	private int liczbaKrawedzi;
	private double gamma;
	private int[] oczekiwanyStopienWierzcholka;
	private double[] rozkladPpbPrzylaczeniaKrawedzi;
	private double[] dystrybuantaPpbPrzylaczeniaKrawedzi;

	public GeneratorKrawedziAutorskiScaleFree(double gamma) {
		this.gamma = gamma;
	}
	
	@Override
	public void generujKrawedzie(Graf graf, int liczbaKrawedzi) {
		// TODO Auto-generated method stub
		this.graf = graf;
		this.liczbaKrawedzi = liczbaKrawedzi;
		
		przyporzadkujOczekiwaneStopnieWierzchokkowZRozkladuPotegowego();
		przyporzadkujPpbPrzylaczeniaKrawedzi();
		generujDystrybuantePpbPrzylaczeniaKrawedzi();
		
		int w1, w2;
		int dodaneKrawedzie = 0;
		while(dodaneKrawedzie < liczbaKrawedzi){
			w1 = losujWierzcholekDoPrzylaczenia();
			w2 = losujWierzcholekDoPrzylaczenia();
			if( (w1 != w2) && (!graf.czyPolaczone(w1, w2)) ){
				graf.dodajKrawedz(w1, w2);
				dodaneKrawedzie++;
			}
		}
		
		//System.out.println("\n\naaaaaaaaaaaaaaaaaaaaa\n\n\n");
		//System.out.println("hellol");
		//System.out.println(Arrays.toString(ppbPrzylaczeniaKrwedziDoWierzcholka));
		printujStopnieWierzcholkow();
		//printujGraf();
	}
	
	private void przyporzadkujOczekiwaneStopnieWierzchokkowZRozkladuPotegowego(){
		oczekiwanyStopienWierzcholka = new int[graf.getLiczbaWezlow()];
		int a = getPrzesuniecie();
		int b = graf.getLiczbaWezlow() - 1;
		RozkladPotegowy rozkladPotegowy = new RozkladPotegowy(gamma, a, b);
		for(int i = 0; i < oczekiwanyStopienWierzcholka.length; i++)
			oczekiwanyStopienWierzcholka[i] = rozkladPotegowy.losuj();	
	}
	
	private int getPrzesuniecie(){
		/*
		 * nazwa tez kiepska przyadloby sie lepsz wymyslic
		 * 
		 * zmienic!!!! to ma byc liczone na podstawie zadanej przez uzytkownika liczby krawedzi
		 */
		return 1;
	}
	
	private void przyporzadkujPpbPrzylaczeniaKrawedzi(){
		double suma = 0;
		for(int i=0; i<oczekiwanyStopienWierzcholka.length; i++){
			suma += oczekiwanyStopienWierzcholka[i];
		}
		rozkladPpbPrzylaczeniaKrawedzi = new double[graf.getLiczbaWezlow()];
		for(int i=0; i<oczekiwanyStopienWierzcholka.length; i++){
			rozkladPpbPrzylaczeniaKrawedzi[i] = (double)oczekiwanyStopienWierzcholka[i] / suma;
		}
	}
	
	private void generujDystrybuantePpbPrzylaczeniaKrawedzi(){
		dystrybuantaPpbPrzylaczeniaKrawedzi = new double[graf.getLiczbaWezlow()];
		dystrybuantaPpbPrzylaczeniaKrawedzi[0] = rozkladPpbPrzylaczeniaKrawedzi[0];
		for(int i=1; i<rozkladPpbPrzylaczeniaKrawedzi.length; i++){
			dystrybuantaPpbPrzylaczeniaKrawedzi[i] = dystrybuantaPpbPrzylaczeniaKrawedzi[i-1] + rozkladPpbPrzylaczeniaKrawedzi[i];
		}
	}
	
	private int losujWierzcholekDoPrzylaczenia(){
		int indexWierzcholka = graf.getLiczbaWezlow() - 1;
		Random random = new Random();
		double losowa = random.nextDouble();
		for(int i=0; i<dystrybuantaPpbPrzylaczeniaKrawedzi.length; i++){
			if(losowa < dystrybuantaPpbPrzylaczeniaKrawedzi[i]){
				indexWierzcholka = i;
				return indexWierzcholka;
			}
		}
		return indexWierzcholka;
	}
	
	private void printujGraf(){
		System.out.println("\nGRAF SCALE FREE WEDLUG MOJEGO ALGORYTMU:\n=======================================================\n");
		System.out.println("Gamma = " + gamma + "\n");
		for(int i=0; i<graf.getLiczbaWezlow(); i++){
			;//System.out.println( i + " " + graf.getListaSasiadowOsobnika(i) );
		}
	}
	
	private void printujStopnieWierzcholkow(){
		System.out.println("/n/nStopnie wierzchołków grafu:\n");
		System.out.println("Autorski scale free, gamma = " + gamma);
		for(int i=0; i<graf.getLiczbaWezlow(); i++){
			System.out.print(graf.getStopienWierzcholka(i) + ", ");
		}
		System.out.println("\n");
	}

}
