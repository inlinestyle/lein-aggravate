(ns leiningen.compressor-helpers)

(def YUI "yui")

(defn compress-with-yui [filename]
  (com.yahoo.platform.yui.compressor.YUICompressor/main (into-array ["--type" "css" "-o" filename filename])))

