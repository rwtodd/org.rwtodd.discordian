(ns org-rwtodd.discordian.core-test
  (:require [clojure.test :refer :all]
            [org-rwtodd.discordian.core :as d]))


(deftest test-xday
  (testing "Dates Close to XDay"
    (is (== 0 (:til-xday (d/date 8661 7 5))))
    (is (== 1 (:til-xday (d/date 8661 7 4))))
    (is (== 2 (:til-xday (d/date 8661 7 3))))
    (is (== 3 (:til-xday (d/date 8661 7 2))))
    (is (== 4 (:til-xday (d/date 8661 7 1)))))
  (testing "The day after X"
    (is (== -1 (:til-xday (d/date 8661 7 6))))))

(deftest test-known-dates
  (testing "a chaoflux holyday"
    (let [one (d/date 1956 2 19)]
      (is (= (:holyday one) "Chaoflux"))
      (is (== (:til-xday one) 2449088))
      (is (= (:weekday one) "Setting Orange"))
      (is (= (:weekday-short one) "SO"))
      (is (= (:season one) "Chaos"))
      (is (= (:season-short one) "Chs"))
      (is (== (:day-of-season one) 50))))
  (testing "just some day"
    (let [one (d/date 1977 6 1)]
      (is (not (:holyday one)))
      (is (== (:til-xday one) 2441315))
      (is (= (:weekday one) "Boomtime"))
      (is (= (:weekday-short one) "BT"))
      (is (= (:season one) "Confusion"))
      (is (= (:season-short one) "Cfn"))
      (is (== (:day-of-season one) 6)))))


