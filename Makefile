## just until I can figure SBT out

.POSIX:
.SUFFIXES: .msrc .scala

MACSRC=macsrc
all:  lib/package.scala lib/discord.scala

.msrc.scala:
	$(MACSRC) < $< > $@

