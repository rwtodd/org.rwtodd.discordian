## just until I can figure SBT out

.POSIX:
.SUFFIXES: .msrc .scala

MACSRC=macsrc
all:  package.scala discord.scala

.msrc.scala:
	$(MACSRC) < $< > $@

