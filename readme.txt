SubtitleNET v0.9.6
git@github.com:rootpd/SubtitleNET.git

Info:
Program na z�klade video s�boru n�jde prisl�chaj�ce titulky, t.j. mali by sedie�, a ak nesedia, tak je to chyba uploadera titulkov.
Titulky mo�no vyh�ad�va� aj bez video s�boru, aj ke� neviem, na �o by niekto v�bec da�o tak� robil. :)
Program je ��ren� "as is", necho�te za mnou, ak v�m to nie�o odserie, boli ste varovan�. Ono tam fakt ni� hrozn� nie je, ale pre istotu. :)
V�etka komunik�cia je len so serverom OpenSubtitles.org, ktor� poskytol aj API, za �o �akujem. Ak by nie�o ne�lo, tak checknite, �i v�bec be�� web.
(... a v podstate je to to ist� �o u� je v bsplayeri, len na to teraz nepotrebujete 20mb player so spywareom ;) ...)

Known Issues:
- pri viac CD filmoch treba osobitne h�ada� a s�ahova� titulky pre ka�d� CD. (�ahajte HD, nebudete ma� tak�to probl�my)
- pri textovom vyh�ad�van� seri�lov to trv� stra�ne dlho kv�li po�tu v�sledkov (asi), nepou��val by som rad�ej, h�adajte pre konkr�tny s�bor.
- st�le nie je mo�nos� zru�i� vyh�ad�vanie uprostred (neviem, �i niekedy v�bec pribudne, nie je to moc nevyhnutn�)

Additional:
Viacer� ste mi vraveli, �e ho budete chcie�, tak aspo� pom��te pri testovan�.
Na spr�vne fungovanie je potrebn� najnov�ia verzia Javy (Java Runtime Enviroment), s�ahujte TU.
Samotn� .jar, ktor� stiahnete je u� spustite�n�, netreba ho rozba�ova�
A v�etky bugy hl�ste:
  fiitkar: dulacka
  mail: dulacka@gmail.com
  facebook: facebook.com/dulacka
  twitter: @rootpd

Program je open-source, pou��vajte ako chcete. Ak m�te nejak� dobr� n�pad, forknite si projekt a potom po�lite patch :).

Changelog: 
v0.9.6
zmenen� pevn� .srt pr�pona titulkov na origin�lnu (nie je dobr� spolieha� sa na prehr�va�e)
opraven� null-sized okno a pridan� aktu�lna akcia
pri non-hash searchi pridan� automatick� stiahnutie v�etk�ch CD (pozor, buggy, spolieha sa na to, �e CD v zozname nasleduj� za sebou!!)
pridan� �al�ie inform�cie vo v�sledkoch vyh�ad�vania

v0.9.4
men�ie bugfixy

v0.9
pridan� logovanie a o�etrovanie exceptionov
prihlasovanie na API server prebieha na pozad� hne� po spusten� programu (r�chlej�ie vyh�ad�vanie)
upraven� v�pis n�jden�ch titulkov

v0.8.0.1
boli probl�my s .jarom, niektor�m ho nechcelo spusti�, toto by to malo vyrie�i�
(created by pd as a VPPJ project)