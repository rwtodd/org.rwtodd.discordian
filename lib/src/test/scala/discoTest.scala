package org.rwtodd.discordian

import org.scalatest.flatspec.AnyFlatSpec

class DiscoSpec extends AnyFlatSpec {
	behavior of "Dates Close to XDay"
	it should "be the days up to X" in {
		assert(0 == new Date(8661,7,5).daysTilXDay())
		assert(1 == new Date(8661,7,4).daysTilXDay())
		assert(2 == new Date(8661,7,3).daysTilXDay())
		assert(3 == new Date(8661,7,2).daysTilXDay())
		assert(4 == new Date(8661,7,1).daysTilXDay())
	}

	it should "be the day after X" in {
		assert(-1 == new Date(8661,7,6).daysTilXDay())
	}

	behavior of "A Few Known Dates"
	it should "be the Chaoflux Holyday" in {
		val d = new Date(1956,2,19)
		assert(d.isHolyday)
		assert("Chaoflux"       == d.getHolyday())
		assert(2449088          == d.daysTilXDay())
		assert("Setting Orange" == d.getWeekday())
		assert("SO"             == d.getWeekday(true))
		assert("Chaos"          == d.getSeason())
		assert("Chs"            == d.getSeason(true))
		assert(50               == d.dayOfSeason)
	}
	it should "be just some day" in {
		val d = new Date(1977,6,1)
		assert(!d.isHolyday)
		assert(""               == d.getHolyday())
		assert(2441315          == d.daysTilXDay())
		assert("Boomtime"       == d.getWeekday())
		assert("BT"             == d.getWeekday(true))
		assert("Confusion"      == d.getSeason())
		assert("Cfn"            == d.getSeason(true))
		assert(6                == d.dayOfSeason)
	}
}

// vim: filetype=scala:noet:tabstop=4:softtabstop=0:shiftwidth=0:
