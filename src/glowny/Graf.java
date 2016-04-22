package glowny;
import java.util.ArrayList;
import java.util.Random;




public abstract class Graf {
	protected TypSieci typSieci;
	protected int liczbaWezlow;
	protected int liczbaKrawedzi;
	protected ArrayList<Osobnik> listaOsobnikow;
	protected double ppbPrzepieciaSmallWorld_Hybrid;
	ParametryRozkladu parametryRozkladuPodatnosciNaInfekcje;
	
	public Graf(TypSieci typSieci, int liczbaWezlow, int liczbaKrawedzi, ParametryRozkladu parametryRozkladuPodatnosciNaInfekcje){
		this.typSieci = typSieci;
		this.liczbaWezlow = liczbaWezlow;
		this.liczbaKrawedzi = liczbaKrawedzi;
		this.parametryRozkladuPodatnosciNaInfekcje = parametryRozkladuPodatnosciNaInfekcje;
		inicjalizujListeOsobnikow();
		utworzKrawedzie();
	}
	
	public Graf(TypSieci typSieci, int liczbaWezlow, int liczbaKrawedzi, double ppbPrzepieciaSmallWorld_Hybrid, ParametryRozkladu parametryRozkladuPodatnosciNaInfekcje){
		this.typSieci = typSieci;
		this.liczbaWezlow = liczbaWezlow;
		this.liczbaKrawedzi = liczbaKrawedzi;
		this.ppbPrzepieciaSmallWorld_Hybrid = ppbPrzepieciaSmallWorld_Hybrid;
		this.parametryRozkladuPodatnosciNaInfekcje = parametryRozkladuPodatnosciNaInfekcje;
		inicjalizujListeOsobnikow();
		utworzKrawedzie();
	} 
	
	public void reset(){
		// wystarczy ustawic stan kazdego osobnika na ZDROWY, reszta pozostaje bez zmian, taka sama populacja tylko ze na nowo zdrowa i gotowa 
		// do kolejnego eksperymentu
		for(int i = 0; i < liczbaWezlow; i++)
			getOsobnik(i).reset();
	}
	
	protected abstract void utworzKrawedzie();
	
	protected void inicjalizujListeOsobnikow(){
		//===========================================================
		//tu tworze osobniki i tu moge ustalac ich odpornosc (pole podatnoscosobnikaNaInfekcje w klasie Osobnik)
		listaOsobnikow = new ArrayList<Osobnik>();
		Osobnik tworzonyOsobnik;
		for(int i=0; i<liczbaWezlow; i++){
			tworzonyOsobnik = stworzOsobnika();
			listaOsobnikow.add(tworzonyOsobnik);
		}
	}
	
	protected Osobnik stworzOsobnika(){
		Random random = new Random();
		double sigma = parametryRozkladuPodatnosciNaInfekcje.odchylenieStandardowe;
		double mi = parametryRozkladuPodatnosciNaInfekcje.srednia;
		//w 2 kolejnych liniach zabezpieczam przed wyjsciem poza zakres [0,1], 
		//wczesniej przycinalem ale teraz losuje od nowa jesli wypadnie poza zakres
		//bo przy przycinaniu masa prawdopodobienstwa z "ogonkow" przechodzila na breg (tzn kazde wylosowanie powyzej jedynki dawalo jedynke)
		//i przez to jedynka byla bardziej prawdopodobna niz na przyklad 0.99
		double wspolczynnikPodatnosciNaInfekcje = -1; 
		while( (wspolczynnikPodatnosciNaInfekcje < 0) || (wspolczynnikPodatnosciNaInfekcje > 1) )
				wspolczynnikPodatnosciNaInfekcje = (sigma * random.nextGaussian()) + mi;
		//System.out.println("wspolczynnikPodatnosci = " + wspolczynnikPodatnosciNaInfekcje + " srednia = "+mi+"  odchylenie = "+sigma + "  to jest printowane w klasie Grf");
		return new Osobnik(StanOsobnika.ZDROWY, wspolczynnikPodatnosciNaInfekcje);
	}
	
	public abstract int[] getTablicaStopniWierzcholkow();
	
	public int getRzeczywistaLiczbaKrawedzi(){
		int[] tablicaStopniWierzcholkow = getTablicaStopniWierzcholkow();
		int sumaStopniWierzcholkow = 0;
		for(int i = 0; i < tablicaStopniWierzcholkow.length; i++){
			sumaStopniWierzcholkow += tablicaStopniWierzcholkow[i];
		}
		int rzeczywistaLiczbaKrawedzi = sumaStopniWierzcholkow / 2;
		return rzeczywistaLiczbaKrawedzi;
	}
	
	public int getLiczbaChorych(){
		int liczbaChorych = 0;
		for(int i=0; i<liczbaWezlow; i++){
			if(getStanZdrowiaOsobnika(i).equals(StanOsobnika.CHORY))
				liczbaChorych++;
		}
		return liczbaChorych;
	}
	
	public int getLiczbaZdrowych(){
		int liczbaZdrowych = 0;
		for(int i=0; i<liczbaWezlow; i++){
			if(getStanZdrowiaOsobnika(i).equals(StanOsobnika.ZDROWY))
				liczbaZdrowych++;
		}
		return liczbaZdrowych;
	}
	
	public int getLiczbaOdpornych(){
		int liczbaOdpornych = 0;
		for(int i=0; i<liczbaWezlow; i++){
			if(getStanZdrowiaOsobnika(i).equals(StanOsobnika.ODPORNY))
				liczbaOdpornych++;
		}
		return liczbaOdpornych;
	}
	
	public Osobnik getOsobnik(int indexOsobnika){
		return listaOsobnikow.get(indexOsobnika);
	}
	
	public abstract ArrayList<Integer> getListaSasiadowOsobnika(int indexOsobnika);
	
	public abstract boolean czyPolaczone(int wezel1, int wezel2);
	
	public StanOsobnika getStanZdrowiaOsobnika(int wezel){
		return listaOsobnikow.get(wezel).getStan();
	}
	
	public void setStanZdrowiaOsobnika(int wezel, StanOsobnika stanZdrowia){
		listaOsobnikow.get(wezel).setStan(stanZdrowia);
	}
	
	public abstract int getStopienWierzcholka(int wezel);
	
	public int getLiczbaWezlow(){
		return liczbaWezlow;
	}
	
	public abstract void dodajKrawedz(int wezel1, int wezel2);
	
	public abstract int usunKrawedz(int wezel1, int wezel2);


}
