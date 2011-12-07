(ns clj-hangman.core
     (:gen-class))
(use '[clojure.java.io :only [reader]])
(use '[clojure.string :only [ replace ] :rename {replace str-replace }])

;; Set the language
(def language :english)

;; The two languages we currently support
(def wordlists {:english "/usr/share/dict/words"
                :dutch   "./dutchwordlist.txt"})

;; The dictionary file has lots of weird/incomplete (non)words
;; in it, lets filter them out with this function
(defn filter-non-words [x]
  (not (re-find #"[^a-z]+" x)))

;; read in the word list based on the language and filter
(def wordlist
  (filter #(filter-non-words %)
          (line-seq (reader (get wordlists language)))))

;; (def word (rand-nth wordlist))

(defn check-letter [ guessed-letter word-letter letter-from-hidden-word ]
    ;;(println guessed-letter word-letter letter-from-hidden-word )
    (cond
        (= guessed-letter word-letter) guessed-letter
        (not= letter-from-hidden-word ".") letter-from-hidden-word
        :else "." ))

(defn my-search-func [guessed-letter word hidden-word]
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
          :else (recur (my-search-func guess word hidden-word)
                 score))))))

(defn -main [& args]
    (println "Welcome to clj-hangman..." )
    (loop [ answer \y ]
        (if (= answer \y)
            (game-loop)
            (System/exit 0))
        (println "Do you want to play again? Press \"y\"...")
        (recur (first (read-line)))))
