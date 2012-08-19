(ns leiningen.aggravate
  (:require [leiningen.file-helpers :as fh]))

(def YUI "yui")

(defn compress-with-yui [filename]
  (com.yahoo.platform.yui.compressor.YUICompressor/main (into-array String ["--type" "css" "-o" filename filename])))

(defn aggravate "Aggregate your files!" [project & args]
  (doseq [options (mapcat project [:aggregate-files :aggregate-dirs])]
    (fh/remove-files (options :output)))
  (doseq [options (project :aggregate-dirs)]
    (fh/aggregate-files (fh/get-files-in-dirs (options :input) (options :suffix)) 
                        (fh/create-file (options :output))))
  (doseq [options (project :aggregate-files)]
    (fh/aggregate-files (options :input)
                        (fh/create-file (options :output))))
  (doseq [options (filter #(% :compressor) (mapcat project [:aggregate-files :aggregate-dirs]))]
    (cond
      (= (options :compressor) YUI) (-> options 
                                      :output 
                                      fh/get-absolute-path 
                                      compress-with-yui))))


