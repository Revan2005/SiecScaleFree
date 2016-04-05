package generatoryKrawedzi;

import java.util.Random;

import glowny.Graf;

public class GeneratorKrawedziRandom implements GeneratorKrawedzi{

	/*
	 * zmodyfikowany algorytm Erdosa-Renyi'ego z 1959 roku
	 * tam maja parametry N-l wierzch i rozklad pk z ktore
	 * go biora liczby k-zakonczen krawedzi dla kazdego wierzcholka
	 * a nastepnie lacza losowo w pary te zakonczenia
	 * ja zrobie z 2 parametrami V i E gdzie dla kazdej krawedzi bede losowal ze stalym ppb
	 * pare wierzcholkow ktore ta krwedz bedzie miala polaczyc
	 */
	
	@Override
	public void generujKrawedzie(Graf graf, int liczbaKrawedzi) {
		Random rand = new Random();
		int liczbaDodanychKrawedzi=0;
		int wierzcholek1, wierzcholek2;
		while(liczbaDodanychKrawedzi<liczbaKrawedzi){
			wierzcholek1 = rand.nextInt(graf.getLiczbaWezlow());
			wierzcholek2 = rand.nextInt(graf.getLiczbaWezlow());
			if( graf.czyPolaczone(wierzcholek1, wierzcholek2) || (wierzcholek1 == wierzcholek2) )
				continue;
			graf.dodajKrawedz(wierzcholek1, wierzcholek2);
			liczbaDodanychKrawedzi++;
		}
		
	}

}
