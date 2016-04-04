package glowny;
import java.util.Arrays;
import plot.*;

public class PanelSterowania {
	private static Graf graf;
	private static int liczbaOsobnikow;
	private static int liczbaKrawedzi;
	private static int poczatkowaLiczbaChorych;
	private static TypSieci typSieci;
	private static int liczbaZaszczepionych;
	private static StrategiaSzczepienia strategiaSzczepienia;
	private static double prawdopodobienstwoZarazenia;
	private static int czasTrwaniaChorobyWDniach;
	private static int liczbaDni;
	
	public static void main(String[] args) {
		
		/* w klasie GrafListowy zmiana w metodzie getstopnieWierzcholka
		 * kiedy dodalem 1 to wykres chorych sie znacznie splaszczyl - podoba mi sie 
		 * przypomina chyba bardziej rzeczywiste
		 */
		liczbaOsobnikow = 10000;
		liczbaKrawedzi = 50000; //kazdy ma srednio liczbaKrawedzi*2 / liczbaWierzholkow polaczen
		poczatkowaLiczbaChorych = 10;
		typSieci = TypSieci.SCALE_FREE;
		//listowy dziala znacznie szybciej niz macierzowy nie ma sensu uzywac macierzowej implementacji
		liczbaZaszczepionych = 200;
		//strategiaSzczepienia = StrategiaSzczepienia.LOSOWE;
		//strategiaSzczepienia = StrategiaSzczepienia.OSOBNIKI_Z_NAJWYZSZYM_STOPNIEM;
		//strategiaSzczepienia = StrategiaSzczepienia.WSKAZ_LOSOWEGO_ZNAJOMEGO;
		strategiaSzczepienia = StrategiaSzczepienia.WSKAZ_ZNAJOMEGO_Z_NAJWYZSZYM_STOPNIEM;
		/* to ostatnie rozni sie znacznie od strategii zaszczeposobnikiznajwyzszymstopniem
		 * bo tu nie wykorzystujemy wiedzy a priori o topologii sieci
		 * tylko pozyskujemy ja tak jak to mozna zrobic w zyciu
		 * dzwonimy po ludziach i pytamy o popularnych znajomych :D
		 */
		
		prawdopodobienstwoZarazenia = 0.02;
		czasTrwaniaChorobyWDniach = 7;
		liczbaDni = 100;
		
		/*
		for(double i=0.010; i<0.021; i+=0.001){
			prawdopodobienstwoZarazenia = i;
			System.out.println("Prawdopodobieństwo zarażenia = " + prawdopodobienstwoZarazenia);
			powtorzEpidemieNRazy( 100 );
			System.out.println("\n\n");
		}
		*/
		
		powtorzEpidemieNRazyZPrintowaniemIWyswietlaniemWykresow(1);
		
	}
	
	public static void powtorzEpidemieNRazyZPrintowaniemIWyswietlaniemWykresow(int n){
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
