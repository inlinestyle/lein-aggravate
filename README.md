# lein-aggravate


A Leiningen plugin for concatenating and compressing files. 
Good for your `.css` files (reduce page load time by cutting down on asset loading calls).
Possibly good for your `.js` files (good use cases are compiled `.coffee` files and natural `.js` files)

## Usage

    To get this at the user level, just follow the usual Leiningen instructions:
    For Leiningen 2: Put `[lein-aggravate "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your `:user` profile.
    For Leiningen 1: Do `lein plugin install lein-aggravate 0.1.0-SNAPSHOT`.

    For your individual project:
    Put `[lein-aggravate "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your `project.clj`.


    There are two optional fields that you can add to your `project.clj`, `aggregate-files` and `aggregate-dirs`:

    ```
  
    :aggregate-files [{:input ["foo/bar.css" "foo/baz.css"]
                       :output "foo/compiled-files/main.css"
                       :compressor "yui"}
                      {:input ["messed/up.css" "messed/up/more.css"]
                       :output "foo/compiled-files/messed.css"}]
    :aggregate-dirs [{:input ["foo"]
                      :output "foo/compiled-dirs/main.css"
                      :suffix "css"
                      :compressor "yui"}]
    ```

FIXME: and add an example usage that actually makes sense:
    You can run this in your project with:
    ```
    $ lein aggravate
    ```

## License

Copyright © 2012 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
