package glowny;
import java.util.Arrays;
import plot.*;

public class PanelSterowania {
	private static Graf graf;
	
	public static void main(String[] args) {
		
		/* w klasie GrafListowy zmiana w metodzie getstopnieWierzcholka
		 * kiedy dodalem 1 to wykres chorych sie znacznie splaszczyl - podoba mi sie 
		 * przypomina chyba bardziej rzeczywiste
		 */
		int liczbaOsobnikow = 10000;
		int liczbaKrawedzi = 50000; //kazdy ma srednio liczbaKrawedzi*2 / liczbaWierzholkow polaczen
		int poczatkowaLiczbaChorych = 10;
		TypSieci typSieci = TypSieci.SCALE_FREE;
		
		//listowy dziala znacznie szybciej niz macierzowy nie ma sensu uzywac macierzowej implementacji
		graf = new GrafListowy(typSieci, liczbaOsobnikow, liczbaKrawedzi);
		
		int sumaStopniWierzcholkow = 0;
		int[] tablicaStopniWierzcholkow = graf.getTablicaStopniWierzcholkow();
		for(int i=0; i<tablicaStopniWierzcholkow.length; i++)
			sumaStopniWierzcholkow += tablicaStopniWierzcholkow[i];
		System.out.println("sumaStopnie = "+sumaStopniWierzcholkow+"   liczbaWierzhcolkow ="+tablicaStopniWierzcholkow.length);
		//for(int i=0; i<100; i++)
		//	System.out.println( graf.getListaSasiadowOsobnika(i) );
		
		int liczbaZaszczepionych = 30;
		double prawdopodobienstwoZarazenia = 0.012;
		int czasTrwaniaChorobyWDniach = 7;
		ModelSzczepienia.zaszczepLosowo(graf, liczbaZaszczepionych);
		//ModelSzczepienia.zaszczepOsobnikiZMaxStopniem(graf, liczbaZaszczepionych);
		Epidemia epidemia = new Epidemia(graf, poczatkowaLiczbaChorych, prawdopodobienstwoZarazenia, czasTrwaniaChorobyWDniach);
		int liczbaDni = 360;
		epidemia.start(liczbaDni);
		System.out.println( Arrays.toString(epidemia.getLiczbyChorychKazdegoDnia()) );
		System.out.println( Arrays.toString(epidemia.getLiczbyZdrowychKazdegoDnia()) );
		System.out.println( Arrays.toString(epidemia.getLiczbyOdpornychKazdegoDnia()) );
		System.out.println( Arrays.toString(epidemia.getZachorowalnoscKazdegoDnia()) );
		
		Plotter.plot(epidemia.getLiczbyZdrowychKazdegoDnia(), epidemia.getLiczbyChorychKazdegoDnia(), epidemia.getLiczbyOdpornychKazdegoDnia());
		
		int liczbaOsobnikowKtorePrzeszlyChorobe = epidemia.getLiczbyZdrowychKazdegoDnia()[0] - epidemia.getLiczbyZdrowychKazdegoDnia()[liczbaDni-1];
		System.out.println((double)liczbaOsobnikowKtorePrzeszlyChorobe/liczbaOsobnikow);

	}

}
