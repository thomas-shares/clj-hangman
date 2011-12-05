(ns clj-hangman.core
     (:gen-class))
(use '[clojure.java.io :only [reader]])
(use '[clojure.string :only [ replace ] :rename {replace str-replace }])

(def wordlist
  (line-seq (reader "./dutchwordlist.txt")))

;; (def word (rand-nth wordlist))

(defn check-letter [ guessed-letter word-letter letter-from-hidden-word ]
    ;;(println guessed-letter word-letter letter-from-hidden-word )
    (cond
        (= guessed-letter word-letter) guessed-letter
        (not= letter-from-hidden-word ".") letter-from-hidden-word
        :else "." ))

(defn my-func [guessed-letter word hidden-word]
    (apply str (map #(check-letter guessed-letter %1 %2) word hidden-word)))

(defn game-loop [ ]
  (let [word (rand-nth wordlist) 
       word-length (count word) ]
    (println "The word is" word-length "characters long.")
    (loop [hidden-word (apply str (repeat word-length "." ))
           score 10]
      (println hidden-word score)
      (let [guess (first (read-line))
            score (if (some #{guess} word)
                    score
                    (dec score))]
        (cond
          (= hidden-word word) (println "well done!!! you solved it")
          (= 0 score) (println "You are hanging... The word was " word ".")
          :else (recur (my-func guess word hidden-word)
                 score))))))

(defn -main [& args]
    (println "Welcome to clj-hangman..." )
    (loop [ answer \y ]
        (if (= answer \y)
            (game-loop)
            (System/exit 0))
        (println "Do you want to play again? Press \"y\"...")
        (recur (first (read-line)))))






