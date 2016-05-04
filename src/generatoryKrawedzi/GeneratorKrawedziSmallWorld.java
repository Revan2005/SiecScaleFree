package generatoryKrawedzi;
import java.util.Random;

import glowny.Graf;

public class GeneratorKrawedziSmallWorld implements GeneratorKrawedzi{
	/*
	 * Na podstawie algorytmu Wattsa-Strogatza na poczatku kazdy jest polaczony z k najblizszymi sasiadami
	 * krawedzi jest n*k/2, gdzie n to liczba wierzcholkow grafu 
	 * tTo tez zrobie troszke inaczej zamiast wyznacac k czyli liczbe sasiadow dla kazdego wierzcholka z 
	 * ktorymi bedzie on polaczony na poczatku w sieci pierscieniowej, to bede szedl w kolko dodajac krawedzie 
	 * najpierw do najblizszczgo jak juz bedzie pelne kolko to 2 najblizszy itd, dzieki temu doloze dokladnie tyle krawedzi
	 * ile chce, bo w oryginale pojawialyby sie zaokraglenia przy wyliczaniu k i mialbym inna liczbe krawedzi
	 * niz chcialem. 
	 */
	private Graf graf;
	private int liczbaKrawedzi;
	private double prawdopodobienstwoPrzepiecia;

	public GeneratorKrawedziSmallWorld(double prawdopodobienstwoPrzepiecia){
		this.prawdopodobienstwoPrzepiecia = prawdopodobienstwoPrzepiecia;
	}
	
	@Override
	public void generujKrawedzie(Graf graf, int liczbaKrawedzi) {
		this.graf = graf;
		this.liczbaKrawedzi = liczbaKrawedzi;
		utworzGrafPierscieniowy();
		przepnijKrawedzie();
		//printujGraf();
	}
	
	private void printujGraf(){
		System.out.println("\nGRAF:\n=======================================================\n");
		for(int i=0; i<graf.getLiczbaWezlow(); i++){
			System.out.println( i + " " + graf.getListaSasiadowOsobnika(i) );
		}

	}
	
	private void utworzGrafPierscieniowy(){
		int dodaneKrawedzie = 0;
		int index = 0;
		int liczbaWierzcholkow = graf.getLiczbaWezlow();
		int odlegloscOdNajblizszegoSasiadaBezPolaczenia = 1;
		while(dodaneKrawedzie < liczbaKrawedzi){
			graf.dodajKrawedz(index, (index+odlegloscOdNajblizszegoSasiadaBezPolaczenia) % liczbaWierzcholkow);
			if( index == liczbaWierzcholkow - 1 ){
				index = (index+1) % liczbaWierzcholkow; //czyli = zero, przeszlismy petle to dodajemy 1 do najblizszego sasiada
				odlegloscOdNajblizszegoSasiadaBezPolaczenia++;
			} else {
				index++;
			}
			dodaneKrawedzie++;
		}
	}
	
	private void przepnijKrawedzie(){
		//trzeba rozwazyc kazda krawedz wiec zrobic liczbaKrawedzi krokow
		int liczbaKrokow = 0;
		int index = 0;
		int liczbaWierzcholkow = graf.getLiczbaWezlow();
		int odlegloscOdNajblizszegoNierozwazanegoSasiada = 1;
		Random rand = new Random();
		int nowyZnajomy;
		while(liczbaKrokow < liczbaKrawedzi){
			//System.out.println(rand.nextDouble()+"  "+prawdopodobienstwoPrzepiecia);
			if(rand.nextDouble() < prawdopodobienstwoPrzepiecia){
				//System.out.println("Przepinam!!!");
				graf.usunKrawedz(index, (index+odlegloscOdNajblizszegoNierozwazanegoSasiada) % liczbaWierzcholkow);
				nowyZnajomy = rand.nextInt(liczbaWierzcholkow);
				graf.dodajKrawedz(index, nowyZnajomy);
			}
			if( index == liczbaWierzcholkow - 1 ){
				index = (index+1) % liczbaWierzcholkow; // czyli = 0, zrobilismy petelke
				odlegloscOdNajblizszegoNierozwazanegoSasiada++;
			} else {
				index++;
			}
			liczbaKrokow++;
		}
	}

	private void printujStopnieWierzcholkow(){
		System.out.println("/n/nStopnie wierzchołków grafu:\n");
		for(int i=0; i<graf.getLiczbaWezlow(); i++){
			System.out.print(graf.getStopienWierzcholka(i) + ", ");
		}
		System.out.println("\n");
	}
	
	
}
