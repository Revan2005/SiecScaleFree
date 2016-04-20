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
		double wspolczynnikPodatnosciNaInfekcje = (sigma * random.nextGaussian()) + mi;
		//System.out.println("wspolczynnikPodatnosci = " + wspolczynnikPodatnosciNaInfekcje + " srednia = "+mi+"  odchylenie = "+sigma + "  to jest printowane w klasie Grf");
		return new Osobnik(StanOsobnika.ZDROWY, wspolczynnikPodatnosciNaInfekcje);
	}
	
	public abstract int[] getTablicaStopniWierzcholkow();
	
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
