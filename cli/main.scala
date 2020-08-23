package org.rwtodd.cli.ddate

import org.rwtodd.discordian.Date

object Cmd {
	def main(args: Array[String]): Unit = {
		val todayFmt = "Today is %{%A, the %e day of %B%} in the YOLD %Y%N%nCelebrate %H"
		val otherFmt = "%{%A, %B %d%}, %Y YOLD"
		try {
			println(args.length match {
				case 0 => new Date().format(todayFmt)
				case 1 => new Date().format(args(0))
				case _ => throw new Exception("incorrect arguments!")
			})
		} catch {
			case e:Throwable => System.err.println(e); System.exit(1)
		}
	}
}

// vim: filetype=scala:noet:softtabstop=0:tabstop=4:shiftwidth=0:
