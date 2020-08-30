(ns org-rwtodd.ddate
  (:require [org-rwtodd.discordian.core :as d])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (letfn [(to-ints [args] (map #(Integer/parseInt %) args))]
    (try
      (case (count args)
        0 (println (d/format "Today is %{%A, the %e day of %B%} in the YOLD %Y%N%nCelebrate %H"
                             (d/date)))
        1 (println (d/format (first args) (d/date)))
        3 (println (d/format  "%{%A, %B %d%}, %Y YOLD" (apply d/date (to-ints args))))
        4 (println (d/format (first args) (apply d/date (to-ints (rest args)))))
        (throw (Exception. "Wrong number of arguments!")))
      (catch Exception e
        (.println *err* "Usage: ddate [fmt] [y m d]")
        (System/exit 1)))))
