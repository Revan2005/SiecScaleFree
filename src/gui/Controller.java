package gui;

import glowny.PanelSterowania;
import glowny.StrategiaSzczepienia;
import glowny.TypSieci;

public class Controller {

	/*
	public static void main(String[] args) {
		int liczbaOsobnikow = 10000;
		int liczbaKrawedzi = 50000; //kazdy ma srednio liczbaKrawedzi*2 / liczbaWierzholkow polaczen
		int poczatkowaLiczbaChorych = 10;
		
		//typSieci = TypSieci.RANDOM;
		TypSieci typSieci = TypSieci.SCALE_FREE;
		//TypSieci typSieci = TypSieci.SMALL_WORLD; // kiedy wybieram smallworld to musze dopisac
		//do konstruktora GrafListowy parametr odpowiedzialny za ppb przepiecia
		double ppbPrzepieciaSmallWorld = 0.3;
		
		//listowy dziala znacznie szybciej niz macierzowy nie ma sensu uzywac macierzowej implementacji
		int liczbaZaszczepionych = 200;
		StrategiaSzczepienia strategiaSzczepienia = StrategiaSzczepienia.LOSOWE;
		//strategiaSzczepienia = StrategiaSzczepienia.OSOBNIKI_Z_NAJWYZSZYM_STOPNIEM;
		//strategiaSzczepienia = StrategiaSzczepienia.WSKAZ_LOSOWEGO_ZNAJOMEGO;
		//strategiaSzczepienia = StrategiaSzczepienia.WSKAZ_ZNAJOMEGO_Z_NAJWYZSZYM_STOPNIEM;
		//to ostatnie rozni sie znacznie od strategii zaszczeposobnikiznajwyzszymstopniem
		// bo tu nie wykorzystujemy wiedzy a priori o topologii sieci
		// tylko pozyskujemy ja tak jak to mozna zrobic w zyciu
		// dzwonimy po ludziach i pytamy o popularnych znajomych :D
		 //
		double prawdopodobienstwoZarazenia = 0.02;
		int czasTrwaniaChorobyWDniach = 7;
		int liczbaDni = 100;
		
		//METAPARAMETRY
		int liczbaPowtorzenEpidemii = 3;
		boolean wyswietlacWykresy = false;
		boolean wyswietlacSzczegoly = false;
		
		
		PanelSterowania.uruchomZParametrami(
				typSieci,
				ppbPrzepieciaSmallWorld,
				liczbaOsobnikow,
				liczbaKrawedzi,
				poczatkowaLiczbaChorych,
				liczbaZaszczepionych,
				strategiaSzczepienia,
				prawdopodobienstwoZarazenia,
				czasTrwaniaChorobyWDniach,
				liczbaDni,
				liczbaPowtorzenEpidemii,
				wyswietlacWykresy,
				wyswietlacSzczegoly
				);
	}
	*/
	
	public static void start(TypSieci typSieci,
			double ppbPrzepieciaSmallWorld,
			int liczbaOsobnikow, 
			int liczbaKrawedzi, 
			int poczatkowaLiczbaChorych,
			int liczbaZaszczepionych,
			StrategiaSzczepienia strategiaSzczepienia,
			double prawdopodobienstwoZarazenia,
			int czasTrwaniaChorobyWDniach,
			int liczbaDni,
			int liczbaPowtorzenEpidemii,
			boolean wyswietlacWykresy,
			boolean wyswietlacSzczegoly){
		PanelSterowania.uruchomZParametrami(
				typSieci,
				ppbPrzepieciaSmallWorld,
				liczbaOsobnikow,
				liczbaKrawedzi,
				poczatkowaLiczbaChorych,
				liczbaZaszczepionych,
				strategiaSzczepienia,
				prawdopodobienstwoZarazenia,
				czasTrwaniaChorobyWDniach,
				liczbaDni,
				liczbaPowtorzenEpidemii,
				wyswietlacWykresy,
				wyswietlacSzczegoly
				);
	}

}
