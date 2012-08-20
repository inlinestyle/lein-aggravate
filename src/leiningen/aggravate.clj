(ns leiningen.aggravate
  (:require [leiningen.file-helpers :as fhelpers]
            [leiningen.compressor-helpers :as chelpers]
            [leiningen.compile :as lcompile]
            [robert.hooke :as hooke]))

(defn aggravate "Aggregate your files!" [project & args]
  (doseq [options (mapcat project [:aggregate-files :aggregate-dirs])]
    (fhelpers/remove-files (options :output)))
  (doseq [options (project :aggregate-dirs)]
    (fhelpers/aggregate-files (fhelpers/get-files-in-dirs (options :input) (options :suffix)) 
                        (fhelpers/create-file (options :output))))
  (doseq [options (project :aggregate-files)]
    (fhelpers/aggregate-files (options :input)
                        (fhelpers/create-file (options :output))))
  (doseq [options (filter #(% :compressor) (mapcat project [:aggregate-files :aggregate-dirs]))]
    (cond
      (= (options :compressor) chelpers/YUI) (-> options
                                         :output
                                         fhelpers/get-absolute-path
                                         chelpers/compress-with-yui))))


