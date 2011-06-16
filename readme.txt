SubtitleNET v0.9.6beta2
git@github.com:rootpd/SubtitleNET.git

DOWNLOAD:
=========
Pre standardne pouzivanie staci stiahnut subor subtitlenet.jar a spustit (nerozbalovat, spustit!)
https://github.com/rootpd/SubtitleNET/raw/master/subtitlenet.jar

Info:
=====
- Program na zaklade video suboru najde prisluchajuce titulky, t.j. mali by sediet, a ak nesedia, tak je to chyba uploadera titulkov.
- Titulky mozno vyhladavat aj bez video suboru, aj ked neviem, na co by niekto vobec daco take robil. :)
- Program je sireny "as is", nechodte za mnou, ak vam to nieco odserie, boli ste varovani. Ono tam fakt nic hrozne nie je, ale pre istotu. :)
- Vsetka komunikacia je len so serverom OpenSubtitles.org, ktory poskytol aj API, za co dakujem. Ak by nieco neslo, tak checknite, ci vobec bezi web.

(... a v podstate je to to iste co uz je v bsplayeri, len na to teraz nepotrebujete 20mb player so spywareom ;) ...)

Known Issues:
=============
- pri viac CD filmoch treba osobitne hladat a stahovat titulky pre kazde CD. (tahajte HD, nebudete mat taketo problemy)
- pri textovom vyhladavani serialov to trva strasne dlho kvoli poctu vysledkov (asi), nepouzival by som radsej, hladajte pre konkretny subor.
- stale nie je moznost zrusit vyhladavanie uprostred (neviem, ci niekedy vobec pribudne, nie je to moc nevyhnutne)

Additional:
===========
Viaceri ste mi vraveli, ze ho budete chciet, tak aspon pomozte pri testovani.
Na spravne fungovanie je potrebna najnovsia verzia Javy (Java Runtime Enviroment)
http://java.com/en/download/index.jsp

Samotny .jar, ktory stiahnete je uz spustitelny, netreba ho rozbalovat
A vsetky bugy hlaste:
  fiitkar: dulacka
  mail: dulacka@gmail.com
  facebook: facebook.com/dulacka
  twitter: @rootpd

Program je open-source, pouzivajte ako chcete. Ak mate nejaky dobry napad, forknite si projekt a potom poslite patch :).

Changelog: 
==========
v0.9.6beta2
opravene rozoznavanie pripon videosuborov (vdaka martingt89)
pridana moznost zmeny velkosti okna (znova vdaka martingt89)
opravena chyba pri textovom vyhladavani

v0.9.5
pridane dalsie informacie vo vysledkoch vyhladavania
zmenena pevna .srt pripona titulkov na originalnu (nie je dobre spoliehat sa na prehravace)
opravene null-sized okno a pridana aktualna akcia
pri non-hash searchi pridane automaticke stiahnutie vsetkych CD (pozor, buggy, spolieha sa na to, ze CD v zozname nasleduju za sebou!!)

v0.9.4
mensie bugfixy

v0.9
pridane logovanie a osetrovanie exceptionov
prihlasovanie na API server prebieha na pozadi hned po spusteni programu (rychlejsie vyhladavanie)
upraveny vypis najdenych titulkov

v0.8.0.1
boli problemy s .jarom, niektorym ho nechcelo spustit, toto by to malo vyriesit

(created by rootpd as a VPPJ project)