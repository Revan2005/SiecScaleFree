package generatoryKrawedzi;

import java.util.Random;

import glowny.Graf;

public class GeneratorKrawedziHybrid implements GeneratorKrawedzi{

	/*
	 * Połączenie Small World i Scale Free, 
	 * algorytm: taki jak small world z tym ze ppb przy przepieciu przepiecia DO ntego wierzcholka zalezy od jego stopnia
	 */
	private Graf graf;
	private int liczbaKrawedzi;
	private double prawdopodobienstwoPrzepiecia;

	public GeneratorKrawedziHybrid(double prawdopodobienstwoPrzepiecia){
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
		System.out.println("\nGRAF HYBRYDOWY:\n=======================================================\n");
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
			if(rand.nextDouble() < prawdopodobienstwoPrzepiecia){
				//System.out.println("Przepinam!!!");
				graf.usunKrawedz(index, (index+odlegloscOdNajblizszegoNierozwazanegoSasiada) % liczbaWierzcholkow);
				nowyZnajomy = wylosujDoKogoSiePrzepiac(index);
				graf.dodajKrawedz(index, nowyZnajomy);
				//System.out.println("Przepinam sasiada "+index+" z "+(index+odlegloscOdNajblizszegoNierozwazanegoSasiada) % liczbaWierzcholkow+ "  do  "+nowyZnajomy);
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
	
	private int wylosujDoKogoSiePrzepiac(int index){
		// index - index osobnika ktory bedzie sie laczyl z innym, tzn ten koniec krawedzi przepinanej ktora pozostaje niezmieniona
		Random rand = new Random();
		double[] dystrybuanta = getDystrybuanta(index);
		double losowa = rand.nextDouble();
		for(int i = 0; i < dystrybuanta.length; i++){
			if( dystrybuanta[i] > losowa ){
				return i;
			}
		}
		return dystrybuanta.length - 1;
	}
	
	private double[] getDystrybuanta(int index){
		int liczbaWierzcholkow = graf.getLiczbaWezlow();
		double[] rozklad = new double[liczbaWierzcholkow - 1]; //tak zeby nie bylo krawedzi zwrotnej
		int sumaStopniWierzcholkow = obliczSumeStopniWierzcholkow(index);
		for( int i = 0; i < liczbaWierzcholkow - 1; i++ ){
			if(i != index)
				rozklad[i] = ((double)graf.getStopienWierzcholka(i) + 1) / (sumaStopniWierzcholkow + liczbaWierzcholkow - 1);
		}
		double[] dystrybuanta = new double[liczbaWierzcholkow - 1]; 
		dystrybuanta[0] = rozklad[0];
		for( int i = 1; i < liczbaWierzcholkow - 1; i++ ){
			dystrybuanta[i] = dystrybuanta[i-1] + rozklad[i];
		}
		return dystrybuanta;
	}
	
	private int obliczSumeStopniWierzcholkow(int index){
		int suma = 0;
		for(int i = 0; i < graf.getLiczbaWezlow(); i++){
			if(i != index)
				suma += graf.getStopienWierzcholka(i);
		}
		return suma;
	}

}
