package org.rwtodd.discordian


import spock.lang.Specification

class BasicTests extends Specification {

  def "dates close to x-day"() {
    expect:
      new DiscordianDate(8661,7,d).daysTilXDay == a
    where:
      d | a
      5 | 0
      4 | 1
      3 | 2
      2 | 3
      1 | 4
      6 | -1
  }

  def "an old known Chaoflux"() {
    given:
      def d = new DiscordianDate(1956,2,19)
    expect:
      d.holyDay
      !d.tibs
      d.holyDayName == 'Chaoflux'
      d.daysTilXDay == 2449088
      d.weekday == 'Setting Orange'
      d.shortWeekday == 'SO'
      d.season == 'Chaos'
      d.shortSeason == 'Chs'
      d.dayOfSeason == 50
  }

  def "an old known non-holiday"() {
    given:
      def d = new DiscordianDate(1977,6,1)
    expect:
      !d.holyDay
      !d.tibs
      d.holyDayName == ''
      d.daysTilXDay == 2441315
      d.weekday == 'Boomtime'
      d.shortWeekday == 'BT'
      d.season == 'Confusion'
      d.shortSeason == 'Cfn'
      d.dayOfSeason == 6
  }
}

