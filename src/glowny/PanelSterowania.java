package glowny;
import java.util.Arrays;
import plot.*;

public class PanelSterowania {
	private static Graf graf;
	
	public static void main(String[] args) {
		int liczbaOsobnikow = 1000;
		int liczbaKrawedzi = 5000;
		int poczatkowaLiczbaChorych = 10;
		TypSieci typSieci = TypSieci.SCALE_FREE;
		graf = new Graf(typSieci, liczbaOsobnikow, liczbaKrawedzi);
		int liczbaZaszczepionych = 30;
		double prawdopodobienstwoZarazenia = 0.03;
		int czasTrwaniaChorobyWDniach = 4;
		ModelSzczepienia.zaszczepLosowo(graf, liczbaZaszczepionych);
		//ModelSzczepienia.zaszczepOsobnikiZMaxStopniem(graf, liczbaZaszczepionych);
		Epidemia epidemia = new Epidemia(graf, poczatkowaLiczbaChorych, prawdopodobienstwoZarazenia, czasTrwaniaChorobyWDniach);
		int liczbaDni = 100;
		epidemia.start(liczbaDni);
		System.out.println( Arrays.toString(epidemia.getLiczbyChorychKazdegoDnia()) );
		System.out.println( Arrays.toString(epidemia.getLiczbyZdrowychKazdegoDnia()) );
		System.out.println( Arrays.toString(epidemia.getLiczbyOdpornychKazdegoDnia()) );
		
		Plotter.plot(epidemia.getLiczbyZdrowychKazdegoDnia(), epidemia.getLiczbyChorychKazdegoDnia(), epidemia.getLiczbyOdpornychKazdegoDnia());
		
		int liczbaOsobnikowKtorePrzeszlyChorobe = epidemia.getLiczbyZdrowychKazdegoDnia()[0] - epidemia.getLiczbyZdrowychKazdegoDnia()[liczbaDni-1];
		System.out.println((double)liczbaOsobnikowKtorePrzeszlyChorobe/liczbaOsobnikow);

	}

}
