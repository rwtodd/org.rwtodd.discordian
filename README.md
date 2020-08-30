# org-rwtodd.discordian.core

Discordian date library for Clojure

(there's a scala version in the __scala-version__ branch of this repo)

~~~~ clojure
(println
	(dd/format
		"%{%A, the %e of %B%} in %Y YOLD.%n%X days left... %.%N%nEnjoy %H" 
		(dd/date 1956 2 19)))
Setting Orange, the 50th of Chaos in 3122 YOLD.
2449088 days left... Or not.
Enjoy Chaoflux
~~~~
