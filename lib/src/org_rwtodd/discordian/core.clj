(ns org-rwtodd.discordian.core
  (:refer-clojure :exclude [format])
  (:import (java.time LocalDate)
           (java.time.temporal ChronoUnit)))

(def ^:private x-day (LocalDate/of 8661 7 5))
(def ^:private names "discordian day/season/holyday names"
  {
   :tibs   "St. Tib's Day"
   :days   ["Sweetmorn" "SM",     "Boomtime" "BT",
             "Pungenday" "PD",     "Prickle-Prickle" "PP",
             "Setting Orange" "SO"]
   :seasons ["Chaos" "Chs",   "Discord" "Dsc",   "Confusion" "Cfn", 
             "Bureaucracy" "Bcy",   "The Aftermath", "Afm"]
   :holy-5  ["Mungday", "Mojoday", "Syaday", "Zaraday", "Maladay"]
   :holy-50 ["Chaoflux", "Discoflux", "Confuflux", "Bureflux", "Afflux"]
   :exclaim ["Hail Eris!", "All Hail Discordia!", "Kallisti!", "Fnord.", "Or not.",
             "Wibble.", "Pzat!", "P'tang!", "Frink!", "Slack!", "Praise \"Bob\"!", "Or kill me.",
             ;; randomness, from the Net and other places. Feel free to add (after
             ;; checking with the relevant authorities, of course).
             "Grudnuk demand sustenance!", "Keep the Lasagna flying!",
             "You are what you see.", "Or is it?", "This statement is false.",
             "Lies and slander, sire!", "Hee hee hee!", "Hail Eris, Hack Clojure!"]})

(defn date
  "Get the discordian date, either by specifying it [y m d] or just getting
  the current day"
  ([] (date (LocalDate/now)))
  ([^LocalDate ld] (let [adjusted (- (.getDayOfYear ld)
                                     (if (and (.isLeapYear ld)
                                              (> (.getMonthValue ld) 2))
                                       2
                                       1))
                         day-of-ssn (inc (mod adjusted 73))
                         is-tibs  (and (= (.getMonthValue ld) 2)
                                       (= (.getDayOfMonth ld) 29))
                         wday     (mod adjusted 5)
                         ssn      (int (/ adjusted 73))]
                     {
                      :day-of-season day-of-ssn
                      :season     (if is-tibs (:tibs names)
                                      (nth (:seasons names) (* 2 ssn)))
                      :season-short (if is-tibs (:tibs names)
                                        (nth (:seasons names) (inc (* 2 ssn))))
                      :weekday    (if is-tibs (:tibs names)
                                      (nth (:days names) (* 2 wday)))
                      :weekday-short  (if is-tibs (:tibs names)
                                          (nth (:days names) (inc (* 2 wday))))
                      :year       (+ 1166 (.getYear ld))
                      :holyday    (nth (case day-of-ssn 5 (:holy-5 names) 50 (:holy-50 names) [])
                                       ssn
                                       nil)
                      :til-xday   (.until ld x-day ChronoUnit/DAYS)
                      :is-tibs    is-tibs     
                      }))
  ([y m d] (date (LocalDate/of y m d))))

(defn format
  "Generate a formatted string for date `d` using the formatting codes `fmt`"
  [^String fmt d]
  (let [end (count fmt)
        sb  (java.lang.StringBuilder. (* 2 (count fmt)))]
    (loop [idx 0]
      (if (>= idx end)
        (.toString sb)
        (let [ch (.charAt fmt idx)]
          (if (= ch \%)
            (let [idx+2 (+ idx 2)]
              (case (nth fmt (inc idx) nil)
                \%  (do (.append sb \%) (recur idx+2))
                \n  (do (.append sb \newline) (recur idx+2))
                \t  (do (.append sb \tab) (recur idx+2))
                \A  (do (.append sb (:weekday d)) (recur idx+2))
                \a  (do (.append sb (:weekday-short d)) (recur idx+2))
                \B  (do (.append sb (:season d)) (recur idx+2))
                \b  (do (.append sb (:season-short d)) (recur idx+2))
                \d  (do (.append sb (:day-of-season d)) (recur idx+2))
                \e  (let [day (:day-of-season d)]
                      (.append sb day)
                      (.append sb (if (== (int (/ day 10)) 1)
                                    "th"
                                    (case (mod day 10)
                                      1 "st"
                                      2 "nd"
                                      3 "rd"
                                      "th")))
                      (recur idx+2))
                \H  (do (when-let [hd (:holyday d)] (.append sb hd))
                        (recur idx+2))
                \X  (do (.append sb (:til-xday d)) (recur idx+2))
                \Y  (do (.append sb (:year d)) (recur idx+2))
                \.  (let [ex (:exclaim names)]
                      (.append sb (nth ex (rand-int (count ex))))
                      (recur idx+2))
                \}  (recur idx+2)
                \N  (recur (if (:holyday d) idx+2 end))
                \{  (if (:is-tibs d)
                      (let [closing (.indexOf fmt "%}" idx)]
                        (.append sb (:tibs names))
                        (recur (if (pos? closing) (+ 2 closing) end)))
                      (recur idx+2))
                ;; unknown escape... just print the % and move on
                (do (.append sb \%) (recur (inc idx)))))
            ;; not an escaped char, just print it
            (do (.append sb ch)
                (recur (inc idx)))))))))
