(ns leiningen.aggravate
  (:require [leiningen.file-helpers :as fhelpers]
            [leiningen.compressor-helpers :as chelpers]
            [leiningen.compile :as lcompile]
            [robert.hooke :as hooke]))

(defn aggravate "Concat/Compress your files!" [project & args]
  (doseq [options (mapcat project [:aggravate-files :aggravate-dirs])]
    (fhelpers/remove-files (options :output)))
  (doseq [options (project :aggravate-dirs)]
    (fhelpers/aggregate-files (fhelpers/get-files-in-dirs (options :input) (options :suffix)) 
                        (fhelpers/create-file (options :output))))
  (doseq [options (project :aggravate-files)]
    (fhelpers/aggregate-files (options :input)
                        (fhelpers/create-file (options :output))))
  (doseq [options (filter #(% :compressor) (mapcat project [:aggravate-files :aggravate-dirs]))]
    (cond
      (= (options :compressor) chelpers/YUI) (-> options
                                         :output
                                         fhelpers/get-absolute-path
                                         chelpers/compress-with-yui))))

(defn compile-hook [task & args]
  (apply task args)
  (apply aggravate args))

(defn activate []
  (hooke/add-hook #'leiningen.compile/compile #'compile-hook))

(activate)
