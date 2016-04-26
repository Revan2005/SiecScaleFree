package glowny;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	private static double zakaznoscPatogenu;
	private static ParametryRozkladu parametryRozkladuPodatnosciNaInfekcje;
	private static int czasTrwaniaChorobyWDniach;
	private static int liczbaDni;
	private static int liczbaPowtorzenEpidemii;
	private static boolean wszystkieSymulacjeNaJednymGrafie;
	private static double sredniaRzeczywistaLiczbaKrawedzi;
	
	public static void uruchomZParametrami( TypSieci _typSieci,
											int _liczbaOsobnikow, 
											int _liczbaKrawedzi, 
											int _poczatkowaLiczbaChorych,
											int _liczbaZaszczepionych,
											StrategiaSzczepienia _strategiaSzczepienia,
											double _zakaznoscPatogenu,
											ParametryRozkladu _parametryRozkladuPodatnosciNaInfekcje,
											int _czasTrwaniaChorobyWDniach,
											int _liczbaDni,
											int _liczbaPowtorzenEpidemii,
											boolean _wszystkieSymulacjeNaJednymGrafie) {

		typSieci = _typSieci; // kiedy wybieram smallworld to musze dopisac
		//do konstruktora GrafListowy parametr odpowiedzialny za ppb przepiecia
		liczbaOsobnikow = _liczbaOsobnikow;
		liczbaKrawedzi = _liczbaKrawedzi; //kazdy ma srednio liczbaKrawedzi*2 / liczbaWierzholkow polaczen
		poczatkowaLiczbaChorych = _poczatkowaLiczbaChorych;
		//implementacja listowa dziala znacznie szybciej niz macierzowy nie ma sensu uzywac macierzowej implementacji
		liczbaZaszczepionych = _liczbaZaszczepionych;
		strategiaSzczepienia = _strategiaSzczepienia;
		zakaznoscPatogenu = _zakaznoscPatogenu;
		parametryRozkladuPodatnosciNaInfekcje = _parametryRozkladuPodatnosciNaInfekcje;
		czasTrwaniaChorobyWDniach = _czasTrwaniaChorobyWDniach;
		liczbaDni = _liczbaDni;
		liczbaPowtorzenEpidemii = _liczbaPowtorzenEpidemii;
		wszystkieSymulacjeNaJednymGrafie = _wszystkieSymulacjeNaJednymGrafie;
		
		if(_wszystkieSymulacjeNaJednymGrafie){
			powtorzEpidemieNRazyNaJednymGrafie(_liczbaPowtorzenEpidemii);
		} else {
			powtorzEpidemieNRazyZaKazdymRazemTworzacNowyGraf(_liczbaPowtorzenEpidemii);
		}
	}
	
	public static void uruchomZParametrami( TypSieci _typSieci,
											double _ppbPrzepieciaSmallWorld,
											int _liczbaOsobnikow, 
											int _liczbaKrawedzi, 
											int _poczatkowaLiczbaChorych,
											int _liczbaZaszczepionych,
											StrategiaSzczepienia _strategiaSzczepienia,
											double _zakaznoscPatogenu,
											ParametryRozkladu _parametryRozkladuPodatnosciNaInfekcje,
											int _czasTrwaniaChorobyWDniach,
											int _liczbaDni,
											int _liczbaPowtorzenEpidemii,
											boolean _wszystkieSymulacjeNaJednymGrafie) {
		typSieci = _typSieci; // kiedy wybieram smallworld to musze dopisac
		//do konstruktora GrafListowy parametr odpowiedzialny za ppb przepiecia
		ppbPrzepieciaSmallWorld = _ppbPrzepieciaSmallWorld;
		liczbaOsobnikow = _liczbaOsobnikow;
		liczbaKrawedzi = _liczbaKrawedzi; //kazdy ma srednio liczbaKrawedzi*2 / liczbaWierzholkow polaczen
		poczatkowaLiczbaChorych = _poczatkowaLiczbaChorych;
		liczbaZaszczepionych = _liczbaZaszczepionych;
		strategiaSzczepienia = _strategiaSzczepienia;
		zakaznoscPatogenu = _zakaznoscPatogenu;
		parametryRozkladuPodatnosciNaInfekcje = _parametryRozkladuPodatnosciNaInfekcje;
		czasTrwaniaChorobyWDniach = _czasTrwaniaChorobyWDniach;
		liczbaDni = _liczbaDni;
		liczbaPowtorzenEpidemii = _liczbaPowtorzenEpidemii;
		wszystkieSymulacjeNaJednymGrafie = _wszystkieSymulacjeNaJednymGrafie;
		
		if(_wszystkieSymulacjeNaJednymGrafie){
			powtorzEpidemieNRazyNaJednymGrafie(_liczbaPowtorzenEpidemii);
		} else {
			powtorzEpidemieNRazyZaKazdymRazemTworzacNowyGraf(_liczbaPowtorzenEpidemii);
		}
	}
	
	public static void powtorzEpidemieNRazyNaJednymGrafie(int liczbaSymulacji){		
		double[] frakcjeChorychWKolejnychSymulacjach = new double[liczbaSymulacji];
		int[][] wynikiNSymulacjiLiczbyChorychKazdegoDnia = new int[liczbaSymulacji][liczbaDni];
		int[][] wynikiNSymulacjiLiczbyZdrowychKazdegoDnia = new int[liczbaSymulacji][liczbaDni];
		int[][] wynikiNSymulacjiLiczbyOdpornychKazdegoDnia = new int[liczbaSymulacji][liczbaDni];
		int[][] wynikiNSymulacjiZachorowalnoscKazdegoDnia = new int[liczbaSymulacji][liczbaDni];
		
		//tworze graf, a w kolejnych iteracjach bede go resetowal, topologia bedize stala w obrebie eksperymentu (wszystkich symulacji)
		if( (typSieci == TypSieci.SMALL_WORLD) || (typSieci == TypSieci.HYBRID) )
			graf = new GrafListowy(typSieci, liczbaOsobnikow, liczbaKrawedzi, ppbPrzepieciaSmallWorld, parametryRozkladuPodatnosciNaInfekcje);
		else
			graf = new GrafListowy(typSieci, liczbaOsobnikow, liczbaKrawedzi, parametryRozkladuPodatnosciNaInfekcje);
		sredniaRzeczywistaLiczbaKrawedzi = graf.getRzeczywistaLiczbaKrawedzi();
		
		for(int i=0; i<liczbaSymulacji; i++){
			//tu miejsce na zresetowanie grafu
			graf.reset(); //reset pozostawia topologie bez zmian ale ustawia wszsytkie osobniki na zdrowe/podatne

			ModelSzczepienia.zaszczep(strategiaSzczepienia, graf,  liczbaZaszczepionych);
			
			int sumaStopniWierzcholkow = 0;
			int[] tablicaStopniWierzcholkow = graf.getTablicaStopniWierzcholkow();
			for(int j=0; j<tablicaStopniWierzcholkow.length; j++)
				sumaStopniWierzcholkow += tablicaStopniWierzcholkow[j];
			System.out.println("sumaStopnie = "+sumaStopniWierzcholkow+"   liczbaWierzhcolkow ="+tablicaStopniWierzcholkow.length);
			//for(int j=0; j<10; j++)
			//	System.out.println( graf.getListaSasiadowOsobnika(j) );
			
			//System.out.println("To wchodzi do epidemii: chorzy: "+graf.getLiczbaChorych()+", zdrowi: "+graf.getLiczbaZdrowych()+" odporni: "+graf.getLiczbaOdpornych());
			Epidemia epidemia = new Epidemia(graf, poczatkowaLiczbaChorych, zakaznoscPatogenu, czasTrwaniaChorobyWDniach);
			
			epidemia.start(liczbaDni);
			//System.out.println( Arrays.toString(epidemia.getLiczbyChorychKazdegoDnia()) );
			//System.out.println( Arrays.toString(epidemia.getLiczbyZdrowychKazdegoDnia()) );
			//System.out.println( Arrays.toString(epidemia.getLiczbyOdpornychKazdegoDnia()) );
			//System.out.println( Arrays.toString(epidemia.getZachorowalnoscKazdegoDnia()) );
			wynikiNSymulacjiLiczbyChorychKazdegoDnia[i] = epidemia.getLiczbyChorychKazdegoDnia();
			//System.out.println(Arrays.toString(wynikiNSymulacjiLiczbyChorychKazdegoDnia[i]));
			wynikiNSymulacjiLiczbyZdrowychKazdegoDnia[i] = epidemia.getLiczbyZdrowychKazdegoDnia();
			wynikiNSymulacjiLiczbyOdpornychKazdegoDnia[i] = epidemia.getLiczbyOdpornychKazdegoDnia();
			wynikiNSymulacjiZachorowalnoscKazdegoDnia[i] = epidemia.getZachorowalnoscKazdegoDnia();
			
			//Plotter.plot(epidemia.getLiczbyZdrowychKazdegoDnia(), epidemia.getLiczbyChorychKazdegoDnia(), epidemia.getLiczbyOdpornychKazdegoDnia());
			/*Plotter.plot(
					epidemia.getLiczbyZdrowychKazdegoDnia(), 
					epidemia.getLiczbyChorychKazdegoDnia(), 
					epidemia.getLiczbyOdpornychKazdegoDnia(),
					epidemia.getZachorowalnoscKazdegoDnia() );
			*/
			int liczbaOsobnikowKtorePrzeszlyChorobe = epidemia.getLiczbyZdrowychKazdegoDnia()[0] - epidemia.getLiczbyZdrowychKazdegoDnia()[liczbaDni-1] + poczatkowaLiczbaChorych;
			double frakcjaOsobnikowKtorePrzeszlyChorobe = ((double)liczbaOsobnikowKtorePrzeszlyChorobe/liczbaOsobnikow);
			//System.out.println(frakcjaOsobnikowKtorePrzeszlyChorobe);
			frakcjeChorychWKolejnychSymulacjach[i] = frakcjaOsobnikowKtorePrzeszlyChorobe;
		}

		
		wyswietlWykres(
				frakcjeChorychWKolejnychSymulacjach, 
				wynikiNSymulacjiLiczbyChorychKazdegoDnia, 
				wynikiNSymulacjiLiczbyOdpornychKazdegoDnia, 
				wynikiNSymulacjiLiczbyZdrowychKazdegoDnia, 
				wynikiNSymulacjiZachorowalnoscKazdegoDnia);

		printujWyniki(
				frakcjeChorychWKolejnychSymulacjach, 
				wynikiNSymulacjiLiczbyChorychKazdegoDnia, 
				wynikiNSymulacjiLiczbyOdpornychKazdegoDnia, 
				wynikiNSymulacjiLiczbyZdrowychKazdegoDnia, 
				wynikiNSymulacjiZachorowalnoscKazdegoDnia);
		
		zapiszDoPliku(
				frakcjeChorychWKolejnychSymulacjach, 
				wynikiNSymulacjiLiczbyChorychKazdegoDnia, 
				wynikiNSymulacjiLiczbyOdpornychKazdegoDnia, 
				wynikiNSymulacjiLiczbyZdrowychKazdegoDnia, 
				wynikiNSymulacjiZachorowalnoscKazdegoDnia);
	}
	
	public static void powtorzEpidemieNRazyZaKazdymRazemTworzacNowyGraf(int liczbaSymulacji){		
		double[] frakcjeChorychWKolejnychSymulacjach = new double[liczbaSymulacji];
		int[][] wynikiNSymulacjiLiczbyChorychKazdegoDnia = new int[liczbaSymulacji][liczbaDni];
		int[][] wynikiNSymulacjiLiczbyZdrowychKazdegoDnia = new int[liczbaSymulacji][liczbaDni];
		int[][] wynikiNSymulacjiLiczbyOdpornychKazdegoDnia = new int[liczbaSymulacji][liczbaDni];
		int[][] wynikiNSymulacjiZachorowalnoscKazdegoDnia = new int[liczbaSymulacji][liczbaDni];
		sredniaRzeczywistaLiczbaKrawedzi = 0;
		
		for(int i=0; i<liczbaSymulacji; i++){
			if( (typSieci == TypSieci.SMALL_WORLD) || (typSieci == TypSieci.HYBRID) )
				graf = new GrafListowy(typSieci, liczbaOsobnikow, liczbaKrawedzi, ppbPrzepieciaSmallWorld, parametryRozkladuPodatnosciNaInfekcje);
			else
				graf = new GrafListowy(typSieci, liczbaOsobnikow, liczbaKrawedzi, parametryRozkladuPodatnosciNaInfekcje);
			ModelSzczepienia.zaszczep(strategiaSzczepienia, graf,  liczbaZaszczepionych);
			
			sredniaRzeczywistaLiczbaKrawedzi += graf.getRzeczywistaLiczbaKrawedzi();
			
			int sumaStopniWierzcholkow = 0;
			int[] tablicaStopniWierzcholkow = graf.getTablicaStopniWierzcholkow();
			for(int j=0; j<tablicaStopniWierzcholkow.length; j++)
				sumaStopniWierzcholkow += tablicaStopniWierzcholkow[j];
			System.out.println("sumaStopnie = "+sumaStopniWierzcholkow+"   liczbaWierzhcolkow ="+tablicaStopniWierzcholkow.length);
			//for(int j=0; j<10; j++)
			//	System.out.println( graf.getListaSasiadowOsobnika(j) );
			
			//System.out.println("To wchodzi do epidemii: chorzy: "+graf.getLiczbaChorych()+", zdrowi: "+graf.getLiczbaZdrowych()+" odporni: "+graf.getLiczbaOdpornych());
			Epidemia epidemia = new Epidemia(graf, poczatkowaLiczbaChorych, zakaznoscPatogenu, czasTrwaniaChorobyWDniach);
			
			epidemia.start(liczbaDni);
			//System.out.println( Arrays.toString(epidemia.getLiczbyChorychKazdegoDnia()) );
			//System.out.println( Arrays.toString(epidemia.getLiczbyZdrowychKazdegoDnia()) );
			//System.out.println( Arrays.toString(epidemia.getLiczbyOdpornychKazdegoDnia()) );
			//System.out.println( Arrays.toString(epidemia.getZachorowalnoscKazdegoDnia()) );
			wynikiNSymulacjiLiczbyChorychKazdegoDnia[i] = epidemia.getLiczbyChorychKazdegoDnia();
			wynikiNSymulacjiLiczbyZdrowychKazdegoDnia[i] = epidemia.getLiczbyZdrowychKazdegoDnia();
			wynikiNSymulacjiLiczbyOdpornychKazdegoDnia[i] = epidemia.getLiczbyOdpornychKazdegoDnia();
			wynikiNSymulacjiZachorowalnoscKazdegoDnia[i] = epidemia.getZachorowalnoscKazdegoDnia();
			
			//Plotter.plot(epidemia.getLiczbyZdrowychKazdegoDnia(), epidemia.getLiczbyChorychKazdegoDnia(), epidemia.getLiczbyOdpornychKazdegoDnia());
			/*Plotter.plot(
					epidemia.getLiczbyZdrowychKazdegoDnia(), 
					epidemia.getLiczbyChorychKazdegoDnia(), 
					epidemia.getLiczbyOdpornychKazdegoDnia(),
					epidemia.getZachorowalnoscKazdegoDnia() );
			*/
			int liczbaOsobnikowKtorePrzeszlyChorobe = epidemia.getLiczbyZdrowychKazdegoDnia()[0] - epidemia.getLiczbyZdrowychKazdegoDnia()[liczbaDni-1] + poczatkowaLiczbaChorych;
			double frakcjaOsobnikowKtorePrzeszlyChorobe = ((double)liczbaOsobnikowKtorePrzeszlyChorobe/liczbaOsobnikow);
			//System.out.println(frakcjaOsobnikowKtorePrzeszlyChorobe);
			frakcjeChorychWKolejnychSymulacjach[i] = frakcjaOsobnikowKtorePrzeszlyChorobe;
			
			//================================================================================
			//double frakcjaLiczonaInaczej = 0;
			//for(int k=0; k<epidemia.getZachorowalnoscKazdegoDnia().length; k++){
			//	frakcjaLiczonaInaczej += epidemia.getZachorowalnoscKazdegoDnia()[k];
			//}
			//frakcjaLiczonaInaczej /= epidemia.populacja.getLiczbaWezlow();
			//System.out.println("Frakcja chorych w epidemii: "+i+" = "+frakcjaLiczonaInaczej);
			//================================================================================
		}
		sredniaRzeczywistaLiczbaKrawedzi /= liczbaSymulacji;
		
		wyswietlWykres(
				frakcjeChorychWKolejnychSymulacjach, 
				wynikiNSymulacjiLiczbyChorychKazdegoDnia, 
				wynikiNSymulacjiLiczbyOdpornychKazdegoDnia, 
				wynikiNSymulacjiLiczbyZdrowychKazdegoDnia, 
				wynikiNSymulacjiZachorowalnoscKazdegoDnia);

		printujWyniki(
				frakcjeChorychWKolejnychSymulacjach, 
				wynikiNSymulacjiLiczbyChorychKazdegoDnia, 
				wynikiNSymulacjiLiczbyOdpornychKazdegoDnia, 
				wynikiNSymulacjiLiczbyZdrowychKazdegoDnia, 
				wynikiNSymulacjiZachorowalnoscKazdegoDnia);
		
		zapiszDoPliku(
				frakcjeChorychWKolejnychSymulacjach, 
				wynikiNSymulacjiLiczbyChorychKazdegoDnia, 
				wynikiNSymulacjiLiczbyOdpornychKazdegoDnia, 
				wynikiNSymulacjiLiczbyZdrowychKazdegoDnia, 
				wynikiNSymulacjiZachorowalnoscKazdegoDnia);
	}
	
	private static void printujWyniki(
			double[] frakcjeChorychWKolejnychSymulacjach,
			int[][] wynikiNSymulacjiLiczbyChorychKazdegoDnia, 
			int[][] wynikiNSymulacjiLiczbyOdpornychKazdegoDnia, 
			int[][] wynikiNSymulacjiLiczbyZdrowychKazdegoDnia, 
			int[][] wynikiNSymulacjiZachorowalnoscKazdegoDnia){
		double[] sredniaZNSymulacjiLiczbyChorychKazdegoDnia = obliczTabliceSrednich(wynikiNSymulacjiLiczbyChorychKazdegoDnia);
		double[] sredniaZNSymulacjiLiczbyZdrowychKazdegoDnia = obliczTabliceSrednich(wynikiNSymulacjiLiczbyZdrowychKazdegoDnia);
		double[] sredniaZNSymulacjiLiczbyOdpornychKazdegoDnia = obliczTabliceSrednich(wynikiNSymulacjiLiczbyOdpornychKazdegoDnia);
		double[] sredniaZNSymulacjiZachorowalnoscKazdegoDnia = obliczTabliceSrednich(wynikiNSymulacjiZachorowalnoscKazdegoDnia);
		
		double[] odchylenieStandardoweZNSymulacjiLiczbyChorychKazdegoDnia = obliczTabliceOdchylenStandardowych(wynikiNSymulacjiLiczbyChorychKazdegoDnia);
		double[] odchylenieStandardoweZNSymulacjiLiczbyZdrowychKazdegoDnia = obliczTabliceOdchylenStandardowych(wynikiNSymulacjiLiczbyZdrowychKazdegoDnia);
		double[] odchylenieStandardoweZNSymulacjiLiczbyOdpornychKazdegoDnia = obliczTabliceOdchylenStandardowych(wynikiNSymulacjiLiczbyOdpornychKazdegoDnia);
		double[] odchylenieStandardoweZNSymulacjiZachorowalnoscKazdegoDnia = obliczTabliceOdchylenStandardowych(wynikiNSymulacjiZachorowalnoscKazdegoDnia);
		
		System.out.println( "I srednia: " + Arrays.toString(sredniaZNSymulacjiLiczbyChorychKazdegoDnia) );
		System.out.println( "I odch. st.: " + Arrays.toString(odchylenieStandardoweZNSymulacjiLiczbyChorychKazdegoDnia) );
		System.out.println( "S srednia: " + Arrays.toString(sredniaZNSymulacjiLiczbyZdrowychKazdegoDnia) );
		System.out.println( "S odch. st.: " + Arrays.toString(odchylenieStandardoweZNSymulacjiLiczbyZdrowychKazdegoDnia) );
		System.out.println( "R srednia: " + Arrays.toString(sredniaZNSymulacjiLiczbyOdpornychKazdegoDnia) );
		System.out.println( "R odch. st.: " + Arrays.toString(odchylenieStandardoweZNSymulacjiLiczbyOdpornychKazdegoDnia) );
		System.out.println( "Z srednia: " + Arrays.toString(sredniaZNSymulacjiZachorowalnoscKazdegoDnia) );
		System.out.println( "Z odch. st.: " + Arrays.toString(odchylenieStandardoweZNSymulacjiZachorowalnoscKazdegoDnia) );
		
		System.out.println( "frakcjeChorychWKolejnychSymulacjach: \n" + 
							Arrays.toString(frakcjeChorychWKolejnychSymulacjach) );
		double sredniaZFrakcjiChorychWKolejnychSymulacjach = obliczSredniaZTablicy(frakcjeChorychWKolejnychSymulacjach);
		double odchylenieStandardoweZFrakcjiChorychWKolejnychSymulacjach = obliczOdchylenieStandardoweZTablicy(frakcjeChorychWKolejnychSymulacjach);
		System.out.println("Srednia ze wszsytkich symulacji frakcja chorych = " + sredniaZFrakcjiChorychWKolejnychSymulacjach);
		System.out.println("Odch. st. frakcji chorych po symulacjach = " + odchylenieStandardoweZFrakcjiChorychWKolejnychSymulacjach);
	}
	
	private static void wyswietlWykres(
			double[] frakcjeChorychWKolejnychSymulacjach,
			int[][] wynikiNSymulacjiLiczbyChorychKazdegoDnia, 
			int[][] wynikiNSymulacjiLiczbyOdpornychKazdegoDnia, 
			int[][] wynikiNSymulacjiLiczbyZdrowychKazdegoDnia, 
			int[][] wynikiNSymulacjiZachorowalnoscKazdegoDnia){
		double[] sredniaZNSymulacjiLiczbyChorychKazdegoDnia = obliczTabliceSrednich(wynikiNSymulacjiLiczbyChorychKazdegoDnia);
		double[] sredniaZNSymulacjiLiczbyZdrowychKazdegoDnia = obliczTabliceSrednich(wynikiNSymulacjiLiczbyZdrowychKazdegoDnia);
		double[] sredniaZNSymulacjiLiczbyOdpornychKazdegoDnia = obliczTabliceSrednich(wynikiNSymulacjiLiczbyOdpornychKazdegoDnia);
		double[] sredniaZNSymulacjiZachorowalnoscKazdegoDnia = obliczTabliceSrednich(wynikiNSymulacjiZachorowalnoscKazdegoDnia);
		Plotter.plot(
				sredniaZNSymulacjiLiczbyZdrowychKazdegoDnia, 
				sredniaZNSymulacjiLiczbyChorychKazdegoDnia, 
				sredniaZNSymulacjiLiczbyOdpornychKazdegoDnia,
				sredniaZNSymulacjiZachorowalnoscKazdegoDnia );
	}
	
	private static double[] obliczTabliceSrednich(int[][] wynikiNSymulacji){
		int liczbaSymulacji = wynikiNSymulacji.length;
		double[] tablicaSrednich = new double[liczbaDni];
		for(int dzien = 0; dzien < liczbaDni; dzien++){
			for(int symulacja = 0; symulacja < liczbaSymulacji; symulacja++){
				tablicaSrednich[dzien] += wynikiNSymulacji[symulacja][dzien];
			}
			tablicaSrednich[dzien] = (double)tablicaSrednich[dzien] / liczbaSymulacji;
		}
		return tablicaSrednich;
	}
	
	private static double[] obliczTabliceOdchylenStandardowych(int[][] wynikiNSymulacji){
		int liczbaSymulacji = wynikiNSymulacji.length;
		double[] tablicaSrednich = obliczTabliceSrednich(wynikiNSymulacji);
		double wariancja = 0;
		double[] tablicaOdchylenStandardowych = new double[liczbaDni];
		double[] tablicaSumKwadratowOdleglosciOdSredniej = new double[liczbaDni];
		for(int dzien = 0; dzien < liczbaDni; dzien++){
			for(int symulacja = 0; symulacja < liczbaSymulacji; symulacja++){
				tablicaSumKwadratowOdleglosciOdSredniej[dzien] += Math.pow((wynikiNSymulacji[symulacja][dzien] - tablicaSrednich[dzien]), 2);
			}
			wariancja = tablicaSumKwadratowOdleglosciOdSredniej[dzien] / (liczbaSymulacji - 1);
			tablicaOdchylenStandardowych[dzien] = Math.sqrt(wariancja);
		}
		return tablicaOdchylenStandardowych;
	}
	
	private static void zapiszDoPliku(
			double[] frakcjeChorychWKolejnychSymulacjach,
			int[][] wynikiNSymulacjiLiczbyChorychKazdegoDnia, 
			int[][] wynikiNSymulacjiLiczbyOdpornychKazdegoDnia, 
			int[][] wynikiNSymulacjiLiczbyZdrowychKazdegoDnia, 
			int[][] wynikiNSymulacjiZachorowalnoscKazdegoDnia){
		double[] sredniaZNSymulacjiLiczbyChorychKazdegoDnia = obliczTabliceSrednich(wynikiNSymulacjiLiczbyChorychKazdegoDnia);
		double[] sredniaZNSymulacjiLiczbyZdrowychKazdegoDnia = obliczTabliceSrednich(wynikiNSymulacjiLiczbyZdrowychKazdegoDnia);
		double[] sredniaZNSymulacjiLiczbyOdpornychKazdegoDnia = obliczTabliceSrednich(wynikiNSymulacjiLiczbyOdpornychKazdegoDnia);
		double[] sredniaZNSymulacjiZachorowalnoscKazdegoDnia = obliczTabliceSrednich(wynikiNSymulacjiZachorowalnoscKazdegoDnia);
		
		double[] odchylenieStandardoweZNSymulacjiLiczbyChorychKazdegoDnia = obliczTabliceOdchylenStandardowych(wynikiNSymulacjiLiczbyChorychKazdegoDnia);
		double[] odchylenieStandardoweZNSymulacjiLiczbyZdrowychKazdegoDnia = obliczTabliceOdchylenStandardowych(wynikiNSymulacjiLiczbyZdrowychKazdegoDnia);
		double[] odchylenieStandardoweZNSymulacjiLiczbyOdpornychKazdegoDnia = obliczTabliceOdchylenStandardowych(wynikiNSymulacjiLiczbyOdpornychKazdegoDnia);
		double[] odchylenieStandardoweZNSymulacjiZachorowalnoscKazdegoDnia = obliczTabliceOdchylenStandardowych(wynikiNSymulacjiZachorowalnoscKazdegoDnia);
		
		String sciezka = "/home/tomek/workspace/MGR/ModelowanieEpidemii/wyniki_eksperymentow/GNUPLOT/WLASCIWY/";
		File sOutputFile = new File(sciezka + "s.dat");
		File iOutputFile = new File(sciezka + "i.dat");
		File rOutputFile = new File(sciezka + "r.dat");
		File zOutputFile = new File(sciezka + "z.dat");
		try {
			FileWriter sWriter = new FileWriter(sOutputFile);
			FileWriter iWriter = new FileWriter(iOutputFile);
			FileWriter rWriter = new FileWriter(rOutputFile);
			FileWriter zWriter = new FileWriter(zOutputFile);
			zapiszDoPlikuSrednieIOdchylenia(sWriter, sredniaZNSymulacjiLiczbyZdrowychKazdegoDnia, odchylenieStandardoweZNSymulacjiLiczbyZdrowychKazdegoDnia);
			zapiszDoPlikuSrednieIOdchylenia(iWriter, sredniaZNSymulacjiLiczbyChorychKazdegoDnia, odchylenieStandardoweZNSymulacjiLiczbyChorychKazdegoDnia);
			zapiszDoPlikuSrednieIOdchylenia(rWriter, sredniaZNSymulacjiLiczbyOdpornychKazdegoDnia, odchylenieStandardoweZNSymulacjiLiczbyOdpornychKazdegoDnia);
			zapiszDoPlikuSrednieIOdchylenia(zWriter, sredniaZNSymulacjiZachorowalnoscKazdegoDnia, odchylenieStandardoweZNSymulacjiZachorowalnoscKazdegoDnia);
			sWriter.close();
			iWriter.close();
			rWriter.close();
			zWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		zapiszDoPlikuFrakcjeChorychWKolejnychSymulacjach(sciezka, frakcjeChorychWKolejnychSymulacjach);
		zapiszDoPlikuParametrySymulacji(sciezka);
		uruchomSkryptTworzacyWykresyWKatalogu(sciezka);
	}
	
	private static void zapiszDoPlikuFrakcjeChorychWKolejnychSymulacjach(String sciezka, double[] frakcjeChorychWKolejnychSymulacjach){
		File frakcjeChorychOutputFile = new File(sciezka + "wykresy/frakcjeChorychWKolejnychSymulacjach.txt");
		try {
			FileWriter frakcjeChorychWriter = new FileWriter(frakcjeChorychOutputFile);
			frakcjeChorychWriter.write("FrakcjeChorychWKolejnychSymulacjach: \n" + 
					Arrays.toString(frakcjeChorychWKolejnychSymulacjach) );
			double sredniaZFrakcjiChorychWKolejnychSymulacjach = obliczSredniaZTablicy(frakcjeChorychWKolejnychSymulacjach);
			double odchylenieStandardoweZFrakcjiChorychWKolejnychSymulacjach = obliczOdchylenieStandardoweZTablicy(frakcjeChorychWKolejnychSymulacjach);
			frakcjeChorychWriter.write("\n\nŚrednia = " + sredniaZFrakcjiChorychWKolejnychSymulacjach + "\n");
			frakcjeChorychWriter.write("Odchylenie stndardowe = " + odchylenieStandardoweZFrakcjiChorychWKolejnychSymulacjach + "\n\n");
			
			frakcjeChorychWriter.write(sredniaZFrakcjiChorychWKolejnychSymulacjach + "\n");
			frakcjeChorychWriter.write(odchylenieStandardoweZFrakcjiChorychWKolejnychSymulacjach + "\n\n\n");
			
			frakcjeChorychWriter.write(sredniaZFrakcjiChorychWKolejnychSymulacjach + " " + odchylenieStandardoweZFrakcjiChorychWKolejnychSymulacjach + "\n");
			frakcjeChorychWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void uruchomSkryptTworzacyWykresyWKatalogu(String sciezka){
		try {
			Runtime.getRuntime().exec(sciezka + "generujWykresy.sh");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void zapiszDoPlikuSrednieIOdchylenia(FileWriter writer, double[] tablicaSrednich, double[] tablicaOdchylen) throws IOException{
		writer.write("dzien srednia odchylenie");
		for(int i = 0; i < tablicaSrednich.length; i++){
			writer.write("\n" + (i+1) + " " + tablicaSrednich[i] + " " + tablicaOdchylen[i]);
		}
	}
	
	private static void zapiszDoPlikuParametrySymulacji(String sciezka){		
		File parametryOutputFile = new File(sciezka + "wykresy/parametry.txt");
		try {
			FileWriter parametryWriter = new FileWriter(parametryOutputFile);
			parametryWriter.write("Parametry:\n");
			parametryWriter.write("Liczba symulacji: " + liczbaPowtorzenEpidemii + "\n");
			if(wszystkieSymulacjeNaJednymGrafie)
				parametryWriter.write("Każda symulacje uruchomiona na takim samym grafie (topologia sieci stała w czasie eksperymentu)\n");
			else
				parametryWriter.write("Przed każdą symulacją losowany nowy graf (parametry sieci stałe, ale topologia sieci zmienna) \n");
			parametryWriter.write("Typ sieci: " + typSieci + "\n");
			if( (typSieci == TypSieci.SMALL_WORLD) || (typSieci == TypSieci.HYBRID) )
				parametryWriter.write("Prawdopodobieństwo przepięcia krawędzi podczas generowania grafu: " + ppbPrzepieciaSmallWorld + "\n");
			parametryWriter.write("Liczba wierzchołków w grafie: " + liczbaOsobnikow + "\n");
			parametryWriter.write("Żądana iczba krawędzi grafu: " + liczbaKrawedzi + "\n");
			if(wszystkieSymulacjeNaJednymGrafie){
				parametryWriter.write("Rzeczywista liczba krawędzi grafu (różnica wynika z własnośc zastosowanego algorytmu): "
						+ graf.getRzeczywistaLiczbaKrawedzi());
				double blad = (double)Math.abs(liczbaKrawedzi - graf.getRzeczywistaLiczbaKrawedzi()) / liczbaKrawedzi;
				blad *= 100;
				parametryWriter.write(" (błąd: " + blad + "%)" + "\n");
				double sredniStopienWierzcholkaWGrafie = ((double)graf.getRzeczywistaLiczbaKrawedzi() * 2) / liczbaOsobnikow; // sumaStopni wierzcholkow to 2* liczba krawedzi, zeby uzyskac srednia nalezy podzielic sume przez liczbe wierzcholkow
				parametryWriter.write("Średni stopień wierzchołka w grafie: " + sredniStopienWierzcholkaWGrafie + "\n");
			} else {
				parametryWriter.write("Średnia rzeczywista liczba krawędzi grafu (różnica wynika z własności zastosowanego algorytmu): "
						+ sredniaRzeczywistaLiczbaKrawedzi);
				double blad = (double)Math.abs(liczbaKrawedzi - sredniaRzeczywistaLiczbaKrawedzi) / liczbaKrawedzi;
				blad *= 100;
				parametryWriter.write(" (błąd: " + blad + "%)" + "\n");
				double sredniStopienWierzcholkaWGrafie = ((double)sredniaRzeczywistaLiczbaKrawedzi * 2) / liczbaOsobnikow; // sumaStopni wierzcholkow to 2* liczba krawedzi, zeby uzyskac srednia nalezy podzielic sume przez liczbe wierzcholkow
				parametryWriter.write("Średni stopień wierzchołka w grafie (uśredniony po wierzchołkach i symulacjach): " + sredniStopienWierzcholkaWGrafie + "\n");
			}
			parametryWriter.write("Początkowa liczba chorych: " + poczatkowaLiczbaChorych + "\n");
			parametryWriter.write("Strategia szczepienia: " + strategiaSzczepienia + "\n");
			parametryWriter.write("Liczba zaszczepionych: " + liczbaZaszczepionych + "\n");
			parametryWriter.write("Współczynnik zakaźności patogenu: " + zakaznoscPatogenu + "\n");
			parametryWriter.write("Współczynnik podatności na infekcje: \n");
			parametryWriter.write("	- średnia: " + parametryRozkladuPodatnosciNaInfekcje.srednia + "\n");
			parametryWriter.write("	- odchylenie standardowe: " + parametryRozkladuPodatnosciNaInfekcje.odchylenieStandardowe + "\n");
			parametryWriter.write("Czas trwania choroby: " + czasTrwaniaChorobyWDniach + " dni" + "\n");
			parametryWriter.write("Czas trwania symulacji: " + liczbaDni + " dni" + "\n");
			parametryWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	private static int[] dodajTabliceJakWektory(int[] dotychczasowaSuma, int[] wynikKolejnejSymulacji){
		//zakladam ze obie tablice sa tej samej dlugosci
		//dodaje tak jak przy dodawaniu wektorow, odpowiadajace sobie skladowe dodaje do siebie
		int[] result = new int[dotychczasowaSuma.length];
		for(int i=0; i<dotychczasowaSuma.length; i++){
			result[i] = dotychczasowaSuma[i] + wynikKolejnejSymulacji[i];
		}
		return result;
	}
	
	
	private static double[] podzielTablicePrzezSkalar(int[] tablica, int skalar){
		//dzieli jednowymiarowa tablice przez skalar tak jak sie mnozy/dzieli 
		//wektor przez skalar - tzn. dziele kazda skladowa wektora przez ten skalar
		double[] wynik = new double[tablica.length];
		for(int i=0; i<tablica.length; i++){
			wynik[i] = (double)tablica[i] / skalar; 
		}
		return wynik;
	}
	*/
	
	private static double obliczSredniaZTablicy(double[] t){
		double sum = 0;
		for(int i=0; i<t.length; i++){
			sum += t[i];
		}
		double result = sum / t.length;
		return result;
	}
	
	private static double obliczOdchylenieStandardoweZTablicy(double[] t){
		double srednia = obliczSredniaZTablicy(t);
		double sumaKwadratowOdleglosciOdSredniej = 0;
		for(int i=0; i<t.length; i++){
			sumaKwadratowOdleglosciOdSredniej += Math.pow((t[i]-srednia), 2);
		}
		double wariancja = sumaKwadratowOdleglosciOdSredniej / (t.length - 1);
		double odchylenieStandardowe = Math.sqrt(wariancja);
		return odchylenieStandardowe;
	}
	
	public static void powtorzEpidemieNRazyZPrintowaniem(int n){
		
	}
	/*
	public static void powtorzEpidemieNRazyZPrintowaniem(int n){
		double[] frakcjeChorychWKolejnychSymulacjach = new double[n];
		
		for(int i=0; i<n; i++){
			graf = new GrafListowy(typSieci, liczbaOsobnikow, liczbaKrawedzi, parametryRozkladuPodatnosciNaInfekcje);
			
			ModelSzczepienia.zaszczep(strategiaSzczepienia, graf,  liczbaZaszczepionych);
			
			int sumaStopniWierzcholkow = 0;
			int[] tablicaStopniWierzcholkow = graf.getTablicaStopniWierzcholkow();
			for(int j=0; j<tablicaStopniWierzcholkow.length; j++)
				sumaStopniWierzcholkow += tablicaStopniWierzcholkow[j];
			System.out.println("sumaStopnie = "+sumaStopniWierzcholkow+"   liczbaWierzhcolkow ="+tablicaStopniWierzcholkow.length);
			//for(int i=0; i<100; i++)
			//	System.out.println( graf.getListaSasiadowOsobnika(i) );
			
			
			Epidemia epidemia = new Epidemia(graf, poczatkowaLiczbaChorych, zakaznoscPatogenu, czasTrwaniaChorobyWDniach);
			
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
	*/
	
	public static void powtorzEpidemieNRazy(int n){
		
	}
	
	/*
	public static void powtorzEpidemieNRazy(int n){
		double[] frakcjeChorychWKolejnychSymulacjach = new double[n];
		
		for(int i=0; i<n; i++){
			graf = new GrafListowy(typSieci, liczbaOsobnikow, liczbaKrawedzi, parametryRozkladuPodatnosciNaInfekcje);
			
			ModelSzczepienia.zaszczep(strategiaSzczepienia, graf,  liczbaZaszczepionych);
			
			int sumaStopniWierzcholkow = 0;
			int[] tablicaStopniWierzcholkow = graf.getTablicaStopniWierzcholkow();
			for(int j=0; j<tablicaStopniWierzcholkow.length; j++)
				sumaStopniWierzcholkow += tablicaStopniWierzcholkow[j];
			//System.out.println("sumaStopnie = "+sumaStopniWierzcholkow+"   liczbaWierzhcolkow ="+tablicaStopniWierzcholkow.length);
			//for(int i=0; i<100; i++)
			//	System.out.println( graf.getListaSasiadowOsobnika(i) );
			
			
			Epidemia epidemia = new Epidemia(graf, poczatkowaLiczbaChorych, zakaznoscPatogenu, czasTrwaniaChorobyWDniach);
			
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
	*/

}
