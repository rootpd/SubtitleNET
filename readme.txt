SubtitleNET v0.9.6
git@github.com:rootpd/SubtitleNET.git

Info:
Program na základe video súboru nájde prislúchajúce titulky, t.j. mali by sedie, a ak nesedia, tak je to chyba uploadera titulkov.
Titulky mono vyh¾adáva aj bez video súboru, aj keï neviem, na èo by niekto vôbec daèo také robil. :)
Program je šírenı "as is", nechoïte za mnou, ak vám to nieèo odserie, boli ste varovaní. Ono tam fakt niè hrozné nie je, ale pre istotu. :)
Všetka komunikácia je len so serverom OpenSubtitles.org, ktorı poskytol aj API, za èo ïakujem. Ak by nieèo nešlo, tak checknite, èi vôbec beí web.
(... a v podstate je to to isté èo u je v bsplayeri, len na to teraz nepotrebujete 20mb player so spywareom ;) ...)

Known Issues:
- pri viac CD filmoch treba osobitne h¾ada a sahova titulky pre kadé CD. (ahajte HD, nebudete ma takéto problémy)
- pri textovom vyh¾adávaní seriálov to trvá strašne dlho kvôli poètu vısledkov (asi), nepouíval by som radšej, h¾adajte pre konkrétny súbor.
- stále nie je monos zruši vyh¾adávanie uprostred (neviem, èi niekedy vôbec pribudne, nie je to moc nevyhnutné)

Additional:
Viacerí ste mi vraveli, e ho budete chcie, tak aspoò pomôte pri testovaní.
Na správne fungovanie je potrebná najnovšia verzia Javy (Java Runtime Enviroment), sahujte TU.
Samotnı .jar, ktorı stiahnete je u spustite¾nı, netreba ho rozba¾ova
A všetky bugy hláste:
  fiitkar: dulacka
  mail: dulacka@gmail.com
  facebook: facebook.com/dulacka
  twitter: @rootpd

Program je open-source, pouívajte ako chcete. Ak máte nejakı dobrı nápad, forknite si projekt a potom pošlite patch :).

Changelog: 
v0.9.6
zmenená pevná .srt prípona titulkov na originálnu (nie je dobré spolieha sa na prehrávaèe)
opravené null-sized okno a pridaná aktuálna akcia
pri non-hash searchi pridané automatické stiahnutie všetkıch CD (pozor, buggy, spolieha sa na to, e CD v zozname nasledujú za sebou!!)
pridané ïalšie informácie vo vısledkoch vyh¾adávania

v0.9.4
menšie bugfixy

v0.9
pridané logovanie a ošetrovanie exceptionov
prihlasovanie na API server prebieha na pozadí hneï po spustení programu (rıchlejšie vyh¾adávanie)
upravenı vıpis nájdenıch titulkov

v0.8.0.1
boli problémy s .jarom, niektorım ho nechcelo spusti, toto by to malo vyrieši
(created by pd as a VPPJ project)