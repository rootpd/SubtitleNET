SubtitleNET v0.9.7
git@github.com:rootpd/SubtitleNET.git

LICENCE:
========
SubtitleNET is free software released under GNU/GPL licence.
http://www.gnu.org/licenses/gpl.html

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

Ako pouzit SubtitleNET v prikazovom riadku?
java -jar subtitlenet.jar [path_to_movie]
java -jar subtitlenet.jar "E:\Cinema\Mr. Nobody 720p\Mr. Nobody.mkv"

V linuxe je moznost nechat otvorit video subor pomocou subtitlenetu, ten hned po spusteni zacne vyhladavat titulky na zvoleny subor.


Known Issues:
=============
- pri viac CD filmoch treba osobitne hladat a stahovat titulky pre kazde CD. (tahajte HD, nebudete mat taketo problemy)
- pri textovom vyhladavani serialov to trva strasne dlho kvoli poctu vysledkov (asi), nepouzival by som radsej, hladajte pre konkretny subor.


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
v0.9.7
znemoznenie kliknutia na hlavne okno ak sa vyhladavaju titulky (@martingt89)
zrusenie vyhladavania pocas jeho priebehu (@martingt89)
pridana moznost pridania cesty k suboru cez prikazovy riadok a automatickeho nastavenia cesty v programe (pouzitie popisane hore v info) (@martingt89)
opraveny crash pri textovom vyhladavani kedy sa program snazil ziskat oznaceny subor v liste
opravene cesty a nazvy titulkov pocas stahovania
pridane oznamovacie okno pocas stahovania titulkov

v0.9.6
opravene rozoznavanie pripon videosuborov (vdaka @martingt89)
pridana moznost zmeny velkosti okna (znova vdaka @martingt89), dodatocne este vylepsena
opravena chyba pri textovom vyhladavani
opravene namnozenie hladani pri bezhlavom klikani (@martingt89)
znemoznenie vyhladavania "no video files" suboru

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
