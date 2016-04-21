[33mcommit 2d7c1de07bd583d44c50fe768cc5ec4bceeba574[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Wed Apr 20 13:27:39 2016 +0200

    Zaimplementowalem siec hybrydowa tj polaczenie sieci scale free i small world, algorytm taki jak w small world - tj zacyznamy od pierscienia i mamy pewne ppb przepiecia krawedzi, z tym ze wezel DO ktorego sie przepinamy ma ppb proporcjonalne do stopnia (ta czesc zaczerpnieta z sieci scale free) a nie stale (rozklad jednorodny) jak w oryginalnym algorytmie small world

[33mcommit 413a2083d6dbb43be85fcdf53f4a8ffb37810619[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Wed Apr 20 10:03:16 2016 +0200

    dolozylem 2 parametry bedace parametrami rozkladu normalnego (srednia i odch std) - podatnosci osobnika jest z niego losowana, przy czym gdy wylosuje poza przedzialem[0,1] to wartosc podatnosci przycinam, podatnosc jest wymnazana z zakaznoscia patogenu (wczesniej zakaznosc = ppb Zarazenia, teraz ppbZarazenia = zakaznoscPatogenu * podatnoscOsobnika - podatnosc jest indywidualna cecha kazdego osobnika i pochodzi z rozkladu normalnego z podanymi parametrami mi i sigma, zakaznosc patogenu traktuje jako stala (zakaznosc w zasadzie tylko skaluje nam podatnosc wiec mozna by to polaczyc w jeden parametr ppb zakazenia ale ja wolalem to rozbic na 2 czesci zeby wyraznie oddzielic, dzieki temu tez dobrze jest skalowac podatnosc do przedzialu 0,1 co ma dobra interpretacje - 0 to calkowity brak odpornosci 1- to calkowita odpornosc))

[33mcommit 58a4085a71ba10b0219a96472debc2694bed4891[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Wed Apr 6 21:05:03 2016 +0200

    poprawilem model szczepienia gdzie dzwonimy i pytamy o najpopulatniejszych znajomych. W przypadku sieci scale free byl taki problem ze przy wiekszej liczbie szczepionych (kilkanasce procent w populacji) najpupularniejszy znajomy kazdego byl juz zaszczepiony, program wpadal w nieskonczona petle nie mogac znalezc kolejnych ludzi do zaszczepienia. Naprawilem to dopisujac ze jezeli najpopularniejszy znajomy danej osoby jest juz odporny to prosimy o podanie nazwiska losowej osoby sposrod znajomych (technicznie rozwiazalem to tak ze szczepie osobe pierwsza na liscie znajomych osoby do ktorej dzwonimy - pierwsza na liscie ktora nie zostala dotad zaszczepiona)

[33mcommit d9b1da3c23a2635aff24ee6a04bdfdc9688b7e85[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Tue Apr 5 20:41:36 2016 +0200

    Dopisalem GUI git add .!

[33mcommit 7e860469570e9875861f059e4554f9a6d6c67cb9[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Tue Apr 5 14:19:00 2016 +0200

    dopisalem implementacje generatorow krawedzidla grafow typu: small world i losowego, small world wymaga wiecej parametrow niz losowy i scale free i byl przez to problem , trzeba jeszcze pomyslec jak to poprawic bo teraz trzeba w panelu sterowania pamietac ze musi byc dodatkowy atrybut przy tworzeniu grafu jezeli typem sieci jest Small World (ten parametr o ktorym pisze to ppb przepiecia w 2 etapie tworzenia grafu im wieksze ppb tym blizej losowego im mniejsze tym blizej grafu pierscieniowego od ktorego zaczyna sie tworzenie grafu small world)

[33mcommit d1c76d3665dc9cd4d846f9f85489adbdf0ce052b[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Mon Apr 4 20:14:37 2016 +0200

    dopisalem dwie strategie szczepienia, szczepienie przez dzwonienie po ludziach i prroszenie o wskazanie losowych znajomych i fruga podobnie tylko prosimy o wskazanie najpopularniejszych znajomych

[33mcommit a8a0209058fb95eb6c449a1d1a27e5db127d996d[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Mon Apr 4 14:58:10 2016 +0200

    zmienilem sposob wyliczania liczbaOsobnikowKtorePrzeszlyEpidemie bo wczesniej nie uwzglednialem osobnikow poczatkowo chorych, a one tez przeszly chorobe przeciez, poza tym zrefaktoryzowalem kod w glownej klasie (tej  metoda main) przenoszac duzo z metody main do innych ktore generuje mi wiele iteraci tworzenia sieci i puszczania epidemii przy tych samych parametrach, poza tym parametry przenioslem jako pola statyczne glownej klasy i dizeki temu nie musze tych wartosci przenosic jako parametrow metod. WAZNEgit status dodalem typ enum StrategiaSzczepienia, przy dodawaniu nowych strategii trzeba bedzie to tam wuzglednic i dolozyc do petli switch w klasie chyba ModelSzczepienia

[33mcommit d19b63072c2c4827a89f70165b18a2adb3abc17d[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Thu Mar 17 23:04:11 2016 +0100

    poprawilem algorytm generowania sieci scale free tak ze teraz zgadza sie z tym co jest w pracy z 1999 roku. Warto zapamietac ze w metodzie getDystrybuanta klasy genereatorKrawedziScaleFree kiedy tworze rozklad prawdopodobienstwa  do stopnia wierzcholka dodaje 1 a do sumy liczbe wezlow w sieci na obecnym etapie jej budowania, tzn tak jakby stopien byl zawsze co najmniej = 1 ale nie zwracam takiego stopnia z poziomu obiektow klasy graf bo uznalem to za problemogenne

[33mcommit 6aa124fa044355bb234a99e94ed8c3f508f96a29[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Tue Mar 15 12:55:14 2016 +0100

    poprawilem generowanie krawedzi, uzaleznilem parametr m od rzadanej liczby krawedzi, wczesniej nie uwzglednialem w ogole rzadanej liczby krawedzi to ile ich wychodzilo wynikalo glownie z parametru m ktory dalem na sztywno = 4, teraz go wyliczam. Parametr m to polowa! sredniego stopnia wierzcholka w grafie (do ilu juz obecnych w grafie doczepia sie nowy dodawany wierzcholek na etapie po inicjalizacji malego podgrafu losowego, w sumie doczepiam nie liczac inicjalizacji malego grafu losowego m*liczba wierzcholkow  krawedzi, wiec m to srednia liczba krawedzi przypadajacych na jeden wierzcholek. Liczba krawedzi przypadajaca na 1 wierzcholek pomnozona przez 2 to sredni stopien wierzcholka w grafie), wyliczam m następująco: liczba krawedzi / liczba wezlow. Dodalem tez implementacje macierzowa grafu, ale dziala wolniej niz listowa - bez sensu

[33mcommit 8d5c4a7b73a4d72b1280a67870d927273d177919[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Sun Mar 13 17:24:11 2016 +0100

    dodalem prosty plotter ktory jakos tam dziala

[33mcommit c272ef2bbb9e1b63ff15f4d8b0afada1d9471dcb[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Sat Mar 5 14:11:56 2016 +0100

    dodalem demo plottera, zeby plot dzialal trzeba dodac 2 jary z katalogu: /home/tomek/biblioteki/jfreechart-1.0.19/lib/ jary: jcommon-1.23.jar i jfreechart-1.0.19.jar

[33mcommit 89f598e77c5fc91cb5e71e7a7cabda4d76bfacd0[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Sat Mar 5 11:28:12 2016 +0100

    troche uporzadkowalem, podzielilem na pakiety, zrobilem szkielet roznych generatorow krawedzi, kazdy implementuje interfejs generatorKrawedzi z 1 metoda generuj(), dopisalem tez osobnikowi pole wspolczynnikPodatnosciNaZakazenie wplywajacy na ppb zarazenia, im wiekszy wspolczynnik podatnosci i zakaznosc patogenu tym wieksze ppb zarazenia

[33mcommit 075c6ddf963d27a395b47853d4f747d6c67582f3[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Sat Mar 5 10:20:37 2016 +0100

    5 marca

[33mcommit 1e59fbebbd3e0681ac7a032759a6d2e896545d1e[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Mon Jan 11 22:37:37 2016 +0100

    dodalem parametr m, przemyslec parametr m0 algorytmu Barabasi- Alberta

[33mcommit d0ef0aa66d2ddd5225746d5e9bb976c83e958982[m
Merge: 758239f b7c2246
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Mon Jan 11 22:05:42 2016 +0100

    Merge branch 'master' of https://github.com/Revan2005/SiecScaleFree

[33mcommit 758239f56f47227e18dd9de232ba982b91b27acb[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Mon Jan 11 22:02:39 2016 +0100

    readme

[33mcommit b7c224623ca9e9019d226ac610ced144f7f0175f[m
Author: Tomek <tomek.biegus@gmail.com>
Date:   Mon Jan 11 21:58:46 2016 +0100

    Initial commit

[33mcommit 1a22f0c651d3138c37f42804b85588ee89532698[m
Author: Revan2005 <tomek.biegus@gmail.com>
Date:   Mon Jan 11 21:56:41 2016 +0100

    inicjalizacja
