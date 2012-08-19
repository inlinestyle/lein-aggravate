(defproject lein-aggravate "0.1.0-SNAPSHOT"
  :description "Aggregate your files!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[com.yahoo.platform.yui/yuicompressor "2.4.2"]]
    :aggregate-files [{:input ["foo/bar.css" "foo/baz.css"]
                     :output "foo/compiled-files/main.css"}]
  :aggregate-dirs [{:input ["foo"]
                    :output "foo/compiled-dirs/main.css"
                    :suffix "css"}]
  :eval-in-leiningen true)
