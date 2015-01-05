# router

A Clojurescript library designed to help routing in the browser.

## Usage

Include the dependency in your project.clj.

[![Clojars Project](http://clojars.org/org.clojars.mkhoeini/router/latest-version.svg)](https://clojars.org/org.clojars.mkhoeini/router)

Then require the `router.core` in your cljs files.

```clojure
(ns your-namespace
  (:require [router.core :as router :include-macros true]))
```

Define your routes with the `match` macro. The syntax is of the [secretary](https://github.com/gf3/secretary).
However, it is simplified in that it will accept only a value, and doesn't pass parameters to the body.

```clojure
(router/match "/" :root-path)
(router/match "/user/:id" :user-path)
(router/match "/admin/*path" :admin-path)
(router/match #"/search/([a-zA-Z]*)" :search-path)
```

Configure the history manager.

```clojure
(router/config!)
```

Or, pass some options. The defaults are:

```clojure
(router/config! {:use-fragment false    ;; Use '/#/some/path' style pathes
                 :path-prefix ""        ;; The path prefix. For example "!"
                 :enabled true})        ;; Enable the manager
```

Later on, you can enable or disable the manager with

```clojure
(router/set-enabled!)
(router/set-disabled!)
```

And, finally you can register your callback to be called on routing.

```clojure
(defn callback
  "Callback should take two params:
  value: The value you associated with the current path.
  params: The returned parameters of current route from secretary."
  [value params]
  (pr "We are at" value "with params" params))

(router/set-callback! your-callback)
```

The current route is `(router/current-route)`.

You can navigate to a new route with `(router/navigate! "/new/route")`.

Also, you can prevent the default behaviour of links and buttons from refreshing the window with
`(router/prevent-default-refresh!)`.


## [React](http://facebook.github.io/react/index.html)

In the `router.react` namespace resides a default callback which you can use to mount your pages to
specific DOM node.

```clojure
(ns some-ns
  (:require [router.react :as rr]))

(rr/initiate! (.-body js/document))
```

The callback expects that the values you associated with the routes are either React elements or
functions of one parameter which return react elements. The parameter is `params` of the route.

On page navigation the supplied React element will be rendered on the DOM node.


## [Reagent](http://reagent-project.github.io/index.html)

The `router.reagent` namespace provides a different `match` macro to facilitate using Reagent
components.

```clojure
(ns some-ns
  (:require [router.reagent :refer-macros (match)]))

(match "/some-path" {:keys [param1] :as route-params} [reagent-component param1 param2 ...])
```

This macro delegates to the original `router.core/match` macro and assosiates with the route a
function with the shape that `router.react`'s callback requires.
