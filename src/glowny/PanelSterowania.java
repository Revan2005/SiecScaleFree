package glowny;
import java.util.Arrays;
import plot.*;

public class PanelSterowania {
	private static Graf graf;
	private static TypSieci typSieci;
	private static double ppbPrzepieciaSmallWorld;
	private static int liczbaOsobnikow;
	private static int liczbaKrawedzi;
	private static int poczatkowaLiczbaChorych;
	private static int liczbaZaszczepionych;
	private static StrategiaSzczepienia strategiaSzczepienia;
	private static double prawdopodobienstwoZarazenia;
	private static int czasTrwaniaChorobyWDniach;
	private static int liczbaDni;
	
	
	public static void uruchomZParametrami( TypSieci _typSieci,
											int _liczbaOsobnikow, 
											int _liczbaKrawedzi, 
											int _poczatkowaLiczbaChorych,
											int _liczbaZaszczepionych,
											StrategiaSzczepienia _strategiaSzczepienia,
											double _prawdopodobienstwoZarazenia,
											int _czasTrwaniaChorobyWDniach,
											int _liczbaDni,
											int _liczbaPowtorzenEpidemii,
											boolean _wyswietlacWykresy,
											boolean _wyswietlacSzczegoly ) {

		typSieci = _typSieci; // kiedy wybieram smallworld to musze dopisac
		//do konstruktora GrafListowy parametr odpowiedzialny za ppb przepiecia
		liczbaOsobnikow = _liczbaOsobnikow;
		liczbaKrawedzi = _liczbaKrawedzi; //kazdy ma srednio liczbaKrawedzi*2 / liczbaWierzholkow polaczen
		poczatkowaLiczbaChorych = _poczatkowaLiczbaChorych;
		//implementacja listowa dziala znacznie szybciej niz macierzowy nie ma sensu uzywac macierzowej implementacji
		liczbaZaszczepionych = _liczbaZaszczepionych;
		strategiaSzczepienia = _strategiaSzczepienia;
		prawdopodobienstwoZarazenia = _prawdopodobienstwoZarazenia;
		czasTrwaniaChorobyWDniach = _czasTrwaniaChorobyWDniach;
		liczbaDni = _liczbaDni;
		if(_wyswietlacWykresy){
			powtorzEpidemieNRazyZPrintowaniemIWyswietlaniemWykresow(_liczbaPowtorzenEpidemii);
		} else {
			if(_wyswietlacSzczegoly){
				powtorzEpidemieNRazyZPrintowaniem(_liczbaPowtorzenEpidemii);
			} else {
				powtorzEpidemieNRazy(_liczbaPowtorzenEpidemii);
			}
		}
	}
	
	public static void uruchomZParametrami( TypSieci _typSieci,
											double _ppbPrzepieciaSmallWorld,
											int _liczbaOsobnikow, 
											int _liczbaKrawedzi, 
											int _poczatkowaLiczbaChorych,
											int _liczbaZaszczepionych,
											StrategiaSzczepienia _strategiaSzczepienia,
											double _prawdopodobienstwoZarazenia,
											int _czasTrwaniaChorobyWDniach,
											int _liczbaDni,
											int _liczbaPowtorzenEpidemii,
											boolean _wyswietlacWykresy,
											boolean _wyswietlacSzczegoly ) {
		typSieci = _typSieci; // kiedy wybieram smallworld to musze dopisac
		//do konstruktora GrafListowy parametr odpowiedzialny za ppb przepiecia
		ppbPrzepieciaSmallWorld = _ppbPrzepieciaSmallWorld;
		liczbaOsobnikow = _liczbaOsobnikow;
		liczbaKrawedzi = _liczbaKrawedzi; //kazdy ma srednio liczbaKrawedzi*2 / liczbaWierzholkow polaczen
		poczatkowaLiczbaChorych = _poczatkowaLiczbaChorych;
		liczbaZaszczepionych = _liczbaZaszczepionych;
		strategiaSzczepienia = _strategiaSzczepienia;
		prawdopodobienstwoZarazenia = _prawdopodobienstwoZarazenia;
		czasTrwaniaChorobyWDniach = _czasTrwaniaChorobyWDniach;
		liczbaDni = _liczbaDni;

		if(_wyswietlacWykresy){
			powtorzEpidemieNRazyZPrintowaniemIWyswietlaniemWykresow(_liczbaPowtorzenEpidemii);
		} else {
			if(_wyswietlacSzczegoly){
				powtorzEpidemieNRazyZPrintowaniem(_liczbaPowtorzenEpidemii);
			} else {
				powtorzEpidemieNRazy(_liczbaPowtorzenEpidemii);
			}
		}
	}
	
	public static void powtorzEpidemieNRazyZPrintowaniemIWyswietlaniemWykresow(int n){
		double[] frakcjeChorychWKolejnychSymulacjach = new double[n];
		
		for(int i=0; i<n; i++){
			if(typSieci == TypSieci.SMALL_WORLD)
				graf = new GrafListowy(typSieci, liczbaOsobnikow, liczbaKrawedzi, ppbPrzepieciaSmallWorld);
			else
				graf = new GrafListowy(typSieci, liczbaOsobnikow, liczbaKrawedzi);
			ModelSzczepienia.zaszczep(strategiaSzczepienia, graf,  liczbaZaszczepionych);
			
			int sumaStopniWierzcholkow = 0;
			int[] tablicaStopniWierzcholkow = graf.getTablicaStopniWierzcholkow();
			for(int j=0; j<tablicaStopniWierzcholkow.length; j++)
				sumaStopniWierzcholkow += tablicaStopniWierzcholkow[j];
			System.out.println("sumaStopnie = "+sumaStopniWierzcholkow+"   liczbaWierzhcolkow ="+tablicaStopniWierzcholkow.length);
			//for(int i=0; i<100; i++)
			//	System.out.println( graf.getListaSasiadowOsobnika(i) );
			
			
			Epidemia epidemia = new Epidemia(graf, poczatkowaLiczbaChorych, prawdopodobienstwoZarazenia, czasTrwaniaChorobyWDniach);
			
			epidemia.start(liczbaDni);
			System.out.println( Arrays.toString(epidemia.getLiczbyChorychKazdegoDnia()) );
			System.out.println( Arrays.toString(epidemia.getLiczbyZdrowychKazdegoDnia()) );
			System.out.println( Arrays.toString(epidemia.getLiczbyOdpornychKazdegoDnia()) );
			System.out.println( Arrays.toString(epidemia.getZachorowalnoscKazdegoDnia()) );
			
			//Plotter.plot(epidemia.getLiczbyZdrowychKazdegoDnia(), epidemia.getLiczbyChorychKazdegoDnia(), epidemia.getLiczbyOdpornychKazdegoDnia());
			Plotter.plot(
					epidemia.getLiczbyZdrowychKazdegoDnia(), 
					epidemia.getLiczbyChorychKazdegoDnia(), 
					epidemia.getLiczbyOdpornychKazdegoDnia(),
					epidemia.getZachorowalnoscKazdegoDnia() );

			int liczbaOsobnikowKtorePrzeszlyChorobe = epidemia.getLiczbyZdrowychKazdegoDnia()[0] - epidemia.getLiczbyZdrowychKazdegoDnia()[liczbaDni-1] + poczatkowaLiczbaChorych;
			double frakcjaOsobnikowKtorePrzeszlyChorobe = ((double)liczbaOsobnikowKtorePrzeszlyChorobe/liczbaOsobnikow);
			System.out.println(frakcjaOsobnikowKtorePrzeszlyChorobe);
			frakcjeChorychWKolejnychSymulacjach[i] = frakcjaOsobnikowKtorePrzeszlyChorobe;
		}
		
		System.out.println( "frakcjeChorychWKolejnychSymulacjach: \n" + 
							Arrays.toString(frakcjeChorychWKolejnychSymulacjach) );
	}
	
	public static void powtorzEpidemieNRazyZPrintowaniem(int n){
		double[] frakcjeChorychWKolejnychSymulacjach = new double[n];
		
		for(int i=0; i<n; i++){
			graf = new GrafListowy(typSieci, liczbaOsobnikow, liczbaKrawedzi);
			
			ModelSzczepienia.zaszczep(strategiaSzczepienia, graf,  liczbaZaszczepionych);
			
			int sumaStopniWierzcholkow = 0;
			int[] tablicaStopniWierzcholkow = graf.getTablicaStopniWierzcholkow();
			for(int j=0; j<tablicaStopniWierzcholkow.length; j++)
				sumaStopniWierzcholkow += tablicaStopniWierzcholkow[j];
			System.out.println("sumaStopnie = "+sumaStopniWierzcholkow+"   liczbaWierzhcolkow ="+tablicaStopniWierzcholkow.length);
			//for(int i=0; i<100; i++)
			//	System.out.println( graf.getListaSasiadowOsobnika(i) );
			
			
			Epidemia epidemia = new Epidemia(graf, poczatkowaLiczbaChorych, prawdopodobienstwoZarazenia, czasTrwaniaChorobyWDniach);
			
			epidemia.start(liczbaDni);
			System.out.println( Arrays.toString(epidemia.getLiczbyChorychKazdegoDnia()) );
			System.out.println( Arrays.toString(epidemia.getLiczbyZdrowychKazdegoDnia()) );
			System.out.println( Arrays.toString(epidemia.getLiczbyOdpornychKazdegoDnia()) );
			System.out.println( Arrays.toString(epidemia.getZachorowalnoscKazdegoDnia()) );
			
			//Plotter.plot(epidemia.getLiczbyZdrowychKazdegoDnia(), epidemia.getLiczbyChorychKazdegoDnia(), epidemia.getLiczbyOdpornychKazdegoDnia());
			//Plotter.plot(
			//		epidemia.getLiczbyZdrowychKazdegoDnia(), 
			//		epidemia.getLiczbyChorychKazdegoDnia(), 
			//		epidemia.getLiczbyOdpornychKazdegoDnia(),
			//		epidemia.getZachorowalnoscKazdegoDnia() );

			int liczbaOsobnikowKtorePrzeszlyChorobe = epidemia.getLiczbyZdrowychKazdegoDnia()[0] - epidemia.getLiczbyZdrowychKazdegoDnia()[liczbaDni-1] + poczatkowaLiczbaChorych;
			double frakcjaOsobnikowKtorePrzeszlyChorobe = ((double)liczbaOsobnikowKtorePrzeszlyChorobe/liczbaOsobnikow);
			System.out.println(frakcjaOsobnikowKtorePrzeszlyChorobe);
			frakcjeChorychWKolejnychSymulacjach[i] = frakcjaOsobnikowKtorePrzeszlyChorobe;
		}
		
		System.out.println( "frakcjeChorychWKolejnychSymulacjach: \n" + 
							Arrays.toString(frakcjeChorychWKolejnychSymulacjach) );
	}
	
	public static void powtorzEpidemieNRazy(int n){
		double[] frakcjeChorychWKolejnychSymulacjach = new double[n];
		
		for(int i=0; i<n; i++){
			graf = new GrafListowy(typSieci, liczbaOsobnikow, liczbaKrawedzi);
			
			ModelSzczepienia.zaszczep(strategiaSzczepienia, graf,  liczbaZaszczepionych);
			
			int sumaStopniWierzcholkow = 0;
			int[] tablicaStopniWierzcholkow = graf.getTablicaStopniWierzcholkow();
			for(int j=0; j<tablicaStopniWierzcholkow.length; j++)
				sumaStopniWierzcholkow += tablicaStopniWierzcholkow[j];
			//System.out.println("sumaStopnie = "+sumaStopniWierzcholkow+"   liczbaWierzhcolkow ="+tablicaStopniWierzcholkow.length);
			//for(int i=0; i<100; i++)
			//	System.out.println( graf.getListaSasiadowOsobnika(i) );
			
			
			Epidemia epidemia = new Epidemia(graf, poczatkowaLiczbaChorych, prawdopodobienstwoZarazenia, czasTrwaniaChorobyWDniach);
			
			epidemia.start(liczbaDni);
			//System.out.println( Arrays.toString(epidemia.getLiczbyChorychKazdegoDnia()) );
			//System.out.println( Arrays.toString(epidemia.getLiczbyZdrowychKazdegoDnia()) );
			//System.out.println( Arrays.toString(epidemia.getLiczbyOdpornychKazdegoDnia()) );
			//System.out.println( Arrays.toString(epidemia.getZachorowalnoscKazdegoDnia()) );
			
			//Plotter.plot(epidemia.getLiczbyZdrowychKazdegoDnia(), epidemia.getLiczbyChorychKazdegoDnia(), epidemia.getLiczbyOdpornychKazdegoDnia());
			//Plotter.plot(
			//		epidemia.getLiczbyZdrowychKazdegoDnia(), 
			//		epidemia.getLiczbyChorychKazdegoDnia(), 
			//		epidemia.getLiczbyOdpornychKazdegoDnia(),
			//		epidemia.getZachorowalnoscKazdegoDnia() );

			int liczbaOsobnikowKtorePrzeszlyChorobe = epidemia.getLiczbyZdrowychKazdegoDnia()[0] - epidemia.getLiczbyZdrowychKazdegoDnia()[liczbaDni-1] + poczatkowaLiczbaChorych;
			//int liczbaOsobnikowKtorePrzeszlyChorobe2 = epidemia.getLiczbyOdpornychKazdegoDnia()[liczbaDni-1] + epidemia.getLiczbyChorychKazdegoDnia()[liczbaDni-1] - liczbaZaszczepionych;
			//int sumaZachorowan = 0;
			//for(int k=0; k<epidemia.getZachorowalnoscKazdegoDnia().length; k++)
			//	sumaZachorowan += epidemia.getZachorowalnoscKazdegoDnia()[k];
			//System.out.println(liczbaOsobnikowKtorePrzeszlyChorobe+"  ||  " + liczbaOsobnikowKtorePrzeszlyChorobe2 +
			//		"  ||  "+sumaZachorowan);
			double frakcjaOsobnikowKtorePrzeszlyChorobe = ((double)liczbaOsobnikowKtorePrzeszlyChorobe/liczbaOsobnikow);
			//System.out.println(frakcjaOsobnikowKtorePrzeszlyChorobe);
			frakcjeChorychWKolejnychSymulacjach[i] = frakcjaOsobnikowKtorePrzeszlyChorobe;
		}
		
		System.out.println( "frakcjeChorychWKolejnychSymulacjach: \n" + 
							Arrays.toString(frakcjeChorychWKolejnychSymulacjach) );
	}

}
