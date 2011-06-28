SubtitleNET v1.0
git@github.com:rootpd/SubtitleNET.git

by @rootpd (follow me at twitter if you want)

LICENCE:
========
SubtitleNET is free software released under GNU/GPL licence.
http://www.gnu.org/licenses/gpl.html

DOWNLOAD:
=========
For standard usage please follow this link:
https://github.com/rootpd/SubtitleNET/raw/master/subtitlenet.jar
(do not unpack, just run - if you have winrar it will probably try to unpack)

If you dont have latest version of java, you can download it here
http://java.com/en/download/index.jsp


Info:
=====
- Software will download subtitles according to video file (subtitles will fit to always video)
- You can search for subtitles without video file with text search, however there is no guarantee that subtitles will fit.
- All the comunication is made to OpenSubtitles.org API server.

How to run SubtitleNET via command line?
java -jar subtitlenet.jar [path_to_movie]
java -jar subtitlenet.jar "E:\Cinema\Mr. Nobody 720p\Mr. Nobody.mkv"

In linux, there is a posibility to let video be opened by SubtitleNET via "open with" system feature. The subtitle search will be started instantly then.


Known Issues:
=============
- When you have movie splitted to more discs, you have to search and download subtitles for every CD 
- When you do the text search and the count of subtitles is just too big, software looks frozen (do not text search TV Shows)
- When you do the text search and you want to download subtitles for more CDs, you have to choose the first of CDs in the list, others will be downloaded automatically. However, this is buggy because software counts on the specific order of subtitles and behaves unpredictably if you dont choose the first subtitles in the list or the order is messed up. If you have problems with it, please send me a message.


Changelog: 
==========
v1.0
<<<<<<< HEAD
- improved text search (now you can choose which movie you meant, if the search wasnt accurate e.g. "lord of the rings")
- added javadoc comments somewhere
=======
vylepsene textove vyhladavanie (program teraz dava na vyber, ktory film sa ma vyhladat pri neurcitom vyhladavani napr. "lord of the rings")
miestami doplnene javadoc komentare
>>>>>>> origin/master

v0.9.7
- main window inactive while search is being made (@martingt89)
- added possibility to cancel search (@martingt89)
- added command line option for instant search, more info mentioned before (@martingt89)
- fixed filelist bug causing crash
- fixed paths while downloading subtitles
- added message window while downloading subtitles

v0.9.6
- fixed video recognition (@martingt89)
- added window size change posibility @martingt89)
- fixed bug at text search
- fixed multiple searches bug (@martingt89)
- fixed "no video files" download posibility

v0.9.5
- added more information about found subtitles
- changed fixed .srt extension to original
- fixed nullsized message window (now showing what is happening now)
- added automatic download of other CDs in non-hash search if there is more than one disc

v0.9.4
- minor bugfixes

v0.9
- logging and exception handling added
- signing into API server is now made in background
- subtitle list changed

v0.8.0.1
- fixed .jar problems

(created by rootpd as a VPPJ project)
