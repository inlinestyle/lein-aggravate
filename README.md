# lein-aggravate

A Leiningen plugin to concatenate files. 
Usefull for catting your `.css` files (reduce page load time by cutting down on asset loading calls).
Conditionally usefull for catting your `.js` files (good use cases are compiled `.coffee` files and natural `.js` files)

## Usage

FIXME: Use this for user-level plugins:

Put `[lein-aggravate "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your
`:user` profile, or if you are on Leiningen 1.x do `lein plugin install
lein-aggravate 0.1.0-SNAPSHOT`.

FIXME: Use this for project-level plugins:

Put `[lein-aggravate "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your `project.clj`.

There are two optional fields that you can add to your `project.clj`:
```
  :aggregate-files [{:input ["foo/bar.css" "foo/baz.css"]
                     :output "foo/compiled-files/main.css"}]
  :aggregate-dirs [{:input ["foo"]
                    :output "foo/compiled-dirs/main.css"
                    :suffix "css"}]
```

FIXME: and add an example usage that actually makes sense:

    $ lein aggravate

## License

Copyright © 2012 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
